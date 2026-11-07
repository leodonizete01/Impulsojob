package impulsojob.dao;

import impulsojob.config.Banco;
import impulsojob.model.CartaApresentacao;
import impulsojob.util.TextoUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartaApresentacaoDAO {

    public void cadastrar(CartaApresentacao carta) throws SQLException {
        String sql = "INSERT INTO cartaapresentacao (cliente_id, titulo, conteudo, data_criacao, data_atualizacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, carta.getClienteId());
            stmt.setString(2, carta.getTitulo());
            stmt.setString(3, carta.getConteudo());
            Date agora = new Date(System.currentTimeMillis());
            stmt.setDate(4, agora);
            stmt.setDate(5, agora);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    carta.setId(rs.getInt(1));
                }
            }
        }
    }

    public void atualizar(CartaApresentacao carta) throws SQLException {
        String sql = "UPDATE cartaapresentacao SET titulo = ?, conteudo = ?, data_atualizacao = ? WHERE id = ? AND cliente_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, carta.getTitulo());
            stmt.setString(2, carta.getConteudo());
            stmt.setDate(3, new Date(System.currentTimeMillis()));
            stmt.setInt(4, carta.getId());
            stmt.setInt(5, carta.getClienteId());

            stmt.executeUpdate();
        }
    }

    public void excluir(int id, int clienteId) throws SQLException {
        String sql = "DELETE FROM cartaapresentacao WHERE id = ? AND cliente_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setInt(2, clienteId);
            stmt.executeUpdate();
        }
    }

    public CartaApresentacao buscarPorId(int id, int clienteId) throws SQLException {
        String sql = "SELECT * FROM cartaapresentacao WHERE id = ? AND cliente_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setInt(2, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CartaApresentacao c = new CartaApresentacao();
                    c.setId(rs.getInt("id"));
                    c.setClienteId(rs.getInt("cliente_id"));
                    c.setTitulo(TextoUtil.sanitizar(rs.getString("titulo")));
                    c.setConteudo(TextoUtil.sanitizar(rs.getString("conteudo")));
                    c.setDataCriacao(rs.getDate("data_criacao"));
                    c.setDataAtualizacao(rs.getDate("data_atualizacao"));
                    return c;
                }
            }
        }
        return null;
    }

    public List<CartaApresentacao> listarPorCliente(int clienteId) throws SQLException {
        List<CartaApresentacao> cartas = new ArrayList<>();
        String sql = "SELECT * FROM cartaapresentacao WHERE cliente_id = ? ORDER BY data_atualizacao DESC, id DESC";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartaApresentacao c = new CartaApresentacao();
                    c.setId(rs.getInt("id"));
                    c.setClienteId(rs.getInt("cliente_id"));
                    c.setTitulo(TextoUtil.sanitizar(rs.getString("titulo")));
                    c.setConteudo(TextoUtil.sanitizar(rs.getString("conteudo")));
                    c.setDataCriacao(rs.getDate("data_criacao"));
                    c.setDataAtualizacao(rs.getDate("data_atualizacao"));
                    cartas.add(c);
                }
            }
        }
        return cartas;
    }
}
