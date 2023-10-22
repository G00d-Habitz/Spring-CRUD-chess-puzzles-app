package com.chessCave.chessApp.service;

import com.chessCave.chessApp.dto.PuzzleDto;
import com.chessCave.chessApp.entity.Puzzle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PuzzleService {

    List<PuzzleDto> getAllPuzzleDtos();
    List<Puzzle> getAllPuzzles();
    void savePuzzle(Puzzle puzzle);
    void deletePuzzle(Long id);
    PuzzleDto getPuzzleDto(Long id);
    List<Long> getIndexList();
    boolean isFenInvalid(String fen);
}
