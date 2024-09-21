package br.lks.quarkussocial.rest.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowerRequest {
    private Long id;
    private Long userId;
    private String userName;
}
