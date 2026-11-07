package impulsojob.dao;

import impulsojob.config.Banco;
import impulsojob.model.Curriculo;
import impulsojob.util.TextoUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CurriculoDAO {

    private final ExperienciaDAO experienciaDAO = new ExperienciaDAO();
    private final FormacaoDAO formacaoDAO = new FormacaoDAO();
    private final HabilidadeDAO habilidadeDAO = new HabilidadeDAO();
    private final IdiomaDAO idiomaDAO = new IdiomaDAO();

    public void cadastrar(Curriculo curriculo) throws SQLException {
        String sql = "INSERT INTO curriculo (nome, cliente_id, modelo_id, objetivo, resumo, cidade, data_atualizacao) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, curriculo.getNome());
            stmt.setInt(2, curriculo.getClienteId());
            stmt.setInt(3, curriculo.getModeloId() > 0 ? curriculo.getModeloId() : 1);
            stmt.setString(4, curriculo.getObjetivo());
            stmt.setString(5, curriculo.getResumo());
            stmt.setString(6, curriculo.getCidade());
            stmt.setDate(7, new Date(System.currentTimeMillis()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    curriculo.setId(rs.getInt(1));
                }
            }
        }

        // Salva itens relacionados
        if (curriculo.getId() > 0) {
            experienciaDAO.salvarTodos(curriculo.getId(), curriculo.getExperiencias());
            formacaoDAO.salvarTodos(curriculo.getId(), curriculo.getFormacoes());
            habilidadeDAO.salvarTodos(curriculo.getId(), curriculo.getHabilidades());
            idiomaDAO.salvarTodos(curriculo.getId(), curriculo.getIdiomas());
        }
    }

    public void atualizar(Curriculo curriculo) throws SQLException {
        String sql = "UPDATE curriculo SET nome = ?, modelo_id = ?, objetivo = ?, resumo = ?, cidade = ?, data_atualizacao = ? WHERE id = ? AND cliente_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, curriculo.getNome());
            stmt.setInt(2, curriculo.getModeloId() > 0 ? curriculo.getModeloId() : 1);
            stmt.setString(3, curriculo.getObjetivo());
            stmt.setString(4, curriculo.getResumo());
            stmt.setString(5, curriculo.getCidade());
            stmt.setDate(6, new Date(System.currentTimeMillis()));
            stmt.setInt(7, curriculo.getId());
            stmt.setInt(8, curriculo.getClienteId());

            stmt.executeUpdate();
        }

        experienciaDAO.salvarTodos(curriculo.getId(), curriculo.getExperiencias());
        formacaoDAO.salvarTodos(curriculo.getId(), curriculo.getFormacoes());
        habilidadeDAO.salvarTodos(curriculo.getId(), curriculo.getHabilidades());
        idiomaDAO.salvarTodos(curriculo.getId(), curriculo.getIdiomas());
    }

    public void excluir(int id, int clienteId) throws SQLException {
        // Exclui dependências primeiro
        experienciaDAO.excluirPorCurriculo(id);
        formacaoDAO.excluirPorCurriculo(id);
        habilidadeDAO.excluirPorCurriculo(id);
        idiomaDAO.excluirPorCurriculo(id);

        String sql = "DELETE FROM curriculo WHERE id = ? AND cliente_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setInt(2, clienteId);
            stmt.executeUpdate();
        }
    }

    public Curriculo buscarPorId(int id, int clienteId) throws SQLException {
        String sql = "SELECT * FROM curriculo WHERE id = ? AND cliente_id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setInt(2, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Curriculo c = new Curriculo();
                    c.setId(rs.getInt("id"));
                    c.setNome(TextoUtil.sanitizar(rs.getString("nome")));
                    c.setClienteId(rs.getInt("cliente_id"));
                    c.setModeloId(rs.getInt("modelo_id"));
                    c.setObjetivo(TextoUtil.sanitizar(rs.getString("objetivo")));
                    c.setResumo(TextoUtil.sanitizar(rs.getString("resumo")));
                    c.setCidade(TextoUtil.sanitizar(rs.getString("cidade")));
                    c.setDataAtualizacao(rs.getDate("data_atualizacao"));

                    // Carrega coleções filhas
                    c.setExperiencias(experienciaDAO.listarPorCurriculo(c.getId()));
                    c.setFormacoes(formacaoDAO.listarPorCurriculo(c.getId()));
                    c.setHabilidades(habilidadeDAO.listarPorCurriculo(c.getId()));
                    c.setIdiomas(idiomaDAO.listarPorCurriculo(c.getId()));

                    return c;
                }
            }
        }
        return null;
    }

    public List<Curriculo> listarPorCliente(int clienteId) throws SQLException {
        List<Curriculo> curriculos = new ArrayList<>();
        String sql = "SELECT * FROM curriculo WHERE cliente_id = ? ORDER BY data_atualizacao DESC, id DESC";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Curriculo c = new Curriculo();
                    c.setId(rs.getInt("id"));
                    c.setNome(TextoUtil.sanitizar(rs.getString("nome")));
                    c.setClienteId(rs.getInt("cliente_id"));
                    c.setModeloId(rs.getInt("modelo_id"));
                    c.setObjetivo(TextoUtil.sanitizar(rs.getString("objetivo")));
                    c.setResumo(TextoUtil.sanitizar(rs.getString("resumo")));
                    c.setCidade(TextoUtil.sanitizar(rs.getString("cidade")));
                    c.setDataAtualizacao(rs.getDate("data_atualizacao"));
                    curriculos.add(c);
                }
            }
        }
        return curriculos;
    }

    public List<Curriculo> listar() throws SQLException {
        List<Curriculo> curriculos = new ArrayList<>();
        String sql = "SELECT * FROM curriculo ORDER BY id DESC";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Curriculo c = new Curriculo();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setClienteId(rs.getInt("cliente_id"));
                c.setModeloId(rs.getInt("modelo_id"));
                c.setObjetivo(rs.getString("objetivo"));
                c.setResumo(rs.getString("resumo"));
                c.setCidade(rs.getString("cidade"));
                c.setDataAtualizacao(rs.getDate("data_atualizacao"));
                curriculos.add(c);
            }
        }
        return curriculos;
    }
}
