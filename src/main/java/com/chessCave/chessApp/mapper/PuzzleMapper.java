package com.chessCave.chessApp.mapper;

import com.chessCave.chessApp.dto.PuzzleDto;
import com.chessCave.chessApp.entity.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PuzzleMapper {


    // Map puzzle to puzzleDto, which has a list of Strings for pieces instead of a FEN
    public static PuzzleDto mapToPuzzleDto(Puzzle puzzle, Map<Character, String> piecesHtml) {
        // create an Array to store all the pieces
        List<String> fen = new ArrayList<>();
        // for every character in the FEN code add a piece or a number of blank squares
        for (char element : puzzle.getFen().toCharArray()) {
            // if the character is "/" - ignore it
            if (element == 47) {
                continue;

                // if the character is a number = add this many blank squares
            } else if (element <= 56 && element >= 49) {
                for (int i = 0; i <= element-49; i++) {
                    fen.add(" ");
                }

                // only other possibility is it being a piece, so add a piece corresponding to this character
            } else {
                fen.add(piecesHtml.get(element));
            }
        }

        // return a built PuzzleDto object
        return PuzzleDto.builder()
                .id(puzzle.getId())
                .name(puzzle.getName())
                .fen(fen)
                .answer(puzzle.getAnswer())
                .build();
    }
}
