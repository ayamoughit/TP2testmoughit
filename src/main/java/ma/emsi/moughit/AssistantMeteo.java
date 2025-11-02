package ma.emsi.moughit;

import dev.langchain4j.service.UserMessage;

public interface AssistantMeteo {
    String chat(@UserMessage String userMessage);
}