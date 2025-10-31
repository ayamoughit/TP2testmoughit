package ma.emsi.moughit;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Test1Test {

    @Test
    public void testGeminiChat() {
        String cle = System.getenv("GEMINI_KEY");
        ChatLanguageModel modele = GoogleAiGeminiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-pro") // Changed to gemini-pro for compatibility
                .build();
        String reponse = modele.chat("Explique moi le fonctionnement de langchain4j en 20 mots.");
        System.out.println(reponse);
        assertNotNull(reponse);
    }
}