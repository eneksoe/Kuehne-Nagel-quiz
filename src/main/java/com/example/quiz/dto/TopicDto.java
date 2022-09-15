package com.example.quiz.dto;

import com.example.quiz.model.Topic;
import lombok.Data;

import java.util.List;

@Data
public class TopicDto {

    List<Topic> categories;
}
