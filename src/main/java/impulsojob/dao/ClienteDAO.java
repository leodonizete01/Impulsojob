package impulsojob.dao;

import impulsojob.config.Banco;
import impulsojob.model.Cliente;
import impulsojob.util.TextoUtil;
import impulsojob.util.HashUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    /**
     * Cadastra um novo cliente com hash de senha no banco de dados.
     */
    public void cadastrar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, cpf, telefone, email, senha) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getSenha());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Autentica usuário por E-mail ou CPF e valida a senha usando BCrypt.
     */
    public Cliente autenticar(String login, String senhaPura) throws SQLException {
        if (login == null || login.trim().isEmpty() || senhaPura == null) {
            return null;
        }

        String sql = "SELECT * FROM cliente WHERE LOWER(email) = LOWER(?) OR REPLACE(REPLACE(cpf, '.', ''), '-', '') = REPLACE(REPLACE(?, '.', ''), '-', '')";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, login.trim());
            stmt.setString(2, login.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashBanco = rs.getString("senha");
                    // Validação de senha por BCrypt
                    if (HashUtil.verificarSenha(senhaPura, hashBanco)) {
                        return mapearCliente(rs);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Busca cliente por E-mail.
     */
    public Cliente buscarPorEmail(String email) throws SQLException {
        if (email == null) return null;
        String sql = "SELECT * FROM cliente WHERE LOWER(email) = LOWER(?)";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null;
    }

    /**
     * Busca cliente por CPF.
     */
    public Cliente buscarPorCpf(String cpf) throws SQLException {
        if (cpf == null) return null;
        String sql = "SELECT * FROM cliente WHERE REPLACE(REPLACE(cpf, '.', ''), '-', '') = REPLACE(REPLACE(?, '.', ''), '-', '')";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cpf.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null;
    }

    /**
     * Busca cliente por ID.
     */
    public Cliente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null;
    }

    /**
     * Atualiza os dados cadastrais permitidos do cliente.
     */
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setInt(5, cliente.getId());

            stmt.executeUpdate();
        }
    }

    /**
     * Atualiza a senha do cliente.
     */
    public void atualizarSenha(int id, String novoHashSenha) throws SQLException {
        String sql = "UPDATE cliente SET senha = ? WHERE id = ?";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, novoHashSenha);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        }
    }

    /**
     * Lista todos os clientes.
     */
    public List<Cliente> listar() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY nome ASC";

        try (Connection con = Banco.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        }
        return clientes;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNome(TextoUtil.sanitizar(rs.getString("nome")));
        c.setCpf(rs.getString("cpf"));
        c.setTelefone(rs.getString("telefone"));
        c.setEmail(TextoUtil.sanitizar(rs.getString("email")));
        c.setSenha(rs.getString("senha"));
        return c;
    }
}
