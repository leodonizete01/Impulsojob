package impulsojob.config;

import impulsojob.model.Cliente;

/**
 * Gerenciador de sessão do usuário logado na aplicação.
 */
public class SessaoUsuario {

    private static Cliente usuarioLogado;

    public static synchronized void iniciarSessao(Cliente cliente) {
        usuarioLogado = cliente;
    }

    public static synchronized Cliente getUsuarioLogado() {
        return usuarioLogado;
    }

    public static synchronized boolean isAutenticado() {
        return usuarioLogado != null;
    }

    public static synchronized void encerrarSessao() {
        usuarioLogado = null;
    }
}
