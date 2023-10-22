package com.chessCave.chessApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "puzzles")
public class Puzzle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "This field cannot be blank!")
    private String name;
    private String fen;
    @Length(max = 250, message = "Too long! Max 250 characters")
    @NotBlank(message = "This field cannot be blank!")
    private String answer;
}
