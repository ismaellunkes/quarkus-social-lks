package br.lks.quarkussocial.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "followers")
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // O usuário que está sendo seguido

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower; // O usuário que segue

    @Column(name = "datahora_inicio")
    private LocalDateTime dataHoraInicio;


}
