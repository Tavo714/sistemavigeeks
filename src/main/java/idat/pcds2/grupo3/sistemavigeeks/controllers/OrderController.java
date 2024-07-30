package idat.pcds2.grupo3.sistemavigeeks.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import idat.pcds2.grupo3.sistemavigeeks.DTO.ProductDTO;
import idat.pcds2.grupo3.sistemavigeeks.models.Orders;
import idat.pcds2.grupo3.sistemavigeeks.services.OrderService;

@Controller
@RequestMapping("/orders")

public class OrderController {

    private OrderService orderService;
    private Orders orderCreated;
    private Orders orderModified;
    private boolean orderDeleted;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
        orderService = null;
        orderModified = null;
        orderDeleted = false;
    }

    @GetMapping()
    public String goToOrderIndexView(Model model) {
        model.addAttribute("title", "Pedidos");
        model.addAttribute("headers", populateHeaders());
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("orderHasCreated", orderCreated != null);
        model.addAttribute("orderHasModified", orderModified != null);
        model.addAttribute("orderHasDeleted", orderDeleted);

        String orderCreatedMessage = orderCreated != null ? "El pedido " +orderCreated.getId() +" ha sido registrado correctamente " : "";
        model.addAttribute("orderCreatedMessage", orderCreatedMessage);

        String orderModifiedMessage = orderModified != null ? "Los datos del pedido " +orderModified.getId() + " han sido actualizados correctamente" : "";
        model.addAttribute("orderModifiedMessage", orderModifiedMessage);

        orderCreated = null;
        orderModified = null;
        orderDeleted = false;
        return "order";
    }

    @GetMapping("/new")
    public String goToOrderCreateView(Model model) {
        model.addAttribute("title", "Nuevo Pedido");
        model.addAttribute("currentOrder", new Orders());
        return "order-create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Orders entity, Model model) {
        orderCreated = orderService.insert(entity);
        model.addAttribute("orderCreated", orderCreated);
        return "redirect:/orders";
    }
    
    @PostMapping("/cli/saveorder")
    public ResponseEntity<String> saveOrderCli(@RequestBody Map<String, List<ProductDTO>> request) {
        Orders order = new Orders();
        order.setEstado("PENDIENTE");
        LocalDate ahora = LocalDate.now();
        order.setFecha(ahora);
        order.setTotal(15D);
        //orderService.insert(order);
        // Aquí puedes procesar la lista de productos
        System.out.println("Received products: " + request);

        return ResponseEntity.ok("success");
    }
    
    

    @GetMapping("/edit/{id}")
    public String goToOrderEditView(@PathVariable Long id, Model model) {
        Orders toUpdate = orderService.getById(id);
        if(toUpdate == null) return "redirect:/orders";

        model.addAttribute("title", "Editar Pedido");
        model.addAttribute("currentOrder", toUpdate);
        return "order-edit";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Orders entity, Model model) {
        orderModified = orderService.update(entity);
        model.addAttribute("orderModified", orderModified);
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        orderDeleted = orderService.delete(id);
        if(!orderDeleted)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok("El pedido ha sido eliminado correctamente");
    }
    

    private List<String> populateHeaders(){
        List<String> headers = new ArrayList<>();
        headers.add("Id");
        headers.add("ID Producto");
        headers.add("Producto");
        headers.add("Cantidad");
        headers.add("Precio");
        headers.add("Total");
        headers.add("Usuario");
        headers.add("Fecha");
        headers.add("Estado");
    

        headers.add("Acciones");

        return headers;
    }
    

}