
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import java.io.Serializable;

/*
  parametro di configurazione per la dimensione e l'unità del font.
 */

@XStreamAlias("dimensioneFont") //(1)
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"dimensione"}) //(1)
public class DimensioneFont implements Serializable {
    
    private int dimensione;
    @XStreamAlias("unita") //(1)
    private String unita;
    
    public DimensioneFont(int dim, String unita) {
        this.dimensione = dim; 
        this.unita = unita;
    }
        
    public int getDimensione() {
        return dimensione;
    }
    
    public String getUnita() {
        return unita;
    }
    
}
/*
 (1) -> Utilizzato annotazioni per la nidificazione di un attributo in un elemento(https://tinyurl.com/yc3qoght)
*/
