package impulsojob.model;

import java.sql.Date;

public class Upload {

    private int id;
    private int clienteId;
    private String nomeArquivo;
    private String caminhoArquivo;
    private String tipoArquivo;
    private Date dataUpload;

    public Upload() {
    }

    public Upload(int id, int clienteId, String nomeArquivo, String caminhoArquivo, String tipoArquivo, Date dataUpload) {
        this.id = id;
        this.clienteId = clienteId;
        this.nomeArquivo = nomeArquivo;
        this.caminhoArquivo = caminhoArquivo;
        this.tipoArquivo = tipoArquivo;
        this.dataUpload = dataUpload;
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

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public Date getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(Date dataUpload) {
        this.dataUpload = dataUpload;
    }

    @Override
    public String toString() {
        return nomeArquivo != null ? nomeArquivo : "Arquivo #" + id;
    }
}
