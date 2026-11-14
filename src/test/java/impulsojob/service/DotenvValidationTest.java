package impulsojob.service;

import impulsojob.config.Banco;
import impulsojob.config.EnvConfig;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class DotenvValidationTest {

    @Test
    public void testDotenvCarregamentoDireto() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        assertNotNull(dotenv);
        
        String dbUser = dotenv.get("DB_USER");
        assertNotNull(dbUser);
        assertEquals("neondb_owner", dbUser);
    }

    @Test
    public void testEnvConfigIntegrado() {
        String dbUser = EnvConfig.get("DB_USER");
        assertNotNull(dbUser);
        assertEquals("neondb_owner", dbUser);
        
        String dbUrl = EnvConfig.get("DB_URL");
        assertNotNull(dbUrl);
        assertTrue(dbUrl.startsWith("jdbc:postgresql://"));
        
        // Valida que EnvConfig retorna valor padrão para chave inexistente
        String inexistente = EnvConfig.get("VARIAVEL_COMPLETAMENTE_INEXISTENTE_XYZ", "padrao");
        assertEquals("padrao", inexistente);
        assertFalse(EnvConfig.has("VARIAVEL_COMPLETAMENTE_INEXISTENTE_XYZ"));
    }

    @Test
    public void testConexaoBancoPostgres() {
        assertDoesNotThrow(() -> {
            try (Connection conn = Banco.getConnection()) {
                assertNotNull(conn);
                assertFalse(conn.isClosed());
            }
        });
    }
}

