package idat.pcds2.grupo3.sistemavigeeks.services;

public class CustomeFieldValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
	
	private String fieldName;
	
	public CustomeFieldValidationException(String message, String fieldName) {
		super(message);
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
}