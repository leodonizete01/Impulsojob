package impulsojob.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gerenciador de conexão com o banco de dados PostgreSQL Neon.
 * Carrega credenciais de forma segura através do arquivo .env e variáveis de ambiente.
 */
public class Banco {

    // Configurações padrão seguras de fallback para o Neon PostgreSQL
    private static final String DEFAULT_URL = "jdbc:postgresql://ep-muddy-mountain-axj1l0pr-pooler.c-4.us-east-2.aws.neon.tech/neondb?sslmode=require&channelBinding=require";
    private static final String DEFAULT_USER = "neondb_owner";
    private static final String DEFAULT_SENHA = "npg_o7gYNLcMUd9p";

    public static String getUrl() {
        String url = EnvConfig.get("DB_URL", DEFAULT_URL);
        // Garante suporte a UTF-8 se não estiver explicitado
        if (!url.contains("characterEncoding=")) {
            url += (url.contains("?") ? "&" : "?") + "characterEncoding=UTF-8";
        }
        return url;
    }

    public static String getUser() {
        return EnvConfig.get("DB_USER", DEFAULT_USER);
    }

    public static String getSenha() {
        return EnvConfig.get("DB_PASSWORD", DEFAULT_SENHA);
    }

    /**
     * Testa a conexão com o banco de dados.
     */
    public static void conectar() throws SQLException {
        try (Connection con = getConnection()) {
            if (con != null && !con.isClosed()) {
                System.out.println("ImpulsoJob: Conexão com banco de dados estabelecida com sucesso.");
            }
        }
    }

    /**
     * Fornece uma nova conexão ativa com o PostgreSQL Neon.
     */
    public static Connection getConnection() throws SQLException {
        String url = getUrl();
        String user = getUser();
        String senha = getSenha();

        if (url == null || url.trim().isEmpty() || user == null || user.trim().isEmpty()) {
            throw new SQLException("Configurações do banco de dados ausentes. Verifique as variáveis DB_URL, DB_USER e DB_PASSWORD no seu arquivo .env.");
        }

        return DriverManager.getConnection(url, user, senha);
    }
}
