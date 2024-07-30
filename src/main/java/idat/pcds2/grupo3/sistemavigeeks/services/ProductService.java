package idat.pcds2.grupo3.sistemavigeeks.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import idat.pcds2.grupo3.sistemavigeeks.models.Product;

import idat.pcds2.grupo3.sistemavigeeks.repositories.ProductRepository;


@Service   

public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product insert(Product entity){
        return productRepository.saveAndFlush(entity);
    }

    public Product update(Product entity){
        Optional<Product> response = productRepository.findById(entity.getId());
        if(!response.isPresent()) {
            return null;
        }
        Product toUpdate = response.get();
        toUpdate.setProducto(entity.getProducto());
        toUpdate.setDescripcion(entity.getDescripcion());
        toUpdate.setPrecio(entity.getPrecio());
        toUpdate.setStock(entity.getStock());
        toUpdate.setImagen(entity.getImagen());        

        return productRepository.saveAndFlush(entity);
    }

    public boolean delete(Long id){
        productRepository.deleteById(id);
        return true;
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product getById(Long id){
        Optional<Product> response = productRepository.findById(id);
        if(!response.isPresent()){
            return null;
        }
        return response.get();
    }

    public List<Product> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
