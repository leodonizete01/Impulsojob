package impulsojob.util;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {

    /**
     * Gera um hash seguro BCrypt para a senha informada.
     */
    public static String hashSenha(String senhaPura) {
        if (senhaPura == null || senhaPura.isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        }
        return BCrypt.hashpw(senhaPura, BCrypt.gensalt(12));
    }

    /**
     * Valida se a senha em texto puro corresponde ao hash BCrypt armazenado.
     */
    public static boolean verificarSenha(String senhaPura, String hashArmazenado) {
        if (senhaPura == null || hashArmazenado == null || hashArmazenado.isEmpty()) {
            return false;
        }
        try {
            return BCrypt.checkpw(senhaPura, hashArmazenado);
        } catch (Exception e) {
            return false;
        }
    }
}
