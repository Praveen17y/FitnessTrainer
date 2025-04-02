package com.example.fitness.model;

import jakarta.persistence.*;

@Entity
public class UserExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String fitnessLevel;

    @Column(length = 1000)
    private String exerciseDetails;

    @Column(nullable = false, length = 500)
    private String userGoals;

    @Column(length = 1000)
    private String injuryNotes;

    public UserExercise() {}

    public UserExercise(String fitnessLevel, String exerciseDetails, String userGoals, String injuryNotes) {
        this.fitnessLevel = fitnessLevel;
        this.exerciseDetails = exerciseDetails;
        this.userGoals = userGoals;
        this.injuryNotes = injuryNotes;
    }

    // Getters and setters...
}
