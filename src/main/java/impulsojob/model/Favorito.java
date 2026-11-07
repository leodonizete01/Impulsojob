package impulsojob.model;

public class Favorito {

    private int id;
    private int clienteId;
    private int modeloId;
    
    // Dados do modelo associado para exibição facilitada
    private String modeloNome;
    private String modeloDescricao;

    public Favorito() {
    }

    public Favorito(int id, int clienteId, int modeloId) {
        this.id = id;
        this.clienteId = clienteId;
        this.modeloId = modeloId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getModeloId() {
        return modeloId;
    }

    public void setModeloId(int modeloId) {
        this.modeloId = modeloId;
    }

    public String getModeloNome() {
        return modeloNome;
    }

    public void setModeloNome(String modeloNome) {
        this.modeloNome = modeloNome;
    }

    public String getModeloDescricao() {
        return modeloDescricao;
    }

    public void setModeloDescricao(String modeloDescricao) {
        this.modeloDescricao = modeloDescricao;
    }
}
