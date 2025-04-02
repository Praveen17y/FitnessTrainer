package com.example.fitness.controller;

import com.example.fitness.model.UserExercise;
import com.example.fitness.repository.UserExerciseRepository;
import com.example.fitness.service.GroqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fitness")
public class FitnessController {

    @Autowired
    private UserExerciseRepository repository;

    @Autowired
    private GroqService groqService;

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/recommend")
    public String getRecommendations(@RequestParam String fitnessLevel,
                                     @RequestParam String exerciseDetails,
                                     @RequestParam String userGoals,
                                     @RequestParam(required = false) String injuryNotes,
                                     Model model) {

        UserExercise userExercise = new UserExercise(fitnessLevel, exerciseDetails, userGoals, injuryNotes);
        repository.save(userExercise);

        String response = groqService.getWorkoutPlan(fitnessLevel, exerciseDetails, userGoals, injuryNotes);
        model.addAttribute("response", response);
        return "result";
    }
}
