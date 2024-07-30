package idat.pcds2.grupo3.sistemavigeeks.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class ProductDTO {
	
	
	private Long id;
    private String producto;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String imagen;
    private int quantity;
}
