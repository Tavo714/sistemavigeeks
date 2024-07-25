package idat.pcds2.grupo3.sistemavigeeks.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Column (name="total") // Debe ser el resultado de multiplicar cantidad por precio
    private Double total;
    
    @Column (name="fecha")
    private LocalDate fecha;
    
    @Column (name="estado")
    private String estado = "ACTIVO";
    
    @ManyToOne
	@JoinColumn(name="client")
	private Client client;
    
    


}
