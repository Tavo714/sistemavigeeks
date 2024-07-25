package idat.pcds2.grupo3.sistemavigeeks.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import idat.pcds2.grupo3.sistemavigeeks.models.Product;
import idat.pcds2.grupo3.sistemavigeeks.repositories.CategoryRepository;
import idat.pcds2.grupo3.sistemavigeeks.services.ProductService;


@Controller
@RequestMapping("/products")

public class ProductController {
	
	@Autowired
	private CategoryRepository categoryRep;

    private ProductService productService;
    private Product productCreated;
    private Product productModified;
    private boolean productDeleted;

    public ProductController(ProductService productService){
        this.productService = productService;
        productService = null;
        productModified = null;
        productDeleted = false;
    }

    @GetMapping()
    public String goToProductIndexView(Model model) {
        model.addAttribute("title", "Productos");
        model.addAttribute("headers", populateHeaders());
        model.addAttribute("products", productService.getAll());
        model.addAttribute("productHasCreated", productCreated != null);
        model.addAttribute("productHasModified", productModified != null);
        model.addAttribute("productHasDeleted", productDeleted);

        String productCreatedMessage = productCreated != null ? "El producto " +productCreated.getProducto() +" ha sido registrado correctamente con codigo " + productCreated.getId() : "";
        model.addAttribute("productCreatedMessage", productCreatedMessage);

        String productModifiedMessage = productModified != null ? "Los datos del producto " +productModified.getProducto() + " ha sido actualizado correctamente" : "";
        model.addAttribute("productModifiedMessage", productModifiedMessage);

        productCreated = null;
        productModified = null;
        productDeleted = false;
        return "product/product";
    }

    @GetMapping("/new")
    public String goToProductCreateView(Model model) {
    	List<Category> categories = categoryRep.findAll();
    	
        model.addAttribute("title", "Nuevo Producto");
        model.addAttribute("currentProduct", new Product());
        model.addAttribute("categories", categories);
        return "product/product-create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Product entity, Model model) {
        productCreated = productService.insert(entity);
        model.addAttribute("productCreated", productCreated);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String goToProductEditView(@PathVariable Long id, Model model) {
        Product toUpdate = productService.getById(id);
        if(toUpdate == null) return "redirect:/products";

        model.addAttribute("title", "Editar Product");
        model.addAttribute("currentProduct", toUpdate);
        return "product/product-edit";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Product entity, Model model) {
        productModified = productService.update(entity);
        model.addAttribute("productModified", productModified);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productDeleted = productService.delete(id);
        if(!productDeleted)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok("El producto ha sido eliminado correctamente");
    }
    

    private List<String> populateHeaders(){
        List<String> headers = new ArrayList<>();
        headers.add("Id");
        headers.add("Producto");
        headers.add("Descripcion");
        headers.add("Precio");
        headers.add("Stock");
        headers.add("Imagen");    

        headers.add("Acciones");

        return headers;
    }
    

}

