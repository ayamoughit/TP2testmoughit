package ma.emsi.moughit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test2Test {

    @Test
    public void testTranslateText() {
        String textToTranslate = "Bonjour le monde!";
        String translatedText = Test2.translateText(textToTranslate);
        System.out.println("Translated text: " + translatedText);
        assertNotNull(translatedText);
        assertTrue(translatedText.toLowerCase().contains("hello world") || translatedText.toLowerCase().contains("hello, world"));
    }
}