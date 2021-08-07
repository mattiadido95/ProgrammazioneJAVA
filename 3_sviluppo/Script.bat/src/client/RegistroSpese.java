import java.io.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.geometry.*;

/*
    classe principale applicativo
*/

public class RegistroSpese extends Application {
    
    private Label msgbenvenuto;
    private Tabella tabella;
    private DiagrammaStatistiche diagrammaStatistiche;   
    private DataBase dataBase;
    private CacheParametri cache;
    private LogXMLAttivita socketDiLog;
    private VBox vb;
    private HBox boxmsg;
  
    
    public void start(Stage stage) {
        
        ConfigurazioneParametriDaXML gestoreParamXML = new ConfigurazioneParametriDaXML("./parametri.xml", "./parametri.xsd");       
        ParametriDiConfigurazione configParam = gestoreParamXML.getParametri();
        
        File fNuovaSpesa = new File("./cache/cacheNS.bin");
        File fFiltro = new File("./cache/cacheF.bin");
        File fRigaSelezionata = new File("./cache/cacheRS.bin");
        File fStatistiche = new File("./cache/cacheSt.bin");

        dataBase = new DataBase(configParam);
        cache = new CacheParametri(fNuovaSpesa, fFiltro, fRigaSelezionata, fStatistiche);    
        socketDiLog = new LogXMLAttivita(gestoreParamXML);
        socketDiLog.inviaMessaggioLogEvento(TipiDiLog.AVVIO_APPLICAZIONE);    

        String nick = configParam.getParametriUser().getNickname();        
        msgbenvenuto = new Label("Ciao "+ nick);    
        String font = configParam.getParametriStilistici().getFont();
        
        String unita = configParam.getParametriStilistici().getDimensioneFont().getUnita();
        msgbenvenuto.setStyle("-fx-font-family: " + font + "; -fx-font-size: " + "20" + unita);

        tabella = new Tabella(dataBase, cache, socketDiLog, configParam);
        tabella.caricaSpese();

        diagrammaStatistiche = new DiagrammaStatistiche(dataBase, cache, socketDiLog, configParam);     
        diagrammaStatistiche.aggiornaGrafico();
        
        stage.setOnCloseRequest((WindowEvent we) -> {
            cache.memorizzaSpesaNonSalvata(tabella.getNuovaSpesa());
            cache.memorizzaFiltro(tabella.getFiltro());
            cache.memorizzaRigaSelezionata(tabella.getRigaSelezionata());
            cache.memorizzaStatistiche(diagrammaStatistiche.getStatistiche());
            socketDiLog.inviaMessaggioLogEvento(TipiDiLog.TERMINE_APPLICAZIONE);
        });      

        boxmsg = new HBox();
        boxmsg.getChildren().addAll(msgbenvenuto);
        boxmsg.setSpacing(30);
        boxmsg.setAlignment(Pos.CENTER);
        
        vb = new VBox();
        vb.getChildren().addAll(boxmsg, diagrammaStatistiche.getHBox(), tabella.getVBox());
        vb.setSpacing(10);
        
  

        String background = configParam.getParametriStilistici().getColBg();
        vb.setStyle("-fx-background-color:"+background+";");
        vb.setPrefSize(950,850);

        
        Group root = new Group(vb);       
        
        Scene scene = new Scene(root);
    
        stage.setTitle("Registro Spese");
        stage.setScene(scene);
        stage.setHeight(850);
        stage.setWidth(950);
        stage.show();
    }
    
}


