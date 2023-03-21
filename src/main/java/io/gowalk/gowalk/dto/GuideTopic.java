package io.gowalk.gowalk.dto;

import java.util.List;

public record GuideTopic(String topic, List<Conversation> previousMessages) {
    public record Conversation(String question, String answer) {

    }
}
