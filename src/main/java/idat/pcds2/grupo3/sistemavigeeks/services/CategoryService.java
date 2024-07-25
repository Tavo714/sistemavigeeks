package idat.pcds2.grupo3.sistemavigeeks.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import idat.pcds2.grupo3.sistemavigeeks.models.Category;

import idat.pcds2.grupo3.sistemavigeeks.repositories.CategoryRepository;


@Service

public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category insert(Category entity){
        return categoryRepository.saveAndFlush(entity);
    }

    public Category update(Category entity){
        Optional<Category> response = categoryRepository.findById(entity.getId());
        if(!response.isPresent()) {
            return null;
        }
        Category toUpdate = response.get();
        toUpdate.setCategoria(entity.getCategoria());
        toUpdate.setDescripcion(entity.getDescripcion());
              

        return categoryRepository.saveAndFlush(entity);
    }

    public boolean delete(Long id){
        categoryRepository.deleteById(id);
        return true;
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public Category getById(Long id){
        Optional<Category> response = categoryRepository.findById(id);
        if(!response.isPresent()){
            return null;
        }
        return response.get();
    }
}
