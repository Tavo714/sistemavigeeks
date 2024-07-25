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

import idat.pcds2.grupo3.sistemavigeeks.models.Product;

import idat.pcds2.grupo3.sistemavigeeks.services.ProductService;


@RestController
@RequestMapping("/api/v1/products")

public class ProductRestController {

    private ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }
    
    @PostMapping()
    public Product insert(@RequestBody Product entity) {
        return productService.insert(entity);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product entity) {
        entity.setId(id);
        return productService.update(entity);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean hasDeleted = productService.delete(id);
        return hasDeleted ? "El producto ha sido eliminado correctamente" : "Ocurrio un problema al eliminar el producto";
    }

}

