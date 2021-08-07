import java.time.*;
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.chart.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Tooltip;

/*
    implementa la sezione statistiche composta dal digramma a torta e dalle informaioni statistiche 
    filtrate per periodo
*/

public class DiagrammaStatistiche {
    
    private DataBase database; // (1)
    private HBox datePickBOX;
    private VBox statBOX;
    private HBox stat_filtraBtn;
    private VBox BoxEsternoDx;
    private HBox BoxPricipale;
    private PieChart diagramma;
    private ObservableList<PieChart.Data> datiDiagramma;
    private Label statHeader;
    private DatePicker dataInizio;
    private DatePicker dataFine;
    private Label TOT;
    private Label MAX;
    private Button filtra;
    private CacheParametri cache; // (2)
    private LogXMLAttivita socketDiLog; // (3)
    private ParametriDiConfigurazione parametriConfig; // (4)
    private Label labelDataInizio;
    private Label labelDataFine;
    
    
    public DiagrammaStatistiche(DataBase db, CacheParametri cache, LogXMLAttivita socket, ParametriDiConfigurazione param) {
        
        this.database = db;
        this.cache = cache;
        this.socketDiLog = socket;
        this.parametriConfig = param;

        String[] statistiche = cache.prelevaStatistiche();           
         
        datiDiagramma = FXCollections.observableArrayList();
        diagramma = new PieChart(datiDiagramma);
    /***DIAGRAMMA****/    
        diagramma.setLegendSide(Side.BOTTOM);    

    /****HEADER****/    
        statHeader = new Label("STATISTICHE:");

    /***DATEPICKER*****/
        dataInizio = new DatePicker();
        if(statistiche[0] == null) dataInizio.setPromptText(LocalDate.now().toString());
        else dataInizio.setValue(LocalDate.parse(statistiche[0]));
        
        dataFine = new DatePicker();
        if(statistiche[1] == null) dataFine.setPromptText(LocalDate.now().toString());
        else dataFine.setValue(LocalDate.parse(statistiche[1]));
        
        labelDataInizio = new Label("da: ");
        labelDataFine = new Label("a: ");

        datePickBOX = new HBox();
        datePickBOX.getChildren().addAll(labelDataInizio, dataInizio, labelDataFine, dataFine);

    /***TOT e MAX*****/
        TOT = new Label(statistiche[2]);
        MAX = new Label(statistiche[3]);

        statBOX = new VBox();
        statBOX.getChildren().addAll(TOT, MAX);

    /****FILTRA BUTTON****/    
        filtra = new Button("Filtra");
        filtra.setOnAction((ActionEvent ev) -> {aggiornaGrafico(); 
            socketDiLog.inviaMessaggioLogEvento(TipiDiLog.CLICK_PULSANTE_FILTRA_STATISTICHE);
        });

    /****BOX****/    
        stat_filtraBtn = new HBox();
        stat_filtraBtn.getChildren().addAll(statBOX, filtra);

        BoxEsternoDx = new VBox();
        BoxEsternoDx.getChildren().addAll(statHeader,datePickBOX,stat_filtraBtn);
       
        BoxPricipale = new HBox();
        BoxPricipale.getChildren().addAll(diagramma, BoxEsternoDx);
               
        setStyle();
    }

    public void aggiornaGrafico() { // (5)
        List<PieChart.Data> d = database.ottieniStatisticheSpese(dataInizio.getValue(),dataFine.getValue());

        if (d.isEmpty()) {
            d.add(new PieChart.Data("Nessun valore", 1));
            datiDiagramma.clear();
            datiDiagramma.addAll(d);
            TOT.setText("- TOT: -");
            MAX.setText("- MAX: - in -");
            diagramma.getData().forEach(data -> {                
                Tooltip toolTip = new Tooltip(" - %");
                Tooltip.install(data.getNode(), toolTip);
            });
        } else{
            datiDiagramma.clear();
            datiDiagramma.addAll(d);

            int tot = database.ottieniTotale(dataInizio.getValue(),dataFine.getValue());
            TOT.setText("- TOT: "+tot);
        
            Spesa spesamassima = database.ottieniSpesaMAX(dataInizio.getValue(),dataFine.getValue());
            MAX.setText("- MAX: " + spesamassima.getEsborso() + " in: " + spesamassima.getCategoria());

            diagramma.getData().forEach(data -> {                
                String percentage = String.format("%.2f %%", (data.getPieValue()/tot * 100));
                Tooltip toolTip = new Tooltip(percentage);
                Tooltip.install(data.getNode(), toolTip);
            });
        }
          
    }    
    
    public HBox getHBox() { 
        return BoxPricipale;
    }

    public String[] getStatistiche(){ // (6)
        String[] stat = new String[]{ 
            dataInizio.getValue() == null ? null : dataInizio.getValue().toString(),
            dataFine.getValue() == null ? null : dataFine.getValue().toString(),
            TOT.getText(),
            MAX.getText(),
        };
        return stat;
    }

    private void setStyle() { // (7)
        String font = parametriConfig.getParametriStilistici().getFont();
        String dimensione = String.valueOf(parametriConfig.getParametriStilistici().getDimensioneFont().getDimensione());
        String unita = parametriConfig.getParametriStilistici().getDimensioneFont().getUnita();
        statHeader.setStyle("-fx-font-family: " + font + "; -fx-font-size: " + dimensione + unita);
        TOT.setStyle("-fx-font-family: " + font + "; -fx-font-size: " + dimensione + unita);
        MAX.setStyle("-fx-font-family: " + font + "; -fx-font-size: " + dimensione + unita);

        stat_filtraBtn.setSpacing(50);
        stat_filtraBtn.setAlignment(Pos.CENTER);

        datePickBOX.setSpacing(10);
        datePickBOX.setAlignment(Pos.CENTER);

        BoxEsternoDx.setSpacing(30);
        BoxEsternoDx.setAlignment(Pos.CENTER);
    }
   
}


/*
    (1) -> oggetto database usato per implementare il caricamento spese presenti nel DB
    (2) -> oggetto cache usato per prelevare da cache i dati della sezione statistiche 
    (3) -> oggetto socketDiLog usato per loggare l'evento di visualizzazione statistiche
    (4) -> oggetto usato per ottenere i parametri stilistici 
    (5) -> metodo usato per aggiornare il diagramma con statistiche relative al periodo delimitato dai parametri passati
    (6) -> metodo che prepara un array di stringhe con i dati inseriti della sezione statistiche da salvare in cache
    (7) -> metodo che setta lo stile della sezione statistiche
*/
