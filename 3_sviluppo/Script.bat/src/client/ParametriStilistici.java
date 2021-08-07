
import java.io.*;

/*
 parametri di configurazione dello stile

 */

public class ParametriStilistici {
        
    private String font;
    private DimensioneFont dimensioneFont; 
    private String coloreBackground;
    private String coloreFont;
    
    public ParametriStilistici(String font, DimensioneFont fimFont, String colBackG, String colFont) {
        this.font = font;
        this.dimensioneFont = fimFont;
        this.coloreBackground = colBackG;
        this.coloreFont = colFont;
    }
    
    public String getFont() {
        return font;
    }
        
    public DimensioneFont getDimensioneFont() {
        return dimensioneFont;
    }

    public String getColBg(){
        return coloreBackground;
    }

    public String getColFnt(){
        return coloreFont;
    }
}

