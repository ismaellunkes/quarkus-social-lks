package br.lks.quarkussocial.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "Usuário é obrigatório")
    @Size(min = 3, max = 20, message = "Usuário deve ter entre 3 e 20 caracteres")
    private String username;
    @NotNull(message = "Idade é obrigatório")
    private Integer age;
}
