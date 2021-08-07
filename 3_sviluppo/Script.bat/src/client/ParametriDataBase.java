
import java.io.*;
/*
   parametri di configurazione per la connessione al DB
*/

public class ParametriDataBase {
    
    private String indirizzoIPDatabase;    
    private int portaDatabase;
    private String nomeDatabase;
    private String usernameDatabase;
    private String passwordDatabase;
    
    public ParametriDataBase( String indIPDb, String nomeDb, int portaDb, String username, String pwd) {
        this.indirizzoIPDatabase = indIPDb;
        this.portaDatabase = portaDb;
        this.nomeDatabase = nomeDb;
        this.usernameDatabase = username;
        this.passwordDatabase = pwd;
    }
    
    public String getIndirizzoIpDb() {
        return indirizzoIPDatabase;
    }
    
    public int getPortaDb() {
        return portaDatabase;
    }
    
    public String getNomeDb() {
        return nomeDatabase;
    }
    
    public String getUsernameDb() {
        return usernameDatabase;
    }
    
    public String getPwdDb() {
        return passwordDatabase;
    }
    
}
