package idat.pcds2.grupo3.sistemavigeeks.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import idat.pcds2.grupo3.sistemavigeeks.models.Product;
import idat.pcds2.grupo3.sistemavigeeks.services.CartItemService;

@Controller
@RequestMapping("/cart")
public class CarritoController {

    private CartItemService cartItemService;
    
    public CarritoController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("productName") String productName,
                            @RequestParam("quantity") int quantity,
                            @RequestParam("price") double price) {
                                cartItemService.addProductToCart(productId, productName, quantity, price);
        return "redirect:/cart/view";
    }

    @GetMapping("/view")
    public String viewCart(Model model) {
        model.addAttribute("cart", cartItemService.getCartItems());
        return "cart"; 
    }
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("productId") Long productId) {
        cartItemService.removeProductFromCart(productId);
        return "redirect:/cart/view";
    }
    
}
