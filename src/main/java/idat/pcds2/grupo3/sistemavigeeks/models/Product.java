package idat.pcds2.grupo3.sistemavigeeks.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="product")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name="producto")
    private String producto;
    @Column (name="descripcion")
    private String descripcion;
    @Column (name="precio")
    private Double precio;
    @Column (name="stock")
    private Integer stock;
    @Column (name="imagen")
    private String imagen;
    
    @ManyToOne
    @JoinColumn(name="category")
    private Category category;
    

}
