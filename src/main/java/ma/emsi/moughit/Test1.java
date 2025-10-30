package ma.emsi.moughit;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

import java.time.Duration;

public class Test1 {

    public static void main(String[] args) {
        String response = chatWithGemini("quel est la capital d'italie");
        System.out.println(response);
    }

    public static String chatWithGemini(String message) {
        String cle = System.getenv("GEMINI_KEY");
        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-2.5-flash")
                .temperature(0.8)
                .timeout(Duration.ofSeconds(60))
                .responseFormat(ResponseFormat.JSON)
                .build();
        return model.chat(message);
    }
}
