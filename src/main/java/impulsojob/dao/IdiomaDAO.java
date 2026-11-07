package impulsojob.dao;

import impulsojob.config.Banco;
import impulsojob.model.Idioma;
import impulsojob.util.TextoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IdiomaDAO {

    public void salvarTodos(int curriculoId, List<Idioma> idiomas) throws SQLException {
        excluirPorCurriculo(curriculoId);

        if (idiomas == null || idiomas.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO idioma (nome, nivel, curriculo_id) VALUES (?, ?, ?)";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            for (Idioma idi : idiomas) {
                stmt.setString(1, idi.getNome());
                stmt.setString(2, idi.getNivel());
                stmt.setInt(3, curriculoId);
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    public List<Idioma> listarPorCurriculo(int curriculoId) throws SQLException {
        List<Idioma> lista = new ArrayList<>();
        String sql = "SELECT * FROM idioma WHERE curriculo_id = ? ORDER BY id ASC";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, curriculoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Idioma idi = new Idioma();
                    idi.setId(rs.getInt("id"));
                    idi.setNome(TextoUtil.sanitizar(rs.getString("nome")));
                    idi.setNivel(TextoUtil.sanitizar(rs.getString("nivel")));
                    idi.setCurriculoId(rs.getInt("curriculo_id"));
                    lista.add(idi);
                }
            }
        }
        return lista;
    }

    public void excluirPorCurriculo(int curriculoId) throws SQLException {
        String sql = "DELETE FROM idioma WHERE curriculo_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, curriculoId);
            stmt.executeUpdate();
        }
    }
}
