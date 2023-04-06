package io.gowalk.gowalk.service.impl;

import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import io.gowalk.gowalk.dto.GuideTalk;
import io.gowalk.gowalk.dto.GuideTopic;
import io.gowalk.gowalk.service.GuideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenAIGuideService implements GuideService {
    private final OpenAiService openAiService;

    @Value("${app.openai.model}")
    private String model;

    @Value("${app.openai.system}")
    private String systemMessage;

    private ChatMessage systemMessageRequest;

    @PostConstruct
    void init() {
        systemMessageRequest = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
    }

    @Override
    public Mono<GuideTalk> tellAbout(GuideTopic topic) {
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), topic.topic());
        List<ChatMessage> assistantMessages = topic.previousMessages().stream()
                .map(conversation ->
                        List.of(
                                new ChatMessage(ChatMessageRole.ASSISTANT.value(), conversation.question()),
                                new ChatMessage(ChatMessageRole.ASSISTANT.value(), conversation.answer())))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        assistantMessages.add(0, systemMessageRequest);
        assistantMessages.add(chatMessage);

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(assistantMessages)
                .model(model)
                .build();
        return Mono.fromCallable(() -> openAiService.createChatCompletion(completionRequest))
                .map(chatCompletion -> chatCompletion.getChoices().get(0).getMessage())
                .map(message -> new GuideTalk(topic.topic(), message.getContent()));
    }
}
