package com.chessCave.chessApp.service.impl;

import com.chessCave.chessApp.dto.PuzzleDto;
import com.chessCave.chessApp.entity.Puzzle;
import com.chessCave.chessApp.mapper.PuzzleMapper;
import com.chessCave.chessApp.repository.PuzzleRepository;
import com.chessCave.chessApp.service.PuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PuzzleServiceImpl implements PuzzleService {
    @Autowired
    private PuzzleRepository puzzleRepository;
    //
    private Map<Character, String> piecesHtml = Map.ofEntries(
            Map.entry('P', "<i class=\"fa-regular fa-chess-pawn fa-2xl\" style=\"color: #ffffff;\"></i>"),
            Map.entry('p', "<i class=\"fa-regular fa-chess-pawn fa-2xl\"></i>"),
            Map.entry('K', "<i class=\"fa-regular fa-chess-king fa-2xl\" style=\"color: #ffffff;\"></i>"),
            Map.entry('k', "<i class=\"fa-regular fa-chess-king fa-2xl\"></i>"),
            Map.entry('Q', "<i class=\"fa-regular fa-chess-queen fa-2xl\" style=\"color: #ffffff;\"></i>"),
            Map.entry('q', "<i class=\"fa-regular fa-chess-queen fa-2xl\"></i>"),
            Map.entry('N', "<i class=\"fa-regular fa-chess-knight fa-2xl\" style=\"color: #ffffff;\"></i>"),
            Map.entry('n', "<i class=\"fa-regular fa-chess-knight fa-2xl\"></i>"),
            Map.entry('B', "<i class=\"fa-regular fa-chess-bishop fa-2xl\" style=\"color: #ffffff;\"></i>"),
            Map.entry('b', "<i class=\"fa-regular fa-chess-bishop fa-2xl\"></i>"),
            Map.entry('R', "<i class=\"fa-regular fa-chess-rook fa-2xl\" style=\"color: #ffffff;\"></i>"),
            Map.entry('r', "<i class=\"fa-regular fa-chess-rook fa-2xl\"></i>")
    );


    @Override
    public PuzzleDto getPuzzleDto(Long id) {
        return PuzzleMapper.mapToPuzzleDto
                (puzzleRepository.findById(id).get(), piecesHtml);
    }

    @Override
    public List<Long> getIndexList() {
        List<Puzzle> puzzles = puzzleRepository.findAll();
        List<Long> indexes = new ArrayList<>();
        for (Puzzle puzzle : puzzles) {
            indexes.add(puzzle.getId());
        }
        return indexes;
    }

    @Override
    public List<PuzzleDto> getAllPuzzleDtos() {
        List<Puzzle> puzzles = puzzleRepository.findAll();
        return puzzles.stream().map((puzzle) -> PuzzleMapper.mapToPuzzleDto(puzzle, piecesHtml))
                .collect(Collectors.toList());
    }

    @Override
    public List<Puzzle> getAllPuzzles() {
        return puzzleRepository.findAll();
    }

    @Override
    public void savePuzzle(Puzzle puzzle) {
        puzzleRepository.save(puzzle);
    }

    @Override
    public void deletePuzzle(Long id) {
        puzzleRepository.deleteById(id);

    }

    // check if the board the fen is setting up has 64 squares and if it contains only legal characters
    @Override
    public boolean isFenInvalid(String fen) {
        // variable to count the number of squares on the board
        int count = 0;
        System.out.println(count);
        for (Character character : fen.toCharArray()) {
            // if the character is "/" - ignore it
            if (character == 47) {
                continue;

                // if character is a type of piece, add one to the count of squares
            } else if (piecesHtml.containsKey(character)) {
                count++;

                // if character is a number add the value of the number to the count of squares
            } else if (character <= 56 && character >= 49) {
                count += character - 48;

                // if there's any other character return true as the fen is not valid
            } else  {
                return true;
            }
        }

        // if the number of squares on the board is 64 return false as the fen is valid, else return true
        if (count == 64) {
            return false;
        } else {
            return true;
        }
    }
}
