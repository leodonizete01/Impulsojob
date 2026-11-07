package impulsojob.service;

import impulsojob.dao.ClienteDAO;
import impulsojob.model.Cliente;
import impulsojob.util.HashUtil;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class ClienteService {

    private final ClienteDAO clienteDAO;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    /**
     * Valida os dados, gera o hash e cadastra um novo usuário.
     */
    public Cliente cadastrar(Cliente cliente, String senha, String confirmacaoSenha) throws Exception {
        if (cliente == null) {
            throw new IllegalArgumentException("Dados do cliente não informados.");
        }
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome completo é obrigatório.");
        }
        if (cliente.getEmail() == null || !EMAIL_PATTERN.matcher(cliente.getEmail().trim()).matches()) {
            throw new IllegalArgumentException("E-mail inválido ou não informado.");
        }
        if (cliente.getCpf() == null || cliente.getCpf().trim().replaceAll("[^0-9]", "").length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }
        if (senha == null || senha.length() < 4) {
            throw new IllegalArgumentException("A senha deve possuir no mínimo 4 caracteres.");
        }
        if (!senha.equals(confirmacaoSenha)) {
            throw new IllegalArgumentException("A confirmação de senha não coincide com a senha digitada.");
        }

        // Verifica duplicidade de E-mail
        if (clienteDAO.buscarPorEmail(cliente.getEmail()) != null) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail.");
        }

        // Verifica duplicidade de CPF
        if (clienteDAO.buscarPorCpf(cliente.getCpf()) != null) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este CPF.");
        }

        // Gera hash seguro
        String hash = HashUtil.hashSenha(senha);
        cliente.setSenha(hash);

        clienteDAO.cadastrar(cliente);
        return cliente;
    }

    /**
     * Autentica usuário por E-mail ou CPF e senha.
     */
    public Cliente autenticar(String login, String senha) throws Exception {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o e-mail ou CPF para entrar.");
        }
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Informe sua senha.");
        }

        Cliente cliente = clienteDAO.autenticar(login, senha);
        if (cliente == null) {
            throw new IllegalArgumentException("Credenciais inválidas. Verifique seu login e senha.");
        }
        return cliente;
    }

    /**
     * Atualiza dados cadastrais do cliente logado.
     */
    public void atualizarPerfil(Cliente cliente) throws Exception {
        if (cliente == null || cliente.getId() <= 0) {
            throw new IllegalArgumentException("Usuário inválido para atualização.");
        }
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome completo não pode ficar vazio.");
        }
        if (cliente.getEmail() == null || !EMAIL_PATTERN.matcher(cliente.getEmail().trim()).matches()) {
            throw new IllegalArgumentException("E-mail inválido.");
        }

        // Verifica se outro cliente já usa o mesmo e-mail
        Cliente existenteEmail = clienteDAO.buscarPorEmail(cliente.getEmail());
        if (existenteEmail != null && existenteEmail.getId() != cliente.getId()) {
            throw new IllegalArgumentException("Este e-mail já está em uso por outro usuário.");
        }

        // Verifica se outro cliente já usa o mesmo CPF
        if (cliente.getCpf() != null && !cliente.getCpf().trim().isEmpty()) {
            Cliente existenteCpf = clienteDAO.buscarPorCpf(cliente.getCpf());
            if (existenteCpf != null && existenteCpf.getId() != cliente.getId()) {
                throw new IllegalArgumentException("Este CPF já está em uso por outro usuário.");
            }
        }

        clienteDAO.atualizar(cliente);
    }

    /**
     * Altera a senha do cliente.
     */
    public void alterarSenha(int clienteId, String senhaAtual, String novaSenha, String confirmacao) throws Exception {
        Cliente c = clienteDAO.buscarPorId(clienteId);
        if (c == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        if (!HashUtil.verificarSenha(senhaAtual, c.getSenha())) {
            throw new IllegalArgumentException("A senha atual informada está incorreta.");
        }
        if (novaSenha == null || novaSenha.length() < 4) {
            throw new IllegalArgumentException("A nova senha deve ter no mínimo 4 caracteres.");
        }
        if (!novaSenha.equals(confirmacao)) {
            throw new IllegalArgumentException("A confirmação da nova senha não coincide.");
        }

        String novoHash = HashUtil.hashSenha(novaSenha);
        clienteDAO.atualizarSenha(clienteId, novoHash);
    }
}
