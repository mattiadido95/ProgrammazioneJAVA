import javafx.beans.property.*;

/**
    usata per incapsulare i dati restituiti da query SQL in un oggetto Java
*/


public class Spesa {
    
    private final SimpleIntegerProperty id;    
    private final SimpleIntegerProperty esborso;
    private final SimpleStringProperty categoria;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty data;

    public Spesa (int id, String nome, String categoria, int esborso, String data) {
        this.id = new SimpleIntegerProperty(id);        
        this.nome = new SimpleStringProperty(nome);        
        this.categoria = new SimpleStringProperty(categoria);
        this.esborso = new SimpleIntegerProperty(esborso);
        this.data = new SimpleStringProperty(data);
    }
    
    public int getId() {
        return id.get();
    }

    public int getEsborso() {
        return esborso.get();
    }

    public String getCategoria() {
        return categoria.get();
    }

    public String getNome() {
        return nome.get();
    }

    public String getData() {
        return data.get();
    }

}