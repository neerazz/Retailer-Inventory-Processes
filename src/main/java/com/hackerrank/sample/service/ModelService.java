package com.hackerrank.sample.service;

import com.hackerrank.sample.model.Model;
import java.util.List;

public interface ModelService {
    void deleteAllModels();
    void deleteModelById(Long id);

    void createModel(Model model);

    Model getModelById(Long id);

    List<Model> getAllModels();
}