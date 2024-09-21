package br.lks.quarkussocial.rest.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    @NotNull(message = "Usuário é obrigatório")
    private Long userId;

    @NotNull(message = "Post é obrigatório")
    @Size(min = 3, max = 180, message = "Post deve ter entre 3 e 180 caracteres")
    private String content;
}
