package com.example.quiz.dto;

import com.example.quiz.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {

    List<Answer> answers;

    Long categoryId;
}
