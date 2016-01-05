package packModelo;

import java.io.Serializable;

public class Sudoku implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3074923738912891028L;
	// Identificador del sudoku
    private int identificador;
    // Nivel de dificultad
    private int dificultad;
    //Estado de Matriz
    private boolean activado;
    // Matriz de casillas
    private Matriz matriz;

    /**
     * Sudoku
     * <p> POST: crea un sudoku con el identificador y dificultad indicados y con
     *         matriz de casillas vacï¿½a.<p>
     * @param pIdentificador String
     * @param pDificultad int
     */
    public Sudoku(int pIdentificador, int pDificultad) {
        identificador = pIdentificador;
        dificultad = pDificultad;
        activado = true;
        matriz = new Matriz();
    }

    /**
     * obtDificultad
     *
     * <p> POST: devuelve el nivel de dificultad del sudoku.
     *
     * <p>
     *
     * @return int
     * @todo Implement this packsudoku.ISudoku method
     */
    public int obtDificultad() {
        return dificultad;
    }

    /**
     * obtIdentificador
     *
     * <p> POST: devuelve el identificador del sudoku.
     *
     * <p>
     *
     * @return String
     * @todo Implement this packsudoku.ISudoku method
     */
    public int obtIdentificador() {
        return identificador;
    }

    /**
     * obtActivado
     *
     * <p> POST: devuelve si el sudoku está activado.
     *
     * <p>
     *
     * @return boolean
     * @todo Implement this packsudoku.ISudoku method
     */
    public boolean obtActivado() {
        return activado;
    }
    
    /**
     * obtActivado
     *
     * <p> POST: devuelve si el sudoku está activado.
     *
     * <p>
     *
     * @return boolean
     * @todo Implement this packsudoku.ISudoku method
     */
    public void setActivado(boolean pAct) {
        activado = pAct;
    }
    
    /**
     * obtMatriz
     *
     * <p> POST: devuelve la matriz de casillas del sudoku.
     *
     * <p>
     *
     * @return Matriz
     * @todo Implement this packsudoku.ISudoku method
     */
  
    public Matriz obtMatriz() {
        return matriz;
    }

    /**
     * asgValorInicial
     *
     * <p> POST: asigna el valor inicial a la casilla que estï¿½ en la
     * coordenada indicada.
     *
     * @param pFila int
     * @param pColumna int
     * @param pValor int
     * @todo Implement this packsudoku.ISudoku method
     */
    public void asgValorInicial(int pFila, int pColumna, int pValor) {
        matriz.asgValorInicial(pFila,pColumna,pValor);
    }

    /**
     * asgValor
     *
     * <p> POST: asigna el valor inicial a la casilla que estï¿½ en la
     * coordenada indicada.
     *
     * @param pFila int
     * @param pColumna int
     * @param pValor int
     * @todo Implement this packsudoku.ISudoku method
     */
    public void asgValor(int pFila, int pColumna, int pValor) {
        matriz.asgValor(pFila,pColumna,pValor);
    }
    
    public boolean esInicial(int pFila, int pColumna){
    	return matriz.esInicial(pFila, pColumna);
    }
    
    public void imprimir(){
		System.out.println("SUDOKU: " + identificador);
		for(int i = 0; i<9; i++){
			for(int j=0; j<9; j++){
				System.out.print(matriz.obtValor(i, j));
			}
			System.out.println();
		}
	}
    
    public boolean[][] comprobarCorrecto(Matriz pMatriz){
    	boolean[][] casillasFallo;
    	if(comprobarCompleto(pMatriz)){
    		casillasFallo = new boolean[9][9];
    		for(int i=0; i<9; i++){
    			for(int j=0; j<9; j++){
    				casillasFallo[i][j] = false;
    			}
    		}
    		for(int i=0; i<9; i++){
    			for(int j=0; j<9; j++){
    				pMatriz.obtCasilla(i, j).rellenarListaPosibles(comprobarListaPosibles(i, j, pMatriz));
    				if(!pMatriz.obtCasilla(i, j).comprobarCorrecto()){
    					casillasFallo[i][j] = true;
    				}
    			}
    		}
    	}else{
    		casillasFallo = new boolean[9][9];
    		for(int i=0; i<9; i++){
    			for(int j=0; j<9; j++){
    				casillasFallo[i][j] = false;
    			}
    		}
    		for(int i=0; i<9; i++){
    			for(int j=0; j<9; j++){
    				pMatriz.obtCasilla(i, j).rellenarListaPosibles(comprobarListaPosibles(i, j, pMatriz));
    				if(pMatriz.obtCasilla(i, j).obtValor() != 0){
    					if(!pMatriz.obtCasilla(i, j).comprobarCorrecto()){
        					casillasFallo[i][j] = true;
        				}
    				}else{
    					casillasFallo[i][j] = true;
    				}
    				
    			}
    		}
    	}
    	boolean todo = false;
    	for(int i=0; !todo && i<9; i++){
			for(int j=0; !todo && j<9; j++){
				if(casillasFallo[i][j]){
					todo = true;
				}
			}
		}
    	if(!todo){
    		casillasFallo = new boolean[0][0];
    	}
    	return casillasFallo;
    }
    
    private boolean comprobarCompleto(Matriz pMatriz){
		boolean completo = true;
		for(int i =0; i<9 && completo; i++){
			for(int j =0; j<9 && completo; j++){
				completo = pMatriz.obtCasilla(i, j).estaCompletada();
			}
		}
		return completo;
	}
    
    private char[] comprobarListaPosibles(int pI, int pJ, Matriz pMatriz){
		char[] lista = new char[9];
		for(int i=0; i<9; i++) lista[i] = Integer.toString(i+1).charAt(0);
		for(int i= 0; i<9; i++){
			if(i!=pI){
				if(esta(lista, pMatriz.obtCasilla(i, pJ).obtValor())){
					lista[pMatriz.obtCasilla(i, pJ).obtValor()-1] = ' ';
				}
			}
		}
		for(int j =0; j<9; j++){
			if(j!= pJ){
				if(esta(lista, pMatriz.obtCasilla(pI, j).obtValor())){
					lista[pMatriz.obtCasilla(pI, j).obtValor()-1] = ' ';
				}	
			}
		}
		int i = 0, j = 0, iMax = 3, jMax = 3;
		if(pI<3){ i = 0; iMax = 3;}
		else if(pI<6 && pI >=3){ i = 3; iMax = 6;}
		else if(pI>=6){ i = 6; iMax = 9;}
		if(pJ<3){ j = 0; jMax = 3;}
		else if(pJ<6 && pJ >=3){ j = 3; jMax = 6;}
		else if(pJ>=6){ j = 6; jMax = 9;}
		while(i<iMax){
			j = jMax-3;
			while(j<jMax){
				if(j!= pJ || i!=pI){
					if(esta(lista, pMatriz.obtCasilla(i, j).obtValor())){
						lista[pMatriz.obtCasilla(i, j).obtValor()-1] = ' ';
					}
				}
				j++;
			}
			i++;
		}
		return lista;
	}
	
    private boolean esta(char[] pLista, int pValor){
		boolean esta = false;
		if(pValor != 0){
			for(int i=0; i<pLista.length && !esta; i++){
				if((int)(pLista[i]-'0') == pValor){
					esta = true;
				}
			}
		}
		return esta;
	}
    
	/*public boolean estaEn(ListaPuntuaciones pLista){
		return pLista.esta(identificador);
	}*/	
/*	public boolean estaEn(ListaPuntuaciones pLista){
		return pLista.esta(identificador);
	}*/	
}
