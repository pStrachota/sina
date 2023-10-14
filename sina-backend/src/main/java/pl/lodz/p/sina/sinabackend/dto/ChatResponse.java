package pl.lodz.p.sina.sinabackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private int index;
        private Message message;
    }
}
