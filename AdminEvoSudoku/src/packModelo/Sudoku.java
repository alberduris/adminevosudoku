package packModelo;

import packAdminSudoku.ListaPuntuaciones;
import packModelo.Casilla;

public class Sudoku {

    // Identificador del sudoku
    private int identificador;
    // Nivel de dificultad
    private int dificultad;
    // Matriz de casillas
    private Matriz matriz;

    /**
     * Sudoku
     * <p> POST: crea un sudoku con el identificador y dificultad indicados y con
     *         matriz de casillas vac�a.<p>
     * @param pIdentificador String
     * @param pDificultad int
     */
    public Sudoku(int pIdentificador, int pDificultad) {
        identificador = pIdentificador;
        dificultad = pDificultad;
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
     * <p> POST: asigna el valor inicial a la casilla que est� en la
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
     * <p> POST: asigna el valor inicial a la casilla que est� en la
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
    	if(!comprobarCompleto(pMatriz)){
    		casillasFallo = new boolean[9][9];
    		for(int i=0; i<9; i++){
    			for(int j=0; j<9; j++){
    				casillasFallo[i][j] = false;
    			}
    		}
    		for(int i=0; i<9; i++){
    			for(int j=0; j<9; j++){
    				matriz.obtCasilla(i, j).rellenarListaPosibles(comprobarListaPosibles(i, j, matriz));
    				if(!matriz.obtCasilla(i, j).comprobarCorrecto()){
    					casillasFallo[i][j] = true;
    				}
    			}
    		}
    	}else{
    		casillasFallo = new boolean[0][0];
    	}
    	return casillasFallo;
    }
    
    private boolean comprobarCompleto(Matriz pMatriz){
		boolean completo = true;
		for(int i =0; i<9 && completo; i++){
			for(int j =0; j<9 && completo; j++){
				//TODO: completo = matriz.obtCasilla(i, j).comprobarCompleto();
			}
		}
		return completo;
	}
    
    private char[] comprobarListaPosibles(int pFila, int pColumna, Matriz pMatriz){
    	char[] lista = new char[9];
    	for(int i =0; i<9; i++) lista[i] = Integer.toString(i+1).charAt(0);
    	for(int i=0; i<9; i++){
			if(i != pFila){
				if(esta(lista, pMatriz.obtValor(i, pColumna))){
					lista[pMatriz.obtValor(i, pColumna)-1] = ' ';
				}
			}
		}
    	for(int i=0; i<9; i++){
    		if(i != pFila){
				if(esta(lista, pMatriz.obtValor(pFila, i))){
					lista[pMatriz.obtValor(pFila, i)-1] = ' ';
				}
			}
		}
    	int i = 0, j = 0, iMax = 3, jMax = 3;
		if(pFila<3){ i = 0; iMax = 3;}
		else if(pFila<6 && pFila >=3){ i = 3; iMax = 6;}
		else if(pFila>=6){ i = 6; iMax = 9;}
		if(pColumna<3){ j = 0; jMax = 3;}
		else if(pColumna<6 && pColumna >=3){ j = 3; jMax = 6;}
		else if(pColumna>=6){ j = 6; jMax = 9;}
		while(i<iMax){
			j = jMax-3;
			while(j<jMax){
				if(j!= pColumna || i!=pFila){
					if(esta(lista, pMatriz.obtValor(i,  j))){
						lista[pMatriz.obtValor(i, j)-1] = ' ';
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
				if(Integer.valueOf(pLista[i]) == pValor){
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
