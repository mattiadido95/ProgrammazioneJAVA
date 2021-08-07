
import java.io.*;

/*
 
 CacheParametri: classe il cui scopo è di memorizzare e prelevare da files 
 binari la spesa inserita ma non salvata, i dati inseriti nel filtro, la riga selezionata e
 i dati insieriti nel campo statistiche,

 */

public class CacheParametri {
    
    private static File fileNuovaSpesa;
    private static File fileFiltro;
    private static File fileRiga;
    private static File fileStatistiche;
    
    
    public CacheParametri(File fNuovaSpesa, File fFiltro, File fRiga, File fStat) {
        fileNuovaSpesa = fNuovaSpesa;
        fileFiltro = fFiltro;
        fileRiga = fRiga;
        fileStatistiche = fStat;
    }
    
    public void memorizzaSpesaNonSalvata(String[] nuovaSpesa) {
        try(FileOutputStream writerFile = new FileOutputStream(fileNuovaSpesa);
            ObjectOutputStream oout = new ObjectOutputStream(writerFile);) {
            for(int i = 0; i < nuovaSpesa.length; i++)
                oout.writeObject(nuovaSpesa[i]);
            System.out.println("Salvata nuova spesa in cache. [file: ./cache/cacheNS.bin]");
        } catch (IOException ex) {
            System.err.println("Impossibile salvare la spesa in cache. [file: ./cache/cacheNS.bin]");
            ex.printStackTrace();
        }
    }
    public String[] prelevaSpesaNonSalvata() {
        String[] spesa = new String[4];
        try(FileInputStream readerFile = new FileInputStream(fileNuovaSpesa);
            ObjectInputStream oin = new ObjectInputStream(readerFile);) {
            for(int i = 0; i < 4; i++)
                spesa[i] = (String)oin.readObject();
            System.out.println("Prelevata nuova spesa in cache. [file: ./cache/cacheNS.bin]");
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Impossibile prelevare spesa salvata in cache. [file: ./cache/cacheNS.bin]");
        }
        return spesa;
    }

    public void memorizzaFiltro(String[] filtri) {
        try(FileOutputStream writerFile = new FileOutputStream(fileFiltro);
            ObjectOutputStream oout = new ObjectOutputStream(writerFile);) {
            for(int i = 0; i < filtri.length; i++)
                oout.writeObject(filtri[i]);
            System.out.println("Salvati parametri filtro in cache. [file: ./cache/cacheF.bin]");
        } catch (IOException ex) {
            System.err.println("Impossibile salvare i parametri del filtro in cache. [file: ./cache/cacheF.bin]");
            ex.printStackTrace();
        }
    }
    public String[] prelevaFiltro() {
        String[] filtri = new String[3];
        try(FileInputStream readerFile = new FileInputStream(fileFiltro);
            ObjectInputStream oin = new ObjectInputStream(readerFile);) {
            for(int i = 0; i < 3; i++)
                filtri[i] = (String)oin.readObject();
            System.out.println("Prelevati parametri filtro in cache. [file: ./cache/cacheF.bin]");
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Impossibile prelevare i parametri del filtro in cache. [file: ./cache/cacheF.bin]");
        }
        return filtri;
    }
    
    public void memorizzaRigaSelezionata(int numRiga) {
        try(FileOutputStream writerFile = new FileOutputStream(fileRiga);
            ObjectOutputStream oout = new ObjectOutputStream(writerFile);) {
                oout.writeObject(numRiga);
                System.out.println("Salvato numero di riga selezionata nella tabella in cache. [file: ./cache/cacheRS.bin]");
        } catch (IOException ex) {
            System.err.println("Impossibile salvare il numero di riga selezionata nella tabella in cache. [file: ./cache/cacheRS.bin]");
            ex.printStackTrace();
        }
    }
    public int prelevaRigaSelezionata() {
        int numRiga = -1;
        try(FileInputStream readerFile = new FileInputStream(fileRiga);
            ObjectInputStream oin = new ObjectInputStream(readerFile);) {
                numRiga = (Integer)oin.readObject();
                System.out.println("Prelevato numero di riga selezionata nella tabella in cache. [file: ./cache/cacheRS.bin]");
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Impossibile prelevare il numero di riga selezionata nella tabella in cache. [file: ./cache/cacheRS.bin]");
        }
        return numRiga;
    }
    
    public void memorizzaStatistiche(String[] stat) {
        try(FileOutputStream writerFile = new FileOutputStream(fileStatistiche);
            ObjectOutputStream oout = new ObjectOutputStream(writerFile);) {
            for(int i = 0; i < stat.length; i++)
                oout.writeObject(stat[i]);
            System.out.println("Salvati parametri statistiche in cache. [file: ./cache/cacheSt.bin]");
        } catch (IOException ex) {
            System.err.println("Impossibile conservare i parametri statistici in cache [file: ./cache/cacheSt.bin]");
            ex.printStackTrace();
        }
    }
    public String[] prelevaStatistiche() {
        String[] stat = new String[4];
        try(FileInputStream readerFile = new FileInputStream(fileStatistiche);
            ObjectInputStream oin = new ObjectInputStream(readerFile);) {
            for(int i = 0; i < 4; i++)
            stat[i] = (String)oin.readObject();
            System.out.println("Prelevati parametri statistiche in cache. [file: ./cache/cacheSt.bin]");
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Impossibile prelevare i parametri statistici in cache.  [file: ./cache/cacheSt.bin]");
        }
        return stat;
    }
}
