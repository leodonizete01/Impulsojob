package impulsojob.dao;

import impulsojob.config.Banco;
import impulsojob.model.Favorito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoritoDAO {

    public void adicionar(int clienteId, int modeloId) throws SQLException {
        if (isFavorito(clienteId, modeloId)) return;

        String sql = "INSERT INTO favorito (cliente_id, modelo_id) VALUES (?, ?)";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            stmt.setInt(2, modeloId);
            stmt.executeUpdate();
        }
    }

    public void remover(int clienteId, int modeloId) throws SQLException {
        String sql = "DELETE FROM favorito WHERE cliente_id = ? AND modelo_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            stmt.setInt(2, modeloId);
            stmt.executeUpdate();
        }
    }

    public boolean isFavorito(int clienteId, int modeloId) throws SQLException {
        String sql = "SELECT 1 FROM favorito WHERE cliente_id = ? AND modelo_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            stmt.setInt(2, modeloId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Favorito> listarPorCliente(int clienteId) throws SQLException {
        List<Favorito> lista = new ArrayList<>();
        String sql = "SELECT f.id, f.cliente_id, f.modelo_id, m.nome as modelo_nome, m.descricao as modelo_desc "
                   + "FROM favorito f "
                   + "INNER JOIN modelo_curriculo m ON f.modelo_id = m.id "
                   + "WHERE f.cliente_id = ? "
                   + "ORDER BY f.id DESC";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Favorito fav = new Favorito();
                    fav.setId(rs.getInt("id"));
                    fav.setClienteId(rs.getInt("cliente_id"));
                    fav.setModeloId(rs.getInt("modelo_id"));
                    fav.setModeloNome(rs.getString("modelo_nome"));
                    fav.setModeloDescricao(rs.getString("modelo_desc"));
                    lista.add(fav);
                }
            }
        }
        return lista;
    }
}
