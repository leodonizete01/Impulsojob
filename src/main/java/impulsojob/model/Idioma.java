package impulsojob.model;

public class Idioma {

    private int id;
    private String nome;
    private String nivel;
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

    public int getCurriculoId() {
        return curriculoId;
    }

    public void setCurriculoId(int curriculoId) {
        this.curriculoId = curriculoId;
    }

    public Idioma() {
    }

    public Idioma(int id, String nome, String nivel, int curriculoId) {

        this.id = id;
        this.nome = nome;
        this.nivel = nivel;
        this.curriculoId = curriculoId;
    }
}

