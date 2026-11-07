package impulsojob.service;

import impulsojob.util.TextoUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TextoUtilTest {

    @Test
    public void testCorrecaoMojibake() {
        assertEquals("Clássico", TextoUtil.sanitizar("ClÃ¡ssico"));
        assertEquals("Aparência", TextoUtil.sanitizar("AparÃªncia"));
        assertEquals("seções", TextoUtil.sanitizar("seÃ§Ãµes"));
        assertEquals("conteúdo", TextoUtil.sanitizar("conteÃºdo"));
        
        // Textos já corretos não devem ser alterados
        assertEquals("Moderno", TextoUtil.sanitizar("Moderno"));
        assertEquals("Desenvolvedor Java", TextoUtil.sanitizar("Desenvolvedor Java"));
        assertEquals("Currículo Profissional", TextoUtil.sanitizar("Currículo Profissional"));
    }
}
