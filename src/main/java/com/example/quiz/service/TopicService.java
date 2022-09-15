package com.example.quiz.service;

import com.example.quiz.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicService {

    List<Topic> getAll();

    void create (Topic topic);

    void delete(Long topicId);

    Topic update (Topic topic);
}
