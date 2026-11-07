package impulsojob.service;

import impulsojob.config.SessaoUsuario;
import impulsojob.model.Cliente;
import impulsojob.model.Curriculo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurriculoEPDFTest {

    private Cliente clienteTeste;

    @BeforeEach
    public void setup() {
        clienteTeste = new Cliente(1, "Leonardo Donizete", "123.456.789-00", "(11) 98765-4321", "leonardo@email.com");
        SessaoUsuario.iniciarSessao(clienteTeste);
    }

    @AfterEach
    public void cleanup() {
        SessaoUsuario.encerrarSessao();
    }

    @Test
    public void testValidacaoCurriculoService() {
        CurriculoService service = new CurriculoService();

        Curriculo cInvalido = new Curriculo();
        cInvalido.setNome(""); // Nome vazio

        assertThrows(IllegalArgumentException.class, () -> {
            service.salvar(cInvalido);
        });

        SessaoUsuario.encerrarSessao();
        Curriculo cSemSessao = new Curriculo();
        cSemSessao.setNome("Meu Curriculo");

        assertThrows(IllegalStateException.class, () -> {
            service.salvar(cSemSessao);
        });
    }
}
