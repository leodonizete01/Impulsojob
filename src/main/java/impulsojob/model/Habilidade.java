package impulsojob.model;

public class Habilidade {

    private int id;
    private String nome;
    private String nivel;
    private String descricao;
    private int curriculoId;

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

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCurriculoId() {
        return curriculoId;
    }

    public void setCurriculoId(int curriculoId) {
        this.curriculoId = curriculoId;
    }

    public Habilidade() {
    }

    public Habilidade(int id, String nome, String nivel,
                      String descricao, int curriculoId) {

        this.id = id;
        this.nome = nome;
        this.nivel = nivel;
        this.descricao = descricao;
        this.curriculoId = curriculoId;
    }
}

