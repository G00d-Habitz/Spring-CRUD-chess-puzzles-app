package com.chessCave.chessApp.controller;

import com.chessCave.chessApp.dto.PuzzleDto;
import com.chessCave.chessApp.entity.Puzzle;
import com.chessCave.chessApp.service.PuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
public class PuzzleController {
    @Autowired
    private PuzzleService puzzleService;


    // displaying homepage and puzzles

    @GetMapping("/")
    public String blankBoard(Model model) {
        PuzzleDto puzzleDto = new PuzzleDto();
        List<String> fen = new ArrayList<>(Collections.nCopies(64, null));
        puzzleDto.setFen(fen);
        model.addAttribute("puzzle", puzzleDto);
        model.addAttribute("indexes", puzzleService.getIndexList());
        return "puzzle";
    }

    @GetMapping("/puzzle/{id}")
    public String puzzles(@PathVariable Long id, Model model) {
        model.addAttribute("puzzle", puzzleService.getPuzzleDto(id));
        model.addAttribute("indexes", puzzleService.getIndexList());
        return "puzzle";
    }



    //Admin side

    @GetMapping("/admin")
    public String blankBoardAdmin(Model model) {
        PuzzleDto puzzleDto = new PuzzleDto();
        List<String> fen = new ArrayList<>(Collections.nCopies(64, null));
        puzzleDto.setFen(fen);
        model.addAttribute("puzzle", puzzleDto);
        model.addAttribute("indexes", puzzleService.getIndexList());
        return "/admin/puzzle";
    }

    @GetMapping("/admin/puzzle/{id}")
    public String puzzlesAdmin(@PathVariable Long id, Model model) {
        model.addAttribute("puzzle", puzzleService.getPuzzleDto(id));
        model.addAttribute("indexes", puzzleService.getIndexList());
        return "/admin/puzzle";
    }

    // adding puzzles

    @GetMapping("/admin/add")
    public String createPuzzle(Model model) {
        Puzzle puzzle = new Puzzle();
        model.addAttribute("puzzle", puzzle);
        return "/admin/add";
    }
    @PostMapping("/admin/add")
    public String addPuzzle(@Valid @ModelAttribute Puzzle puzzle, BindingResult result, Model model) {

        if (puzzleService.isFenInvalid(puzzle.getFen())) {
            result.rejectValue("fen", "FEN_INVALID",
                    "Fen is incorrect, too long or contains invalid characters");
        }
        if (result.hasErrors()) {
            model.addAttribute("puzzle", puzzle);
            return "/admin/add";
        }

        puzzleService.savePuzzle(puzzle);
        return "redirect:/admin";
    }

    // deleting puzzles

    @GetMapping("/admin/delete")
    public String deletePuzzle(Model model) {
        model.addAttribute("puzzles", puzzleService.getAllPuzzles());
        return "/admin/delete";
    }
    @PostMapping("/admin/delete/{id}")
    public String deletePuzzle(@PathVariable Long id) {
        puzzleService.deletePuzzle(id);
        return "redirect:/admin/delete";
    }

    // editing puzzles

    @GetMapping("/admin/edit")
    public String findToEdit(Model model) {
        Puzzle puzzle = new Puzzle();
        model.addAttribute("puzzle", puzzle);
        return "/admin/edit";
    }


    @PostMapping("/admin/edit")
    public String saveEdit(@Valid @ModelAttribute("puzzle") Puzzle puzzle, BindingResult result, Model model) {
        // checking if there even is a puzzle of this ID, and if it exists checking if the new FEN is valid
        if (!puzzleService.getIndexList().contains(puzzle.getId())) {
            result.rejectValue("id", "ID_NOT_FOUND", "Puzzle of this ID was not found");
        } else if (puzzleService.isFenInvalid(puzzle.getFen())) {
            result.rejectValue("fen", "FEN_INVALID",
                    "Fen is either too long or contains invalid characters");
        }

        if (result.hasErrors()) {
            model.addAttribute("puzzle", puzzle);
            return "/admin/edit";
        }

        puzzleService.savePuzzle(puzzle);
        return "redirect:/admin/delete";
    }
}
