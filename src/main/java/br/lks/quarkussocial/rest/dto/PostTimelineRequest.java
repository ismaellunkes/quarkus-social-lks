package br.lks.quarkussocial.rest.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTimelineRequest {
    private String userName;
    private String content;
}
