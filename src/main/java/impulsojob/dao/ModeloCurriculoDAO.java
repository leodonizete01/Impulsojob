package impulsojob.dao;

import impulsojob.config.Banco;
import impulsojob.model.ModeloCurriculo;
import impulsojob.util.TextoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeloCurriculoDAO {

    public List<ModeloCurriculo> listar() throws SQLException {
        List<ModeloCurriculo> modelos = new ArrayList<>();
        String sql = "SELECT * FROM modelo_curriculo ORDER BY id ASC";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ModeloCurriculo m = new ModeloCurriculo();
                m.setId(rs.getInt("id"));
                m.setNome(TextoUtil.sanitizar(rs.getString("nome")));
                m.setDescricao(TextoUtil.sanitizar(rs.getString("descricao")));
                m.setArquivoModelo(rs.getString("arquivo_modelo"));
                modelos.add(m);
            }
        }
        return modelos;
    }

    public ModeloCurriculo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM modelo_curriculo WHERE id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ModeloCurriculo m = new ModeloCurriculo();
                    m.setId(rs.getInt("id"));
                    m.setNome(TextoUtil.sanitizar(rs.getString("nome")));
                    m.setDescricao(TextoUtil.sanitizar(rs.getString("descricao")));
                    m.setArquivoModelo(rs.getString("arquivo_modelo"));
                    return m;
                }
            }
        }
        return null;
    }
}
