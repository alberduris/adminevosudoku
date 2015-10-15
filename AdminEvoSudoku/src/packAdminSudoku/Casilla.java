package packAdminSudoku;

public class Casilla {
	private char numero, solucion;
	private char[] listaPosibles;
	private char[] listaNotas;
	private boolean fija;
	
	Casilla( char pNumero, char pSolucion){
		this.numero = pNumero;
		if(!Character.isDigit(numero)){
			this.fija = false;
		}else{ 
			this.fija = true;
		}
		this.solucion = pSolucion;
		this.listaNotas = new char[9];
		this.listaPosibles = new char[9];
		if(!fija){
			for(int i=0; i<9; i++){
				listaNotas[i] = ' ';
			}
		}
		for(int i=0; i<9; i++){
			listaPosibles[i] = ' ';
		}
	}

	public char getNumero() {
		return numero;
	}
	
	public char getSolucion(){
		return solucion;
	}
	
	public boolean obtenerFija(){
		return fija;
	}
	
	public char[] getListaNotas(){
		if(cantidadDeNumeros(listaNotas)>1){
			return listaNotas;
		}
		return null;
	}
	public char[] getListaPosibles(){
		return listaPosibles;
	}
	
	public void anadirNumero(char pNum){
		if(!fija){
			int i = Character.getNumericValue(pNum);
			if(Character.isDigit(numero)){
				if(numero == pNum){
					numero = ' ';
				}else{
					int aux = Character.getNumericValue(numero);
					listaNotas[aux-1] = numero;
					listaNotas[i-1] = pNum;
					numero = ' ';
				}
			}else{
				if(cantidadDeNumeros(listaNotas) == 0){
					numero = pNum;
				}else if(cantidadDeNumeros(listaNotas) == 2){
					if(Character.isDigit(listaNotas[i-1])){
						listaNotas[i-1] = ' ';
						numero = listaNotas[Character.getNumericValue(cogerNumeroUnico(listaNotas)-1)];
						listaNotas[Character.getNumericValue(cogerNumeroUnico(listaNotas)-1)] = ' ';
					}else{
						listaNotas[i-1] = pNum;
					}
				}else{
					if(Character.isDigit(listaNotas[i-1])){
						listaNotas[i-1] = ' ';
					}else{
						listaNotas[i-1] = pNum;
					}
				}
			}
		}
	}

	public void borrarNumero() {
		numero = ' ';
		for(int i = 0; i < listaNotas.length ; i++){
			listaNotas[i]=' ';
		}
	}

	private int cantidadDeNumeros(char[] pLista){
		int cont = 0;
		for(int i = 0; i<9; i++){
			if(Character.isDigit(pLista[i])){
				cont++;
			}
		}
		return cont;
	}
	
	private char cogerNumeroUnico(char[] pLista){
		char aux = ' ';
		for(int i = 0; i<9 && aux == ' '; i++){
			if(pLista[i] != ' '){
				aux = pLista[i];
			}
		}
		return aux;
	}
	
	public boolean comprobarCompleto(){
		boolean completo = false;
		if(numero == solucion){
			completo = true;
		}
		return completo;
	}
	
	public boolean comprobarCorrecto(){
		boolean correcto = true;
		if(!fija){
			if(cantidadDeNumeros(listaPosibles) > 0){
				if(Character.isDigit(numero)){
					if(!Character.isDigit(listaPosibles[Character.getNumericValue(numero)-1])){
						correcto = false;;
					}
				}	
			}
		}
		return correcto;
	}
	
	public void rellenarListaPosibles(char[] pLista){
		for(int i=0; i<pLista.length; i++){
			listaPosibles[i] = pLista[i];
		}
	}
	
	public boolean eliminateValues(){
		boolean modificado=false;
		if(!Character.isDigit(numero)){
			if(cantidadDeNumeros(listaPosibles) == 1){
				numero = cogerNumeroUnico(listaPosibles);
				modificado = true;
				for(int i=0; i<9; i++) listaNotas[i]=' ';
			}else if(cantidadDeNumeros(listaPosibles) > 1){
				for(int i = 0; i<9; i++){
					if(listaPosibles[i] != listaNotas[i]){
						modificado = true;
						listaNotas[i] = listaPosibles[i];
					}
				}
			}
		}
		return modificado;
	}
}
