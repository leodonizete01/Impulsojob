package impulsojob.dao;

import impulsojob.config.Banco;
import impulsojob.model.Experiencia;
import impulsojob.util.TextoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExperienciaDAO {

    public void salvarTodos(int curriculoId, List<Experiencia> experiencias) throws SQLException {
        excluirPorCurriculo(curriculoId);

        if (experiencias == null || experiencias.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO experiencia (cargo, empresa, data_inicio, data_fim, descricao, curriculo_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            for (Experiencia exp : experiencias) {
                stmt.setString(1, exp.getCargo());
                stmt.setString(2, exp.getEmpresa());
                stmt.setDate(3, exp.getDataInicio());
                stmt.setDate(4, exp.getDataFim());
                stmt.setString(5, exp.getDescricao());
                stmt.setInt(6, curriculoId);
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    public List<Experiencia> listarPorCurriculo(int curriculoId) throws SQLException {
        List<Experiencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM experiencia WHERE curriculo_id = ? ORDER BY id ASC";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, curriculoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Experiencia exp = new Experiencia();
                    exp.setId(rs.getInt("id"));
                    exp.setCargo(TextoUtil.sanitizar(rs.getString("cargo")));
                    exp.setEmpresa(TextoUtil.sanitizar(rs.getString("empresa")));
                    exp.setDataInicio(rs.getDate("data_inicio"));
                    exp.setDataFim(rs.getDate("data_fim"));
                    exp.setDescricao(TextoUtil.sanitizar(rs.getString("descricao")));
                    exp.setCurriculoId(rs.getInt("curriculo_id"));
                    lista.add(exp);
                }
            }
        }
        return lista;
    }

    public void excluirPorCurriculo(int curriculoId) throws SQLException {
        String sql = "DELETE FROM experiencia WHERE curriculo_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, curriculoId);
            stmt.executeUpdate();
        }
    }
}
