package com.chessCave.chessApp.repository;

import com.chessCave.chessApp.entity.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuzzleRepository extends JpaRepository<Puzzle, Long> {
}
