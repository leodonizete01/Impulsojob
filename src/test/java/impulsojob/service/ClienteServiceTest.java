package impulsojob.service;

import impulsojob.config.SessaoUsuario;
import impulsojob.model.Cliente;
import impulsojob.util.HashUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceTest {

    @BeforeEach
    @AfterEach
    public void setup() {
        SessaoUsuario.encerrarSessao();
    }

    @Test
    public void testHashUtil() {
        String senha = "SenhaSegura123!";
        String hash = HashUtil.hashSenha(senha);

        assertNotNull(hash);
        assertTrue(hash.startsWith("$2a$") || hash.startsWith("$2b$") || hash.startsWith("$2y$"));
        assertTrue(HashUtil.verificarSenha(senha, hash));
        assertFalse(HashUtil.verificarSenha("SenhaErrada", hash));
    }

    @Test
    public void testSessaoUsuario() {
        assertFalse(SessaoUsuario.isAutenticado());
        assertNull(SessaoUsuario.getUsuarioLogado());

        Cliente cliente = new Cliente(1, "Leonardo Teste", "11122233344", "11999998888", "leonardo@teste.com");
        SessaoUsuario.iniciarSessao(cliente);

        assertTrue(SessaoUsuario.isAutenticado());
        assertEquals(cliente, SessaoUsuario.getUsuarioLogado());

        SessaoUsuario.encerrarSessao();
        assertFalse(SessaoUsuario.isAutenticado());
        assertNull(SessaoUsuario.getUsuarioLogado());
    }

    @Test
    public void testValidacaoCamposObrigatoriosCadastro() {
        ClienteService service = new ClienteService();

        Cliente c1 = new Cliente();
        c1.setNome("");
        c1.setEmail("invalido@teste.com");
        c1.setCpf("11122233344");

        assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrar(c1, "1234", "1234");
        });

        Cliente c2 = new Cliente();
        c2.setNome("Teste Valido");
        c2.setEmail("email_invalido");
        c2.setCpf("11122233344");

        assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrar(c2, "1234", "1234");
        });

        Cliente c3 = new Cliente();
        c3.setNome("Teste Valido");
        c3.setEmail("valido@teste.com");
        c3.setCpf("123"); // CPF curto

        assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrar(c3, "1234", "1234");
        });

        Cliente c4 = new Cliente();
        c4.setNome("Teste Valido");
        c4.setEmail("valido@teste.com");
        c4.setCpf("11122233344");

        assertThrows(IllegalArgumentException.class, () -> {
            // Senhas divergentes
            service.cadastrar(c4, "1234", "9999");
        });
    }
}
