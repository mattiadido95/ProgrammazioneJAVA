
import java.io.*;
import java.net.*;
import java.nio.file.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.xml.sax.*;


public class ServerDiLog {

    public static void main(String[] args) {
        try(ServerSocket servs = new ServerSocket(8080);){
            while(true){
                try(Socket so = servs.accept();
                    DataInputStream din = new DataInputStream(so.getInputStream());){
                    String xml = din.readUTF();
                    validaRigaDiLog(xml);
                }
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public static void validaRigaDiLog(String xml) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema s = sf.newSchema(new StreamSource("./messaggioDiLog.xsd"));
            s.newValidator().validate(new StreamSource(new StringReader(xml)));
            Files.write(Paths.get("fileDiLog.txt"), xml.getBytes(), StandardOpenOption.APPEND);
            System.out.println("Messaggio di Log registrato.");
        } catch (Exception ex) {
            if(ex instanceof SAXException)
                System.out.println("Errore di validazione: " + ex.getMessage());
            else
                System.out.println(ex.getMessage());
        }
    }
    
}
