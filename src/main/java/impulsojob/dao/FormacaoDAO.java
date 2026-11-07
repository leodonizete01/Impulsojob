package impulsojob.dao;

import impulsojob.config.Banco;
import impulsojob.model.Formacao;
import impulsojob.util.TextoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormacaoDAO {

    public void salvarTodos(int curriculoId, List<Formacao> formacoes) throws SQLException {
        excluirPorCurriculo(curriculoId);

        if (formacoes == null || formacoes.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO formacao (curso, instituicao, data_inicio, data_fim, descricao, curriculo_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            for (Formacao f : formacoes) {
                stmt.setString(1, f.getCurso());
                stmt.setString(2, f.getInstituicao());
                stmt.setDate(3, f.getDataInicio());
                stmt.setDate(4, f.getDataFim());
                stmt.setString(5, f.getDescricao());
                stmt.setInt(6, curriculoId);
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    public List<Formacao> listarPorCurriculo(int curriculoId) throws SQLException {
        List<Formacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM formacao WHERE curriculo_id = ? ORDER BY id ASC";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, curriculoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Formacao f = new Formacao();
                    f.setId(rs.getInt("id"));
                    f.setCurso(TextoUtil.sanitizar(rs.getString("curso")));
                    f.setInstituicao(TextoUtil.sanitizar(rs.getString("instituicao")));
                    f.setDataInicio(rs.getDate("data_inicio"));
                    f.setDataFim(rs.getDate("data_fim"));
                    f.setDescricao(TextoUtil.sanitizar(rs.getString("descricao")));
                    f.setCurriculoId(rs.getInt("curriculo_id"));
                    lista.add(f);
                }
            }
        }
        return lista;
    }

    public void excluirPorCurriculo(int curriculoId) throws SQLException {
        String sql = "DELETE FROM formacao WHERE curriculo_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, curriculoId);
            stmt.executeUpdate();
        }
    }
}
