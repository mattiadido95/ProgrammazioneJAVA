
import java.io.*;
/*
 parametri per la connessione con il server di log.
 
*/

public class ParametriServer {
    
    private String indirizzoIPServerDiLog;
    private int portaServerDiLog;
    
    public ParametriServer(String ind, int porta) {
        this.indirizzoIPServerDiLog = ind;
        this.portaServerDiLog = porta;
    }
    
    public String getIndirizzoIpSrvr() {
        return indirizzoIPServerDiLog;
    }
    
    public int getPortaServer() {
        return portaServerDiLog;
    }
    
}
