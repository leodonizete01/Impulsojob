package impulsojob.model;

import java.sql.Date;

public class Experiencia {

    private int id;
    private String cargo;
    private String empresa;
    private Date dataInicio;
    private Date dataFim;
    private String descricao;
    private int curriculoId;

    public Experiencia() {
    }

    public Experiencia(int id, String cargo, String empresa,
                       Date dataInicio, Date dataFim,
                       String descricao, int curriculoId) {
        this.id = id;
        this.cargo = cargo;
        this.empresa = empresa;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.descricao = descricao;
        this.curriculoId = curriculoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
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
}