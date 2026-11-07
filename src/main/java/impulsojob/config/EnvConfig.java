package impulsojob.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;

/**
 * Utilitário centralizado para carregamento seguro de variáveis de ambiente e arquivo .env.
 * Compatível com execução via NetBeans, Gradle e empacotamento JAR.
 */
public class EnvConfig {

    private static Dotenv dotenv;
    private static boolean inicializado = false;

    private static synchronized void inicializar() {
        if (inicializado) return;

        // Lista de diretórios prováveis onde o arquivo .env pode estar localizado
        String[] diretorios = new String[]{
                ".",
                "./",
                System.getProperty("user.dir", "."),
                new File(".").getAbsolutePath()
        };

        for (String dir : diretorios) {
            try {
                File arquivoEnv = new File(dir, ".env");
                if (arquivoEnv.exists() && arquivoEnv.isFile()) {
                    dotenv = Dotenv.configure()
                            .directory(dir)
                            .filename(".env")
                            .ignoreIfMissing()
                            .load();
                    break;
                }
            } catch (Exception ignored) {
            }
        }

        // Se ainda não inicializou, tenta a busca padrão do Dotenv
        if (dotenv == null) {
            try {
                dotenv = Dotenv.configure().ignoreIfMissing().load();
            } catch (Exception ignored) {
            }
        }

        inicializado = true;
    }

    /**
     * Obtém o valor de uma variável.
     * Ordem de prioridade:
     * 1. Variável de ambiente do Sistema Operacional (System.getenv)
     * 2. Arquivo .env local (Dotenv)
     * 3. Valor padrão (caso fornecido)
     */
    public static String get(String chave, String valorPadrao) {
        inicializar();

        // 1. Variável de ambiente do Sistema Operacional
        String valor = System.getenv(chave);
        if (valor != null && !valor.trim().isEmpty()) {
            return valor.trim();
        }

        // 2. Arquivo .env via Dotenv
        if (dotenv != null) {
            try {
                valor = dotenv.get(chave);
                if (valor != null && !valor.trim().isEmpty()) {
                    return valor.trim();
                }
            } catch (Exception ignored) {
            }
        }

        return valorPadrao;
    }

    public static String get(String chave) {
        return get(chave, null);
    }

    /**
     * Verifica se uma variável está preenchida sem exibir seu conteúdo secreto.
     */
    public static boolean has(String chave) {
        String val = get(chave);
        return val != null && !val.trim().isEmpty();
    }
}
