package ma.emsi.moughit;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;

import java.util.Scanner;

public class Test6 {
    public static void main(String[] args) {
        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(System.getenv("GEMINI_KEY"))
                .modelName("gemini-2.0-flash")
                .logRequests(true)
                .logResponses(true)
                .build();

        AssistantMeteo assistant = AiServices.builder(AssistantMeteo.class)
                .chatModel(model)
                .tools(new MeteoTool())
                .build();

        Scanner scanner = new Scanner(System.in);

        // Test 1: Weather of a valid city
        System.out.println("Entrez le nom d'une ville pour obtenir la météo : ");
        String city = scanner.nextLine();
        String question = "Quelle est la météo à " + city + " ?";
        String reponse = assistant.chat(question);
        System.out.println(reponse);

        // Test 2: Weather of a non-existent city
        System.out.println("\nEntrez le nom d'une ville qui n'existe pas : ");
        String nonExistentCity = scanner.nextLine();
        question = "Quelle est la météo à " + nonExistentCity + " ?";
        reponse = assistant.chat(question);
        System.out.println(reponse);

        // Test 3: A question not related to the weather
        System.out.println("\nPosez une question qui n'a rien à voir avec la météo : ");
        String otherQuestion = scanner.nextLine();
        reponse = assistant.chat(otherQuestion);
        System.out.println(reponse);
    }
}