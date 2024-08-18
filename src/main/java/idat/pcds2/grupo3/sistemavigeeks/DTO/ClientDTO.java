package idat.pcds2.grupo3.sistemavigeeks.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
	
	private String nombres;
	
	private String apellidos;
	
	private String telefono;

	private String ruc;
	
	private String email;
	
	private String empresa;
	
	private String username;
	
	private String password;
	
}
