import java.sql.*;
import java.time.*;
import java.util.*;
import javafx.scene.chart.*;

/*
    DataBase implementa la struttura backend dell'applicativo mettendo a disposizione metodi per manipolare i dati nel DB
*/

public class DataBase {

    private static Connection connessioneADatabase;
    private static PreparedStatement statementGetSpeseInserite;
    private static PreparedStatement statementGetCategorie;
    private static PreparedStatement statementGetStorico;
    private static PreparedStatement statementAggiungiSpesa;
    private static PreparedStatement statementEliminaSpesa;
    private static PreparedStatement setTOT;
    private static PreparedStatement setMAX;
    private static String url;
    private static String usernameDatabase;
    private static String passwordDatabase;

    public DataBase(ParametriDiConfigurazione parametriDB) {

        String indIP = parametriDB.getParametriDatabase().getIndirizzoIpDb();
        int porta = parametriDB.getParametriDatabase().getPortaDb();;
        String nomeDB = parametriDB.getParametriDatabase().getNomeDb();
        usernameDatabase = parametriDB.getParametriDatabase().getUsernameDb();
        passwordDatabase = parametriDB.getParametriDatabase().getPwdDb();
        url = "jdbc:mysql://"+indIP+":"+porta+"/"+nomeDB;

        try { // (1)
            connessioneADatabase = DriverManager.getConnection(url, usernameDatabase, passwordDatabase );
            statementGetSpeseInserite = connessioneADatabase.prepareStatement("SELECT * FROM spese WHERE categoria=? AND data>=? AND data<=?");            
            statementGetStorico = connessioneADatabase.prepareStatement("SELECT SUM(esborso) as esborso, categoria FROM spese WHERE data>=? AND data<=? GROUP BY categoria"); 
            statementAggiungiSpesa = connessioneADatabase.prepareStatement("INSERT INTO spese VALUES (?,?,?,?,?)");
            statementEliminaSpesa = connessioneADatabase.prepareStatement("DELETE FROM spese WHERE id = ?");
            setTOT = connessioneADatabase.prepareStatement("SELECT SUM(esborso) as esborso FROM spese WHERE data>=? AND data<=?");
            setMAX = connessioneADatabase.prepareStatement("SELECT * FROM spese WHERE data>=? AND data<=? ORDER BY esborso DESC LIMIT 1");
        } catch(SQLException e) {
            System.err.println(e.getMessage());     
        }
    }

/* FUNZIONI PER DETTAGLIO */
    public List<Spesa> ottieniListaSpese(String categoria, LocalDate dataInizio, LocalDate dataFine ) {  // (2)
        List<Spesa> listaSpese = new ArrayList<>();
        try {
            statementGetSpeseInserite.setString(1, categoria);
            statementGetSpeseInserite.setDate(2, java.sql.Date.valueOf(dataInizio));
            statementGetSpeseInserite.setDate(3, java.sql.Date.valueOf(dataFine));
            ResultSet rs = statementGetSpeseInserite.executeQuery();
            while(rs.next()){
                listaSpese.add(new Spesa(rs.getInt("id"), rs.getString("nome"), rs.getString("categoria"),rs.getInt("esborso"), rs.getDate("data").toString()));}
        } catch(SQLException e ) {
            System.err.println(e.getMessage()); 
        }        
        return listaSpese;
    }

    public void eliminaSpesa(int idSpesa) {  // (3)
        try{
            statementEliminaSpesa.setInt(1, idSpesa);
            statementEliminaSpesa.executeUpdate();
            System.out.println("Spesa eliminata.");
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void aggiungiSpesa(int esborso, String categoria, String nome, LocalDate data) {  // (4)
        try{
            statementAggiungiSpesa.setInt(1, 0);
            statementAggiungiSpesa.setString(2, nome);
            statementAggiungiSpesa.setString(3, categoria);
            statementAggiungiSpesa.setInt(4, esborso);
            statementAggiungiSpesa.setDate(5, java.sql.Date.valueOf(data)); 

            statementAggiungiSpesa.executeUpdate();
            System.out.println("Spesa salvata.");
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }        
    }

/* FUNZIONI PER DIAGRAMMA E STATISTICHE */
    public List<PieChart.Data> ottieniStatisticheSpese( LocalDate dataInizio, LocalDate dataFine ) { // (5)
        List<PieChart.Data> listaSpese = new ArrayList<>();
        try {
            statementGetStorico.setDate(1, java.sql.Date.valueOf(dataInizio));
            statementGetStorico.setDate(2, java.sql.Date.valueOf(dataFine));
            ResultSet rs = statementGetStorico.executeQuery();            
            while(rs.next())
             listaSpese.add(new PieChart.Data(rs.getString("categoria"), rs.getInt("esborso")));            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listaSpese;
    }

    public int ottieniTotale( LocalDate dataInizio, LocalDate dataFine ){ // (6)
        int totale = 0;
        try {  
            setTOT.setDate(1, java.sql.Date.valueOf(dataInizio));
            setTOT.setDate(2, java.sql.Date.valueOf(dataFine));
            ResultSet rs = setTOT.executeQuery();
            rs.next();          
            totale = rs.getInt("esborso");
        }catch(SQLException e){
            System.err.println(e.getMessage() + " SPESA TOT");
        }                
        return totale;
    }

    public Spesa ottieniSpesaMAX( LocalDate dataInizio, LocalDate dataFine ) { // (7)
        Spesa spesa = new Spesa (0,"","",0,null);
        try {
            setMAX.setDate(1, java.sql.Date.valueOf(dataInizio));
            setMAX.setDate(2, java.sql.Date.valueOf(dataFine));
            ResultSet rs = setMAX.executeQuery();
            rs.next();
            spesa = new Spesa(rs.getInt("id"), rs.getString("nome"), rs.getString("categoria"),rs.getInt("esborso"), rs.getDate("data").toString());
        } catch(SQLException e ) {
            System.err.println(e.getMessage() + " SPESA MAX"); 
        }    
        return spesa;
    } 

}

/*
    (1) -> vengono create le query richieste dai metodi successivi
    (2) -> usato per ottenere la lista delle spese presenti nel DB
    (3) -> usato per eliminare la spesa selezionata nella table della sezione "Dettaglio"
    (4) -> usato per aggiungere una nuova spesa nel DB 
    (5) -> usato per ottenere una lista spese effettuate in un periodo determinato dai parametri passati
    (6) -> usato per ottenere quanto speso nel periodo preso delimitato dai parametri passati
    (7) -> usato per ottenere la spesa piu esosa fra le presenti nel periodo delimitato dai parametri passati
*/