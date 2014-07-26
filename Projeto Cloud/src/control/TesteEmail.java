package control;



public class TesteEmail {
	
	public static void main(String[] args) {
		
		
		//Email e = new Email("jamersonfpl@gmail.com", "amarovn@gmail.com", "senha");
	    Email e = new Email("xrodssx@hotmail.com", "cloud.project666@gmail.com", "123abcdef");
		String texto = "Jesus ta voltando - pega paus e pregos";
		e.enviarEmail("Meu codigo funciona", texto);
	}
	
}
