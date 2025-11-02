package ma.emsi.moughit;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.nio.file.Paths;
import java.util.Scanner;




public class Test5 {

    public static void main(String[] args) {
        String apiKey = System.getenv("GEMINI_KEY");


        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-2.5-flash")
                .logRequests(true)
                .logResponses(true)
                .build();

        EmbeddingModel embeddingModel = GoogleAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("text-embedding-004")
                .build();

        String nomFichierPdf = "src/main/java/machine learning.pdf";
        System.out.println("Initialisation du RAG avec le document : " + nomFichierPdf);

        Document document;
        try {
            DocumentParser pdfParser = new ApachePdfBoxDocumentParser();
            document = FileSystemDocumentLoader.loadDocument(Paths.get(nomFichierPdf), pdfParser);
        } catch (Exception e) {
            System.err.println("ERREUR lors du chargement du document : " + nomFichierPdf);
            System.err.println("Vérifiez que le fichier est bien présent à la racine du projet.");
            e.printStackTrace();
            return;
        }

        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        System.out.println("Ingestion du document (découpage et création des embeddings)...");
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .documentSplitter(DocumentSplitters.recursive(500, 100))
                .build();

        ingestor.ingest(document);
        System.out.println("Ingestion terminée.");

        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(3)
                .build();

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(contentRetriever)
                .build();


        System.out.println("--- Assistant RAG initialisé et prêt ---");
        System.out.println("Posez vos questions sur le document. Tapez 'exit' pour quitter.");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("\nVotre question : ");
                String userQuery = scanner.nextLine();

                if (userQuery == null || "exit".equalsIgnoreCase(userQuery.trim())) {
                    System.out.println("Au revoir !");
                    break;
                }

                if (userQuery.trim().isEmpty()) {
                    continue;
                }

                System.out.println("\nAssistant (recherche et génération...) :");

                String response = assistant.chat(userQuery);

                System.out.println(response);
            }
        }
    }
}

