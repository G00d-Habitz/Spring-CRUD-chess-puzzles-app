package com.chessCave.chessApp.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PuzzleDto {
    private Long id;
    private String name;
    private List<String> fen;
    private String answer;
}
