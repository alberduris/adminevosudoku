package packModelo;

public class FiltroTexto {
	
	public FiltroTexto(){
		
	}
	
	static public boolean esEmail(String str){
		
		boolean esMail = false;
		if(str.contains("@"))esMail = true;
		return esMail;
	}
	
	static public boolean caracterAdmitido(String str){
		
		boolean admitido = true;
		Character aux = ' ';
		
		for(int i = 0; i < str.length() && admitido == true; i++){
			aux = str.charAt(i);
			if(!Character.isLetterOrDigit(aux) && !aux.equals('-') && !aux.equals('_')) admitido = false;
		}
		
		return admitido;
		
	}
	
	static public String getContraseña(char[] contraseñaCharArray){
		StringBuilder strBuilder = new StringBuilder();
		String pass = "";
		
		strBuilder.append(contraseñaCharArray);
		pass = strBuilder.toString();
		
		return pass; 
	}
}
