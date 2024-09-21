package br.lks.quarkussocial.rest.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFollowerRequest {
    @NotNull(message = "Seguidor é obrigatório")
    private Long followerId;
}
