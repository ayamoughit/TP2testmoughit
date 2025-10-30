package ma.emsi.moughit;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class Test1 {

    public static void main(String[] args) {
        String response = chatWithGemini("Hello, Gemini!");
        System.out.println(response);
    }

    public static String chatWithGemini(String message) {
        String cle = System.getenv("GEMINI_KEY");
        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-pro")
                .build();
        return model.chat(message);
    }
}
