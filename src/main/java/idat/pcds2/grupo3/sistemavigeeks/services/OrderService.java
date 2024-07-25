package idat.pcds2.grupo3.sistemavigeeks.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import idat.pcds2.grupo3.sistemavigeeks.models.Orders;
import idat.pcds2.grupo3.sistemavigeeks.repositories.OrderRepository;

@Service

public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
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
