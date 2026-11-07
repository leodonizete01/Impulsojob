package impulsojob.model;

public class Formacao {

    private int id;
    private String curso;
    private String instituicao;
    private java.sql.Date dataInicio;
    private java.sql.Date dataFim;
    private String descricao;
    private int curriculoId;    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public java.sql.Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(java.sql.Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public java.sql.Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(java.sql.Date dataFim) {
        this.dataFim = dataFim;
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

    public Formacao() {
    }

    public Formacao(int id, String curso, String instituicao,
                    java.sql.Date dataInicio, java.sql.Date dataFim,
                    String descricao, int curriculoId) {

        this.id = id;
        this.curso = curso;
        this.instituicao = instituicao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.descricao = descricao;
        this.curriculoId = curriculoId;
    }
}