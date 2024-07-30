package idat.pcds2.grupo3.sistemavigeeks.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import idat.pcds2.grupo3.sistemavigeeks.models.CartItem;

@Service
public class CartItemService {

    private List<CartItem> cartItems = new ArrayList<>();

    public void addProductToCart(Long productId, String productName, int quantity, double price) {
        CartItem existingItem = findCartItemByProductId(productId);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(productId, productName, quantity, price);
            cartItems.add(newItem);
        }
    }
    public void removeProductFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProductId().equals(productId));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    private CartItem findCartItemByProductId(Long productId) {
        return cartItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }
}
