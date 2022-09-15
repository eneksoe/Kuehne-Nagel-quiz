package com.example.quiz.model;

import com.example.quiz.util.OptionAttributeConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String text;

    @NotNull
    private String rank;

    @Convert(converter = OptionAttributeConverter.class)
    private ConcurrentHashMap<Long, Option> options = new ConcurrentHashMap<>(4);

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
