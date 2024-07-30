package idat.pcds2.grupo3.sistemavigeeks.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import idat.pcds2.grupo3.sistemavigeeks.models.Category;
import idat.pcds2.grupo3.sistemavigeeks.models.Product;
import idat.pcds2.grupo3.sistemavigeeks.repositories.CategoryRepository;

import idat.pcds2.grupo3.sistemavigeeks.services.ProductService;

@Controller
@RequestMapping("/catalog")

public class catalogController {

    @Autowired
	private CategoryRepository categoryRep;
   
    private ProductService productService;
    
    public catalogController(ProductService productService){
        this.productService = productService;
        productService = null;
        
    }

    @GetMapping()
    public String showProductCatalog(Model model) {
        
        List<Category> categories = categoryRep.findAll();
        

        model.addAttribute("title", "Catálogo de Productos");
        model.addAttribute("products", productService.getAll());
        model.addAttribute("categories", categories);
        
        return "catalogo";
    }

    @GetMapping("/category/{id}")
    public String showProductsByCategory(@PathVariable("id") Long categoryId, Model model) {
        
        List<Category> categories = categoryRep.findAll();
        List<Product> products = productService.getByCategory(categoryId);
        model.addAttribute("title", "Catálogo de Productos");
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "catalogo";
    }

   
}


