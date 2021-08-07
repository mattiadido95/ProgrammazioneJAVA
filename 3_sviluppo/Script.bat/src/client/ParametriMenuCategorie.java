import java.io.*;
import java.util.Arrays;
/*
   parametri di configurazione per le cateogorie da usare
*/

public class ParametriMenuCategorie {

    private String[] categorie;

    public ParametriMenuCategorie(String[] cat){   
        categorie = cat;
    }

    public String[] getCategorie(){
        return categorie;
    }

}