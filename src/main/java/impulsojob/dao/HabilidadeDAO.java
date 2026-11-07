package impulsojob.dao;

import impulsojob.config.Banco;
import impulsojob.model.Habilidade;
import impulsojob.util.TextoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabilidadeDAO {

    public void salvarTodos(int curriculoId, List<Habilidade> habilidades) throws SQLException {
        excluirPorCurriculo(curriculoId);

        if (habilidades == null || habilidades.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO habilidade (nome, nivel, descricao, curriculo_id) VALUES (?, ?, ?, ?)";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            for (Habilidade hab : habilidades) {
                stmt.setString(1, hab.getNome());
                stmt.setString(2, hab.getNivel());
                stmt.setString(3, hab.getDescricao());
                stmt.setInt(4, curriculoId);
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    public List<Habilidade> listarPorCurriculo(int curriculoId) throws SQLException {
        List<Habilidade> lista = new ArrayList<>();
        String sql = "SELECT * FROM habilidade WHERE curriculo_id = ? ORDER BY id ASC";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, curriculoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Habilidade hab = new Habilidade();
                    hab.setId(rs.getInt("id"));
                    hab.setNome(TextoUtil.sanitizar(rs.getString("nome")));
                    hab.setNivel(TextoUtil.sanitizar(rs.getString("nivel")));
                    hab.setDescricao(TextoUtil.sanitizar(rs.getString("descricao")));
                    hab.setCurriculoId(rs.getInt("curriculo_id"));
                    lista.add(hab);
                }
            }
        }
        return lista;
    }

    public void excluirPorCurriculo(int curriculoId) throws SQLException {
        String sql = "DELETE FROM habilidade WHERE curriculo_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, curriculoId);
            stmt.executeUpdate();
        }
    }
}
