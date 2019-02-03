package com.hackerrank.sample.service;

import com.hackerrank.sample.excpetion.BadResourceRequestException;
import com.hackerrank.sample.excpetion.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Model;
import com.hackerrank.sample.repository.ModelRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("modelService")
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public void deleteAllModels() {
        modelRepository.deleteAllInBatch();
    }

    @Override
    public void deleteModelById(Long id) {
        modelRepository.deleteById(id);
    }

    @Override
    public void createModel(Model model) {
        Model existingModel = modelRepository.findById(model.getId()).get();

        if (existingModel != null) {
            throw new BadResourceRequestException("Model with same id exists.");
        }
        modelRepository.save(model);
    }

    @Override
    public Model getModelById(Long id) {
        Model model = modelRepository.findById(id).get();

        if (model == null) {
            throw new NoSuchResourceFoundException("No model with given id found.");
        }

        return model;
    }

    @Override
    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }
}