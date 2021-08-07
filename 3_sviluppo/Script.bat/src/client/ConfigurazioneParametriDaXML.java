
import com.thoughtworks.xstream.*;
import java.io.*;
import java.nio.file.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import com.thoughtworks.xstream.converters.basic.*;
        

/**
 ConfigurazioneParametriDaXML: gestore che preleva e valida i parametri in fase di configarazione da file locale XML. 
 */


public class ConfigurazioneParametriDaXML {
    
    private static String pathFileConfigurazione;
    private static String pathFileXSDConfigurazione;
    private ParametriDiConfigurazione parametri;
    
    public ConfigurazioneParametriDaXML(String pathFileConfig, String pathFileXSD) { // (1)
        this.pathFileConfigurazione = pathFileConfig;
        this.pathFileXSDConfigurazione = pathFileXSD;
    }
   
    
    public void validaFileDiConfigurazione() { // (2)
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = db.parse(pathFileConfigurazione);
            Schema s = sf.newSchema(new StreamSource(pathFileXSDConfigurazione));
            s.newValidator().validate(new DOMSource(d));
        } catch (Exception ex) {
            if(ex instanceof SAXException)
                System.out.println("Errore di validazione: " + ex.getMessage());
            else
                System.out.println(ex.getMessage());
        }
    }    
    
    public ParametriDiConfigurazione getParametri() { // (3)
        validaFileDiConfigurazione();
        XStream xs = new XStream();
        xs.processAnnotations(DimensioneFont.class);
        String x = null;
        try {
            x = new String(Files.readAllBytes(Paths.get(pathFileConfigurazione)));
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
        parametri = (ParametriDiConfigurazione)xs.fromXML(x);
        return parametri; 
    }
   
   
}

/*
    (1) -> costruttore del gestore che lo inzializza con il path del file da leggere e quello del file XSD 
            utilizzato per validarlo

    (2) -> metodo che esegue la validazione del file con i parametri richiesti, restituisce l'errore in caso di fallimento
    
    (3) -> metodo che restituisce i parametri richiesti dopo aver eseguito una conversione tramite il '.fromXML()', resituisce 
            l'oggetto parametri di tipo ParametriDiConfigurazione
*/