package idat.pcds2.grupo3.sistemavigeeks.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrdeDTO {
    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
}
