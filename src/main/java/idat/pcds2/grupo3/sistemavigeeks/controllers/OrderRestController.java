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

import idat.pcds2.grupo3.sistemavigeeks.models.Orders;
import idat.pcds2.grupo3.sistemavigeeks.services.OrderService;

@RestController
@RequestMapping("/api/v1/orders")

public class OrderRestController {

    private OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public List<Orders> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public Orders getById(@PathVariable Long id) {
        return orderService.getById(id);
    }
    
    @PostMapping()
    public Orders insert(@RequestBody Orders entity) {
        return orderService.insert(entity);
    }

    @PutMapping("/{id}")
    public Orders update(@PathVariable Long id, @RequestBody Orders entity) {
        entity.setId(id);
        return orderService.update(entity);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean hasDeleted = orderService.delete(id);
        return hasDeleted ? "El pedido ha sido eliminado correctamente" : "Ocurrio un problema al eliminar el pedido";
    }

}