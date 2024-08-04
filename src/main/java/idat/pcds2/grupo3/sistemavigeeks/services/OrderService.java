package idat.pcds2.grupo3.sistemavigeeks.services;

import java.util.List;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import idat.pcds2.grupo3.sistemavigeeks.DTO.ProductDTO;
import idat.pcds2.grupo3.sistemavigeeks.models.Orders;
import idat.pcds2.grupo3.sistemavigeeks.models.Product;
import idat.pcds2.grupo3.sistemavigeeks.repositories.OrderRepository;

@Service

public class OrderService {

    private OrderRepository orderRepository;
    private ProductService productService;
    

    public OrderService(OrderRepository orderRepository, ProductService productService){
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Orders insert(Orders entity){
        return orderRepository.saveAndFlush(entity);
    }
    
    public Orders update(Orders entity){
        Optional<Orders> response = orderRepository.findById(entity.getId());
        if(!response.isPresent()) {
            return null;
        }
        Orders toUpdate = response.get();
        toUpdate.setTotal(entity.getTotal());
        toUpdate.setFecha(entity.getFecha());
        toUpdate.setEstado(entity.getEstado());
        return orderRepository.saveAndFlush(entity);
    }

    public ResponseEntity<String> processOrder(Orders entity, List<ProductDTO> products) {
        try {
            // Guardar la orden en la base de datos
            insert(entity);

            // Actualizar el stock de los productos
            for (ProductDTO product : products) {
                // Obtén el producto desde la base de datos usando el ID del producto
                Product dbProduct = productService.getById(product.getId());
                if (dbProduct == null) {
                    return ResponseEntity.badRequest().body("Product not found: " + product.getId());
                }

                // Verifica si hay suficiente stock
                if (dbProduct.getStock() < product.getQuantity()) {
                    return ResponseEntity.badRequest().body("Insufficient stock for product: " + product.getId());
                }

                // Actualiza el stock
                dbProduct.setStock(dbProduct.getStock() - product.getQuantity());
                productService.update(dbProduct); // Guarda los cambios en la base de datos
            }

            return ResponseEntity.ok("Order processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process order: " + e.getMessage());
        }
    }

    public boolean delete(Long id){
        orderRepository.deleteById(id);
        return true;
    }

    public List<Orders> getAll(){
        return orderRepository.findAll();
    }

    public Orders getById(Long id){
        Optional<Orders> response = orderRepository.findById(id);
        if(!response.isPresent()){
            return null;
        }
        return response.get();
    }

}
