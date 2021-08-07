import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.*;
import javafx.event.*;
import javafx.geometry.*;
import java.time.*;

/*
    classe che implementa la sezione dettaglio contenente la tabella con campi input, button per manipolarla e sezione filtri
*/

public class Tabella {
    
    private VBox vbox;
    private VBox vboxButton;
    private VBox vboxTab_Inp;
    private HBox hbox;
    private HBox hboxfiltro;
    private HBox hboxinput;
    private Label titolo;
    private DataBase dataBase;
    private TableView<Spesa> table;
    private ObservableList<Spesa> listaSpese;
    private ObservableList<String> opzioniComboBox;    
    private Button buttonSvuota;
    private Button buttonAggiungi;
    private Button buttonRimuovi;
    private TextField nome;
    private ComboBox categoria;
    private TextField esborso;
    private DatePicker data;
    private Button filtra;
    private ComboBox filtrocategoria;
    private DatePicker filtrodatainizio;
    private DatePicker filtrodatafine;
    private CacheParametri cache;
    private LogXMLAttivita socketDiLog;
    private ParametriDiConfigurazione parametriConfig;
    private Label labelDataInizio;
    private Label labelDataFine;
    private Boolean primocaricamento;
    


    public Tabella (DataBase db, CacheParametri cache, LogXMLAttivita so, ParametriDiConfigurazione param){

        this.dataBase = db;
        this.cache = cache;   
        this.socketDiLog = so;
        this.parametriConfig = param;      
        primocaricamento = true;      
        
        titolo = new Label("Dettaglio");

/* ---------------SEZIONE COSTRUZIONE TABELLA---------------- */   
        table = new TableView<>();
        
        table.setMinHeight(150);
        table.setMaxHeight(150);
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn nomeCol = new TableColumn("nome");
        nomeCol.setMinWidth(200);
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        TableColumn categoriaCol = new TableColumn("categoria");
        categoriaCol.setMinWidth(200);
        categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        
        TableColumn esborsoCol = new TableColumn("esborso");
        esborsoCol.setMinWidth(200);
        esborsoCol.setCellValueFactory(new PropertyValueFactory<>("esborso"));
        
        TableColumn dataCol = new TableColumn("data");
        dataCol.setMinWidth(200);
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));

        listaSpese = FXCollections.observableArrayList();
        table.setItems(listaSpese);
        table.getColumns().addAll(nomeCol, categoriaCol, esborsoCol, dataCol);

        nomeCol.setStyle( "-fx-alignment: CENTER;");
        categoriaCol.setStyle( "-fx-alignment: CENTER;");
        esborsoCol.setStyle( "-fx-alignment: CENTER;");
        dataCol.setStyle( "-fx-alignment: CENTER;");

/* --------------------------------------------------------- */

/* ----------------------SEZIONE INPUT---------------------- */

        String[] spesa = cache.prelevaSpesaNonSalvata();
        String[] categorie = parametriConfig.getParametriMenuCategorie().getCategorie();
        

        /*******INPUT NOME SPESA*********/
        nome = new TextField();
        if(spesa[0] != null) nome.setText(spesa[0]);
        nome.setPromptText("nome");
        nome.setMinWidth(200);

       
        /*******INPUT CATEGORIA SPESA*********/
        opzioniComboBox = FXCollections.observableArrayList();
        opzioniComboBox.addAll(categorie);
        categoria = new ComboBox(opzioniComboBox);
        if(spesa[1] != null) categoria.setValue(spesa[1]);
        categoria.setPromptText("categoria");
        categoria.setMinWidth(200);
        

        
        /*******INPUT ESBORSO SPESA*********/
        esborso = new TextField();
        if(spesa[2] != null) esborso.setText(spesa[2]);
        esborso.setPromptText("esborso");
        esborso.setMinWidth(200);


        /*******INPUT DATA SPESA*********/
        data = new DatePicker();
        if(spesa[3] == null) data.setPromptText(LocalDate.now().toString());
        else data.setValue(LocalDate.parse(spesa[3]));
        data.setPromptText("data");
        data.setMinWidth(200);

/* ----------------------------------------------------------- */
             
/*-------------------SEZIONE BUTTON TABELLA--------------------*/ 

        buttonSvuota = new Button("Svuota");
        buttonSvuota.setOnAction((ActionEvent ev) -> {annullaInserimento(); 
            socketDiLog.inviaMessaggioLogEvento(TipiDiLog.CLICK_PULSANTE_SVUOTA_NUOVA_SPESA);});

        buttonRimuovi = new Button("Rimuovi");
        buttonRimuovi.setOnAction((ActionEvent ev) -> {eliminaSpesa();
            socketDiLog.inviaMessaggioLogEvento(TipiDiLog.CLICK_PULSANTE_RIMUOVI_SPESA);});

        buttonAggiungi = new Button("Aggiungi");
        buttonAggiungi.setOnAction((ActionEvent ev) -> {salvaSpesa();
            socketDiLog.inviaMessaggioLogEvento(TipiDiLog.CLICK_PULSANTE_AGGIUNGI_SPESA);});
        
/* ----------------------------------------------------------- */

/* ----------------------SEZIONE FILTRO---------------------- */

        filtra = new Button("Filtra");
        filtra.setOnAction((ActionEvent ev) -> {caricaSpese(); 
            socketDiLog.inviaMessaggioLogEvento(TipiDiLog.CLICK_PULSANTE_FILTRA_DETTAGLI);});

        String[] filtri = cache.prelevaFiltro();
       

        /*******INPUT CATEGORIA FILTRO*********/
        opzioniComboBox = FXCollections.observableArrayList();
        opzioniComboBox.addAll(categorie);
        filtrocategoria = new ComboBox(opzioniComboBox);
        if(filtri[0] != null) filtrocategoria.setValue(filtri[0]);
        filtrocategoria.setPromptText("categoria");
        filtrocategoria.setMinWidth(200);
        


        /*******INPUT DATA SPESA*********/
        filtrodatainizio = new DatePicker();
        if(filtri[1] == null) filtrodatainizio.setPromptText(LocalDate.now().toString());
        else filtrodatainizio.setValue(LocalDate.parse(filtri[1]));
        filtrodatainizio.setPromptText("data");
        filtrodatainizio.setMinWidth(200);

        filtrodatafine = new DatePicker();        
        if(filtri[2] == null) filtrodatafine.setPromptText(LocalDate.now().toString());
        else filtrodatafine.setValue(LocalDate.parse(filtri[2]));
        filtrodatafine.setPromptText("data");
        filtrodatafine.setMinWidth(200);

        labelDataInizio = new Label("da: ");
        labelDataFine = new Label("a: ");

/* ----------------------------------------------------------- */

        hboxfiltro = new HBox();
        hboxfiltro.getChildren().addAll(filtrocategoria, labelDataInizio, filtrodatainizio, labelDataFine, filtrodatafine,filtra);

        hboxinput = new HBox();
        hboxinput.getChildren().addAll(nome,categoria,esborso,data);    
        
        vboxTab_Inp = new VBox();
        vboxTab_Inp.getChildren().addAll(table, hboxinput);        
        
        vboxButton = new VBox();
        vboxButton.getChildren().addAll(buttonSvuota,buttonRimuovi,buttonAggiungi);

        hbox = new HBox();
        hbox.getChildren().addAll(vboxTab_Inp, vboxButton);
        
        vbox = new VBox();
        vbox.getChildren().addAll(titolo,hboxfiltro, hbox);
        setStyle();
        
    }

    private void annullaInserimento() { // (1)
        esborso.setText("");
        categoria.setValue(null);
        nome.setText("");
        data.setValue(null);
        System.out.println("Campi puliti.");
    }

    public void caricaSpese() {  // (2)
        /* DATI PER LA TABELLA DETTAGLIO */ 
        
        if(filtrocategoria.getValue() == null) return; //al caricamento quando non è settata nessuna categoria
        List<Spesa> spese = dataBase.ottieniListaSpese( filtrocategoria.getValue().toString(), filtrodatainizio.getValue(), filtrodatafine.getValue() );
        listaSpese.clear();
        listaSpese.addAll(spese);
        if(primocaricamento){
            int riga = cache.prelevaRigaSelezionata();             
            table.getSelectionModel().select(riga);
        }        
        primocaricamento = false;
    }

    private void salvaSpesa() {  // (3)
        int costo;
        String categoria2;
        LocalDate data1;
        
        if(!esborso.getText().isEmpty())
            costo = Integer.parseInt(esborso.getText());
        else {
            System.out.println("Impossibile salvare la spesa, manca il parametro costo.");
            return;
        }        
        if(categoria.getValue() != null)
            categoria2 = categoria.getValue().toString();
        else {
            System.out.println("Impossibile salvare la spesa, manca il parametro categoria.");
            return;
        }        
        String descrizione = nome.getText();        
        if(data.getValue() != null)
            {
                data1 = data.getValue();}
        else
            {
                data1 = LocalDate.now();}
        
        dataBase.aggiungiSpesa(costo, categoria2, descrizione, data1);
        caricaSpese();
        annullaInserimento();
    }   

    private void eliminaSpesa() { //(4)
        Spesa tmp = table.getSelectionModel().getSelectedItem();
        if (tmp == null)
            System.out.println("Nessuna spesa selezionata.");
        else {
            System.out.println("Eliminata la spesa nr°: " + tmp.getId());
            dataBase.eliminaSpesa(tmp.getId());
        }        
        caricaSpese();  
    }

    public VBox getVBox() { 
        return vbox;
    }

    public String[] getNuovaSpesa() { //(5)
        String[] l = new String[]{ 
            nome.getText(),
            categoria.getValue() == null ? null : categoria.getValue().toString(),
            esborso.getText(),
            data.getValue()== null ? null : data.getValue().toString()
        };
        return l;
    }

    public String[] getFiltro() { //(6)
        String[] l = new String[]{ 
            filtrocategoria.getValue() == null ? null : filtrocategoria.getValue().toString(),
            filtrodatainizio.getValue()== null ? null : filtrodatainizio.getValue().toString(),
            filtrodatafine.getValue()== null ? null : filtrodatafine.getValue().toString(),
        };
        return l;
    }

    public int getRigaSelezionata() { //(7)
     int numRiga = table.getSelectionModel().getSelectedIndex();   
     return numRiga;
    }

    
    private void setStyle() { //(8)
        String font = parametriConfig.getParametriStilistici().getFont();
        String dimensione = String.valueOf(parametriConfig.getParametriStilistici().getDimensioneFont().getDimensione());
        String unita = parametriConfig.getParametriStilistici().getDimensioneFont().getUnita();
        titolo.setStyle("-fx-font-family: " + font + "; -fx-font-size: " + dimensione + unita);
        hboxfiltro.setSpacing(10);
        hboxfiltro.setAlignment(Pos.CENTER);
        vboxButton.setAlignment(Pos.CENTER);
        vboxButton.setSpacing(10);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        table.setMinHeight(230);
        table.setMaxHeight(230);
        

    }
}

/*
    (1) -> metodo che una volta eseguita l'operazione di inserimento nuova spesa ripulisce i campi input
    (2) -> metodo che carica la lista spese in dettaglio nella tabella dopo l'operazioe di filtraggio
    (3) -> metodo che salva la nuova spesa inserita 
    (4) -> metodo che elimina la spesa selezionata nella tabella
    (5), (6), (7) -> metodo utilizzato dalla cache per prelevare i dati della sezione dettaglio
    (8) -> metodo per settare lo stile  

*/