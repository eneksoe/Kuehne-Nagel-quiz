package com.example.quiz.service.impl;

import com.example.quiz.model.Topic;
import com.example.quiz.repository.TopicRepository;
import com.example.quiz.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository repository;

    @Override
    public List<Topic> getAll() {
        return repository.findAll();
    }

    @Override
    public void create(Topic topic) {
        repository.save(topic);
    }

    @Override
    public void delete(Long topicId) {
        repository.deleteById(topicId);
    }

    @Override
    public Topic update(Topic topic) {
        Topic topicFromDb = repository.findById(topic.getId())
                .orElseThrow(()->new IllegalArgumentException("Topic with id: " + topic.getId() + " not found"));
        topicFromDb.setName(topicFromDb.getName());
        return repository.save(topicFromDb);
    }
}
