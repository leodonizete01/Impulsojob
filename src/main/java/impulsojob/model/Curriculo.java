package impulsojob.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Curriculo {

    private int id;
    private String nome;
    private int clienteId;
    private int modeloId;
    private String objetivo;
    private String resumo;
    private String cidade;
    private Date dataAtualizacao;

    private List<Experiencia> experiencias = new ArrayList<>();
    private List<Formacao> formacoes = new ArrayList<>();
    private List<Habilidade> habilidades = new ArrayList<>();
    private List<Idioma> idiomas = new ArrayList<>();

    public Curriculo() {
    }

    public Curriculo(int id, String nome, int clienteId, int modeloId) {
        this.id = id;
        this.nome = nome;
        this.clienteId = clienteId;
        this.modeloId = modeloId;
    }

    public Curriculo(int id, String nome, int clienteId, int modeloId, String objetivo, String resumo, String cidade) {
        this.id = id;
        this.nome = nome;
        this.clienteId = clienteId;
        this.modeloId = modeloId;
        this.objetivo = objetivo;
        this.resumo = resumo;
        this.cidade = cidade;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

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

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public List<Experiencia> getExperiencias() {
        return experiencias;
    }

    public void setExperiencias(List<Experiencia> experiencias) {
        this.experiencias = experiencias != null ? experiencias : new ArrayList<>();
    }

    public List<Formacao> getFormacoes() {
        return formacoes;
    }

    public void setFormacoes(List<Formacao> formacoes) {
        this.formacoes = formacoes != null ? formacoes : new ArrayList<>();
    }

    public List<Habilidade> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Habilidade> habilidades) {
        this.habilidades = habilidades != null ? habilidades : new ArrayList<>();
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas != null ? idiomas : new ArrayList<>();
    }

    @Override
    public String toString() {
        return nome != null ? nome : "Currículo #" + id;
    }
}
