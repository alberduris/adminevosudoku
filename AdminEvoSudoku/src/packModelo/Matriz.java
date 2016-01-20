package packModelo;

import java.io.Serializable;

public class Matriz implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3583073981134219483L;
	
    private final int DIMENSION = 9;
    private Casilla[][] matriz;

    public Matriz() {
        matriz = new Casilla[9][9];
        for (int i=0; i<9; i++)
            for (int j=0; j<9; j++)
                matriz[i][j] = new Casilla();
    }

    /**
     * asgValorInicial
     *
     * @param pFila int
     * @param pColumna int
     * @param pValor int
     * @todo Implement this packsudoku.IMatriz method
     */
    public void asgValorInicial(int pFila, int pColumna, int pValor) {
        Casilla unaCasilla = matriz[pFila][pColumna];
        unaCasilla.asgValorInicial(pValor);
    }

    /**
     * asgValor
     *
     * @param pFila int
     * @param pColumna int
     * @param pValor int
     * @todo Implement this packsudoku.IMatriz method
     */
    public void asgValor(int pFila, int pColumna, int pValor) {
        Casilla unaCasilla = matriz[pFila][pColumna];
        unaCasilla.asgValor(pValor);
    }

    /**
     * quitarValor
     *
     * @param pFila int
     * @param pColumna int
     * @todo Implement this packsudoku.IMatriz method
     */
    public void quitarValor(int pFila, int pColumna) {
        Casilla unaCasilla = matriz[pFila][pColumna];
        unaCasilla.quitarValor();
    }

    /**
     * estaLibre
     *
     * @param pFila int
     * @param pColumna int
     * @todo Implement this packsudoku.IMatriz method
     */
    public boolean estaLibre(int pFila, int pColumna) {
        Casilla unaCasilla = matriz[pFila-1][pColumna-1];
        return unaCasilla.estaLibre();
    }

    /**
     * esInicial
     *
     * @param pFila int
     * @param pColumna int
     * @todo Implement this packsudoku.IMatriz method
     */
    public boolean esInicial(int pFila, int pColumna) {
        Casilla unaCasilla = matriz[pFila][pColumna];
        return unaCasilla.esInicial();
    }

    /**
     * obtValor
     *
     * @param pFila int
     * @param pColumna int
     * @todo Implement this packsudoku.IMatriz method
     */
    public int obtValor(int pFila, int pColumna) {
        Casilla unaCasilla = matriz[pFila][pColumna];
        return unaCasilla.obtValor();
    }

    /**
     * descubrirInconsistencias
     *
     * @param pTipo char
     * @param pIdentificador int
     * @todo Implement this packsudoku.IMatriz method
     */
    public boolean descubrirInconsistencias(char pTipo, int pIdentificador) {
       if (pTipo == 'F')
           return hayRepetidosFila(pIdentificador);
       else
          if (pTipo == 'C')
              return hayRepetidosColumna(pIdentificador);
          else
              return hayRepetidosZona(pIdentificador);
    }

    /**
     * comprobarSolucion
     *
     * @param pMatrizOriginal Matriz
     * @todo Implement this packsudoku.IMatriz method
     * Supongo que pMatrizOriginal contiene la matriz completa y el metodo se invoca desde la matriz
     * correspondiente a la situaci�n actual de la resoluci�n del sudoku
     */
    public int comprobarSolucion(Matriz pMatrizOriginal) {
       int contador = 0;
       Casilla casillaOriginal, casillaUsuario;
       for (int fila = 0; fila < DIMENSION; fila++)
          for (int col = 0; col < DIMENSION; col++){
              casillaOriginal = pMatrizOriginal.obtCasilla(fila+1, col+1);
              casillaUsuario = matriz[fila][col];
              if (!casillaUsuario.estaLibre() && !casillaUsuario.mismoValor(casillaOriginal))
                  contador = contador + 1;
          }
       return contador;
    }

    /**
     * obtCasilla
     *
     * @param pFila int
     * @param pColumna int
     * @todo Implement this packsudoku.IMatriz method
     */
    public Casilla obtCasilla(int pFila, int pColumna) {
        return matriz[pFila][pColumna];
    }

    /**
     * hayRepetidosFila
     *
     * @param pFila int
     * @todo Implement this packsudoku.IMatriz method
     */
    private boolean hayRepetidosFila(int pFila){
       boolean repe = false;
       pFila = pFila -1;
       int colIni = 0;
       int colFin = 0;
       Casilla unaCas, dosCas;
       while (colIni < DIMENSION - 1 && !repe) {
          colFin = colIni + 1;
          unaCas = matriz[pFila][colIni];
          if (!unaCas.estaLibre())
             while (colFin < DIMENSION && !repe) {
                 dosCas = matriz[pFila][colFin];
                 if (!dosCas.estaLibre())
                    if (unaCas.mismoValor(dosCas))
                       repe = true;
                 colFin = colFin + 1;
             }
          colIni = colIni + 1;
       }
       return repe;
    }

    /**
     * hayRepetidosColumna
     *
     * @param pColumna int
     * @todo Implement this packsudoku.IMatriz method
     */
    private boolean hayRepetidosColumna(int pColumna){
       boolean repe = false;
       int filaIni = 0;
       int filaFin = 0;
       Casilla unaCas, dosCas;
       pColumna = pColumna -1;
       while (filaIni < DIMENSION - 1 && !repe) {
          filaFin = filaIni + 1;
          unaCas = matriz[filaIni][pColumna];
          if (!unaCas.estaLibre())
             while (filaFin < DIMENSION && !repe) {
                 dosCas = matriz[filaFin][pColumna];
                 if (!dosCas.estaLibre())
                    if (unaCas.mismoValor(dosCas))
                       repe = true;
                 filaFin = filaFin + 1;
             }
          filaIni = filaIni + 1;
       }
       return repe;
    }

    /**
     * revisarPivote
     *
     * @param pIdZona int
     * @param pFilaPiv int
     * @param pColPiv int
     * @todo Implement this packsudoku.IMatriz method
     */
    private boolean revisarPivote(int pIdZona, int pFilaPiv, int pColPiv) {
        boolean repe = false;
        int auxZona = pIdZona/3;
        int filaIni = auxZona * 3;
        int filaFin = filaIni + 2;
        int colIni = (pIdZona%3) * 3;
        int colFin = colIni + 2;
        Casilla pivote = matriz[pFilaPiv][pColPiv];
        int fila = pFilaPiv;
        int col = pColPiv + 1;
        if (!pivote.estaLibre()) {
            int piv = matriz[pFilaPiv][pColPiv].obtValor();
            while (fila <= filaFin && !repe) {
                while (col <= colFin && !repe) {
                    if (!matriz[fila][col].estaLibre())
                       if (piv == matriz[fila][col].obtValor())
                          repe = true;
                    col = col + 1;
                }
                fila = fila + 1;
                col = colIni;
            }
        }
        return repe;
    }

    /**
     * hayRepetidosZona
     *
     * @param pIdZona int
     * @todo Implement this packsudoku.IMatriz method
     */
    private boolean hayRepetidosZona(int pIdZona) {
       pIdZona = pIdZona - 1;
       boolean repe = false;
       int auxZona = pIdZona/3;
       int filaIni = auxZona * 3;
       int filaFin = filaIni + 2;
       int colIni = (pIdZona%3) * 3;
       int colFin = colIni + 2;
       int fila = filaIni;
       int col = colIni;
       while (fila <= filaFin && !repe) {
          while (col <= colFin && !repe) {
             repe = revisarPivote(pIdZona, fila, col);
             col = col + 1;
          }
          fila = fila + 1;
          col = colIni;
       }
       return repe;
    }

    public boolean estaCompleto() {
       boolean faltan = false;
       int fila = 1;
       int col;
       while (fila <= DIMENSION && !faltan) {
           col = 1;
           while (col <= DIMENSION && !faltan) {
               faltan = obtCasilla(fila, col).estaLibre();
               col = col+1;
           }
           fila = fila+1;
       }
       return !faltan;
    }
    
    public char[] obtListaNotas(int pFila,int pColumna){
    	return obtCasilla(pFila, pColumna).getListaNotas();
    }
    
    public char[] obtListaPosibles(int pFila,int pColumna){
    	return obtCasilla(pFila, pColumna).getListaPosibles();
    }
       
    public char[] obtenerListaNotas(int pFila,int pColumna){
		return obtListaNotas(pFila, pColumna);		
	}
    
    public boolean[][] todosLosNumeros(int pValor){
    	boolean[][] casillasNumero;
    	casillasNumero = new boolean[9][9];
    	for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(obtCasilla(i, j).obtValor() == pValor){
					casillasNumero[i][j] = true;
				}else{
					casillasNumero[i][j] = false;
				}
			}
		}
    	return casillasNumero;
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
	
   
   public boolean eliminateValues(){
		Casilla[][] casillasAux = new Casilla[9][9];
		casillasAux = crearCasillasAux();
		boolean modificado = false;
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				obtCasilla(i, j).rellenarListaPosibles(comprobarListaPosibles(i, j, casillasAux));
				if(obtCasilla(i, j).eliminateValues()){
					modificado = true;
				}
			}
		}
		return modificado;
	}	
	
	private Casilla[][] crearCasillasAux(){
		Casilla[][] cas = new Casilla [9][9];
		char[] listAux = new char[9];
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(obtValor(i, j) != 0){
					cas[i][j] = new Casilla();
					cas[i][j].asgValorInicial(obtValor(i, j));
				}else{
					cas[i][j] = new Casilla();
					cas[i][j].asgValor(0);					
					listAux = obtCasilla(i, j).getListaPosibles();
					for(int k =0; k<9; k++){
						if(Character.isDigit(listAux[k])){
							cas[i][j].asgValor((int)(listAux[k]-'0'));
						}						
					}
				}
				cas[i][j] = obtCasilla(i, j);
			}
		}
		return cas;
	}
    
    private char[] comprobarListaPosibles(int pI, int pJ, Casilla[][] pCas){
		char[] lista = new char[9];
		for(int i=0; i<9; i++) lista[i] = Integer.toString(i+1).charAt(0);
		for(int i= 0; i<9; i++){
			if(i!=pI){
				if(esta(lista, pCas[i][pJ].obtValor())){
					lista[pCas[i][pJ].obtValor()-1] = ' ';
				}
			}
		}
		for(int j =0; j<9; j++){
			if(j!= pJ){
				if(esta(lista, pCas[pI][j].obtValor())){
					lista[pCas[pI][j].obtValor()-1] = ' ';
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
					if(esta(lista, pCas[i][j].obtValor())){
						lista[pCas[i][j].obtValor()-1] = ' ';
					}
				}
				j++;
			}
			i++;
		}
		return lista;
	}
    
    public void imprimir(){
    	for(int i = 0; i < 9; i++){
    		for(int j = 0; j < 9; j++){
    			System.out.print(obtCasilla(i,j).obtValor() + " | ");
    		}
    		System.out.println();
    	}
    }
}
