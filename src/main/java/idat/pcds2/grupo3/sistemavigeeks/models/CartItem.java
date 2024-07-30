package idat.pcds2.grupo3.sistemavigeeks.models;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CartItem {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
}
