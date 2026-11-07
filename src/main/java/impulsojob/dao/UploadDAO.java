package impulsojob.dao;

import impulsojob.config.Banco;
import impulsojob.model.Upload;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UploadDAO {

    public void cadastrar(Upload upload) throws SQLException {
        String sql = "INSERT INTO upload (cliente_id, nome_arquivo, caminho_arquivo, tipo_arquivo, data_upload) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, upload.getClienteId());
            stmt.setString(2, upload.getNomeArquivo());
            stmt.setString(3, upload.getCaminhoArquivo());
            stmt.setString(4, upload.getTipoArquivo());
            stmt.setDate(5, upload.getDataUpload() != null ? upload.getDataUpload() : new Date(System.currentTimeMillis()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    upload.setId(rs.getInt(1));
                }
            }
        }
    }

    public void excluir(int id, int clienteId) throws SQLException {
        String sql = "DELETE FROM upload WHERE id = ? AND cliente_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setInt(2, clienteId);
            stmt.executeUpdate();
        }
    }

    public List<Upload> listarPorCliente(int clienteId) throws SQLException {
        List<Upload> lista = new ArrayList<>();
        String sql = "SELECT * FROM upload WHERE cliente_id = ? ORDER BY data_upload DESC, id DESC";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Upload u = new Upload();
                    u.setId(rs.getInt("id"));
                    u.setClienteId(rs.getInt("cliente_id"));
                    u.setNomeArquivo(rs.getString("nome_arquivo"));
                    u.setCaminhoArquivo(rs.getString("caminho_arquivo"));
                    u.setTipoArquivo(rs.getString("tipo_arquivo"));
                    u.setDataUpload(rs.getDate("data_upload"));
                    lista.add(u);
                }
            }
        }
        return lista;
    }

    public Upload buscarPorId(int id, int clienteId) throws SQLException {
        String sql = "SELECT * FROM upload WHERE id = ? AND cliente_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setInt(2, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Upload u = new Upload();
                    u.setId(rs.getInt("id"));
                    u.setClienteId(rs.getInt("cliente_id"));
                    u.setNomeArquivo(rs.getString("nome_arquivo"));
                    u.setCaminhoArquivo(rs.getString("caminho_arquivo"));
                    u.setTipoArquivo(rs.getString("tipo_arquivo"));
                    u.setDataUpload(rs.getDate("data_upload"));
                    return u;
                }
            }
        }
        return null;
    }
}
