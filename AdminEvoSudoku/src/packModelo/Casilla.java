package packModelo;

public class Casilla{

    // Indica si es un valor inicial
    private boolean valorInicial;

    // Valor contenido en la casilla
    private int valor;

    // Estado de ocupaci�n de la casilla
    private boolean ocupada;

    // Lista de las casillas posibles
    private char[] listaPosibles;
   
    //Lista de notas.
	private char[] listaNotas;
    
    /**
     * Casilla
     * <p> POST: crea una casilla que est� libre y no contiene un valor inicial.<p>
     */
    public Casilla() {
       valorInicial = false;
       ocupada = false;
       valor = 0;
       this.listaNotas = new char[9];
       this.listaPosibles = new char[9];
       for(int i=0; i<9; i++){
			listaPosibles[i] = ' ';
		}
       for(int i=0; i<9; i++){
			listaNotas[i] = ' ';
		}
    }

    /**
     * asgValorInicial
     *
     * <p>POST: inicializa el valor de la casilla con el valor indicado,
     * pasando a estar ocupada.
     *
     * @param pValor int
     */
    public void asgValorInicial(int pValor) {
       valor = pValor;
       ocupada = true;
       valorInicial = true;
    }

    /**
     * asgValor
     *
     * <p>POST: inicializa el valor de la casilla con el valor indicado.
     *
     * @param pValor int
     */
    public void asgValor(int pValor) {
       if(!valorInicial){
			char num = String.valueOf(pValor).charAt(0);
			if(valor != 0){
				if(valor == pValor){
					valor = 0;
					ocupada = false;
				}else{
					int aux = valor;
					listaNotas[aux-1] = String.valueOf(valor).charAt(0);
					listaNotas[pValor-1] = num;
					valor = 0;
					ocupada = false;
				}
			}else{
				if(cantidadDeNumeros(listaNotas) == 0){
					valor = pValor;
					ocupada = true;
				}else if(cantidadDeNumeros(listaNotas) == 2){
					if(Character.isDigit(listaNotas[pValor-1])){
						listaNotas[pValor-1] = ' ';
						valor = (int)(listaNotas[Character.getNumericValue(cogerNumeroUnico(listaNotas))-1]-'0');
						listaNotas[Character.getNumericValue(cogerNumeroUnico(listaNotas)-1)] = ' ';
					}else{
						listaNotas[pValor-1] = num;
					}
				}else{
					if(Character.isDigit(listaNotas[pValor-1])){
						listaNotas[pValor-1] = ' ';
					}else{
						listaNotas[pValor-1] = num;
					}
				}
			}
		} 
    }

    /**
     * quitarValor
     * <p>POST: indica que la casilla no est� ocupada.<p>
     */
    public void quitarValor() {
       ocupada = false;
       valor = 0;
       for(int i=0; i<listaNotas.length; i++){
    	   listaNotas[i] = ' ';
       }
    }

    /**
     * estaLibre
     *
     * <p>POST: devuelve true si la casilla esta libre y false en caso
     * contrario
     *
     * <p>
     *
     * @return boolean
     */
    public boolean estaLibre() {
       return !ocupada;
    }

    /**
     * esInicial
     *
     * <p> POST: devuelve true si la casilla contiene un valor inicial y
     * false en caso contrario.
     *
     * <p>
     *
     * @return boolean
     */
    public boolean esInicial() {
       return valorInicial;
    }

    /**
     * obtValor
     *
     * <p> PRE: La casilla est� ocupada.
     *
     * <p>
     *
     * <p> POST: devuelve el valor contenido en la casilla.
     *
     * <p>
     *
     * @return int
     */
    public int obtValor() {
       return valor;
    }

    /**
     * mismoValor
     * <p> PRE: ambas casillas est�n ocupadas.<p>
     * <p> POST: devuelve true si ambas casillas contienen el mismo valor y
     *         false en caso contrario.<p>
     * @param pCasilla Casilla
     * @return boolean
     */
     public boolean mismoValor(Casilla pCasilla) {
        return this.obtValor() == pCasilla.obtValor();
     }
     
     public boolean estaCompletada() {
    	 return ocupada;
     }
     
     public char[] getListaNotas(){
 		char[] lista = new char[0];
    	 if(cantidadDeNumeros(listaNotas)>1){
 			lista = listaNotas;
 		}
 		return lista;
 	}
     
	public char[] getListaPosibles(){
		return listaPosibles;
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
			if(Character.isDigit(pLista[i])){
				aux = pLista[i];
			}
		}
		return aux;
	}
	
	public boolean comprobarCorrecto(){
		boolean correcto = true;
		if(!valorInicial){
			if(cantidadDeNumeros(listaPosibles) > 0){
				if(valor != 0){
					if(!Character.isDigit(listaPosibles[valor-1])){
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
		if(valor == 0){
			if(cantidadDeNumeros(listaPosibles) == 1){
				valor = (int)(cogerNumeroUnico(listaPosibles)-'0');
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
	
	private void imprimirLista(){
		for(int i=0; i<9; i++){
			System.out.print(listaNotas[i]+ ", ");
		}
		System.out.println();
	}
	
}

