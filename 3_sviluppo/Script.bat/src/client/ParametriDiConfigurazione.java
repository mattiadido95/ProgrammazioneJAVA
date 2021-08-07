import java.io.*;
/**
 classe principale dei parametri di configurazione, contiene i parametri server, database, stilistici, user e menucategoria
*/
public class ParametriDiConfigurazione implements Serializable {
    
    private ParametriServer parametriServer; 
    private ParametriDataBase parametriDatabase; 
    private ParametriStilistici parametriStilistici;   
    private ParametriUser parametriUser; 
    private ParametriMenuCategorie parametriMenuCategorie;
    
    public ParametriDiConfigurazione(ParametriServer paramServer, ParametriDataBase paramDb, ParametriStilistici paramStilistici, ParametriUser paramUser, ParametriMenuCategorie paramCat) {
        this.parametriServer = paramServer;
        this.parametriDatabase = paramDb;
        this.parametriStilistici = paramStilistici;
        this.parametriUser = paramUser;
        this.parametriMenuCategorie = paramCat;
    }
    
    public ParametriServer getParametriServer() { // (1)
        return parametriServer;
    }
    
    public ParametriDataBase getParametriDatabase() { // (2)
        return parametriDatabase;
    }
    
    public ParametriStilistici getParametriStilistici() { // (3)
        return parametriStilistici;
    }

    public ParametriUser getParametriUser() { // (4)
        return parametriUser;
    }

    public ParametriMenuCategorie getParametriMenuCategorie() { // (5)
        return parametriMenuCategorie;
    }
}
/*
    (1) -> parametri server per LogXMLAttivita per connettersi 
    (2) -> parametri database per DataBase per connettersi
    (3) -> parametri di stile 
    (4) -> parametri info user
    (5) -> categorie da usare    
*/