package impulsojob.util;

import java.nio.charset.StandardCharsets;

/**
 * Utilitário para sanitização e correção de textos e problemas de codificação (Mojibake).
 */
public class TextoUtil {

    /**
     * Detecta e corrige sequências de caracteres corrompidos decorrentes de dupla codificação UTF-8 / Windows-1252.
     * Exemplo: 'ClÃ¡ssico' -> 'Clássico', 'AparÃªncia' -> 'Aparência', 'seÃ§Ãµes' -> 'seções'.
     */
    public static String sanitizar(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }

        // Se o texto contém sequências típicas de mojibake (como Ã seguido de caracteres especiais)
        if (contemMojibake(texto)) {
            try {
                // Converte os caracteres mal interpretados de volta para os bytes originais e decodifica como UTF-8
                String corrigido = new String(texto.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                // Verifica se a conversão produziu um texto válido sem caracteres de substituição
                if (!corrigido.contains("\uFFFD")) {
                    return corrigido;
                }
            } catch (Exception ignored) {
            }
        }

        return texto;
    }

    /**
     * Verifica se a string contém padrões comuns de dupla codificação UTF-8.
     */
    public static boolean contemMojibake(String texto) {
        if (texto == null) return false;
        // Padrão comum: caractere Ã (0xC3) ou Â (0xC2) seguido por bytes do intervalo UTF-8 (0x80 a 0xBF)
        for (int i = 0; i < texto.length() - 1; i++) {
            char c = texto.charAt(i);
            char prox = texto.charAt(i + 1);
            if ((c == '\u00C3' || c == '\u00C2') && (prox >= '\u0080' && prox <= '\u00BF')) {
                return true;
            }
        }
        return false;
    }
}
