package com.example.quiz.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result {

    private String questionText;

    private String correctAnswer;

    private String selectedAnswer;

    private boolean correct;
}
