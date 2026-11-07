package impulsojob.model;

import java.sql.Date;

public class CartaApresentacao {

    private int id;
    private int clienteId;
    private String titulo;
    private String conteudo;
    private Date dataCriacao;
    private Date dataAtualizacao;

    public CartaApresentacao() {
    }

    public CartaApresentacao(int id, int clienteId, String titulo, String conteudo, Date dataCriacao, Date dataAtualizacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public String toString() {
        return titulo != null ? titulo : "Carta #" + id;
    }
}
