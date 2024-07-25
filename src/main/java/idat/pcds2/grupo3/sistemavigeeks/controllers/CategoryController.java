package idat.pcds2.grupo3.sistemavigeeks.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import idat.pcds2.grupo3.sistemavigeeks.models.Category;

import idat.pcds2.grupo3.sistemavigeeks.services.CategoryService;


@Controller
@RequestMapping("/categories")

public class CategoryController {

    private CategoryService categoryService;
    private Category categoryCreated;
    private Category categoryModified;
    private boolean categoryDeleted;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
        categoryService = null;
        categoryModified = null;
        categoryDeleted = false;
    }

    @GetMapping()
    public String goToCategoryIndexView(Model model) {
        model.addAttribute("title", "Categorias");
        model.addAttribute("headers", populateHeaders());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("categoryHasCreated", categoryCreated != null);
        model.addAttribute("categoryHasModified", categoryModified != null);
        model.addAttribute("categoryHasDeleted", categoryDeleted);

        String categoryCreatedMessage = categoryCreated != null ? "La categoria " +categoryCreated.getCategoria() +" ha sido registrada correctamente con codigo " + categoryCreated.getId() : "";
        model.addAttribute("categoryCreatedMessage", categoryCreatedMessage);

        String categoryModifiedMessage = categoryModified != null ? "Los datos de la categoria " +categoryModified.getCategoria() + " ha sido actualizada correctamente" : "";
        model.addAttribute("categoryModifiedMessage", categoryModifiedMessage);

        categoryCreated = null;
        categoryModified = null;
        categoryDeleted = false;
        return "category";
    }

    @GetMapping("/new")
    public String goToCategoryCreateView(Model model) {
        model.addAttribute("title", "Nueva Categoria");
        model.addAttribute("currentCategory", new Category());
        return "category-create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Category entity, Model model) {
        categoryCreated = categoryService.insert(entity);
        model.addAttribute("categoryCreated", categoryCreated);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String goToCategoryEditView(@PathVariable Long id, Model model) {
        Category toUpdate = categoryService.getById(id);
        if(toUpdate == null) return "redirect:/categories";

        model.addAttribute("title", "Editar Categoria");
        model.addAttribute("currentCategory", toUpdate);
        return "category-edit";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Category entity, Model model) {
        categoryModified = categoryService.update(entity);
        model.addAttribute("categoryModified", categoryModified);
        return "redirect:/categories";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        categoryDeleted = categoryService.delete(id);
        if(!categoryDeleted)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok("La categoria ha sido eliminada correctamente");
    }
    

    private List<String> populateHeaders(){
        List<String> headers = new ArrayList<>();
        headers.add("Id");
        headers.add("Categoria");
        headers.add("Descripcion");
    

        headers.add("Acciones");

        return headers;
    }
    

}
