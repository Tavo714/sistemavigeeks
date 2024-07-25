package idat.pcds2.grupo3.sistemavigeeks.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idat.pcds2.grupo3.sistemavigeeks.models.Category;

import idat.pcds2.grupo3.sistemavigeeks.services.CategoryService;


@RestController
@RequestMapping("/api/v1/categories")

public class CategoryRestController {

    private CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }
    
    @PostMapping()
    public Category insert(@RequestBody Category entity) {
        return categoryService.insert(entity);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category entity) {
        entity.setId(id);
        return categoryService.update(entity);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean hasDeleted = categoryService.delete(id);
        return hasDeleted ? "La categoria ha sido eliminada correctamente" : "Ocurrio un problema al eliminar la categoria";
    }

}

