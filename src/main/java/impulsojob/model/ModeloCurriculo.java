package impulsojob.model;

public class ModeloCurriculo {

    private int id;
    private String nome;
    private String descricao;
    private String arquivoModelo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getArquivoModelo() {
        return arquivoModelo;
    }

    public void setArquivoModelo(String arquivoModelo) {
        this.arquivoModelo = arquivoModelo;
    }

    public ModeloCurriculo() {
    }

    public ModeloCurriculo(int id, String nome, String descricao, String arquivoModelo) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.arquivoModelo = arquivoModelo;
    }
}

