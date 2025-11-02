package ma.emsi.moughit;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.store.embedding.CosineSimilarity;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.output.Response;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Test3 {
    public static void main(String[] args) {
        // Replace "YOUR_GEMINI_API_KEY" with your actual Gemini API key
        String apiKey = System.getenv("GEMINI_KEY");

        EmbeddingModel embeddingModel = GoogleAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-embedding-001")
                .taskType(GoogleAiEmbeddingModel.TaskType.SEMANTIC_SIMILARITY)
                .outputDimensionality(300)
                .timeout(Duration.ofMillis(6000))
                .build();

        List<List<String>> sentencePairs = Arrays.asList(
                Arrays.asList("I like to eat pizza.", "Pizza is my favorite food."),
                Arrays.asList("The weather is nice today.", "It is sunny outside."),
                Arrays.asList("I love to code in Java.", "My cat is black."),
                Arrays.asList("What is your name?", "Can you tell me your name?")
        );

        for (List<String> pair : sentencePairs) {
            String sentence1 = pair.get(0);
            String sentence2 = pair.get(1);

            Response<Embedding> response1 = embeddingModel.embed(sentence1);
            Response<Embedding> response2 = embeddingModel.embed(sentence2);

            Embedding embedding1 = response1.content();
            Embedding embedding2 = response2.content();

            double similarity = CosineSimilarity.between(embedding1, embedding2);

            System.out.printf("'%s' vs '%s' -> Similarity: %f%n", sentence1, sentence2, similarity);
        }
    }
}