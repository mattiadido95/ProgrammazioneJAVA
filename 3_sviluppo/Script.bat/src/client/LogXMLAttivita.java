import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.converters.basic.*;
import java.net.*;
import java.io.*;

/*
  LogXMLAttivita serializza e invia i messaggi di log al serverDiLog
*/
public class LogXMLAttivita {

    private String indirizzoIPServer;
    private int portaServer;

    public LogXMLAttivita (ConfigurazioneParametriDaXML gestoreParametri) {
        ParametriDiConfigurazione p = gestoreParametri.getParametri();
        this.indirizzoIPServer = p.getParametriServer().getIndirizzoIpSrvr();
        this.portaServer = p.getParametriServer().getPortaServer();
    }

    public void inviaMessaggioLogEvento(TipiDiLog evento) { // (1)
        
        try(Socket s = new Socket(indirizzoIPServer, portaServer);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());) {
            MessaggioDiLog m = new MessaggioDiLog(evento, InetAddress.getLocalHost().getHostAddress());

            dout.writeUTF("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+serializzaXML(m)+"\n\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private String serializzaXML(MessaggioDiLog m) { // (2)
        XStream xs = new XStream();
        xs.useAttributeFor(MessaggioDiLog.class, "evento"); // (3)
        xs.registerConverter(new DateConverter("yyyy-MM-dd", null));
        String x = xs.toXML(m);
        return x;
    }
}
/*
 (1) -> invia il messaggio di log serializzato tramite serializzaXML() al server di log

 (2) -> serializza il messaggio di log

 (3) -> serializza l'evento sotto forma di tag annidato a MessaggioDiLog.
*/