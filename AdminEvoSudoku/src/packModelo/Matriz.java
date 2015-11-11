package packModelo;

public class Matriz {

    // ATRIBUTOS DE LA CLASE
    // Dimensi�n del Sudoko
    private final int DIMENSION = 9;
    // Matriz para recoger los valores del Sudoku
    private Casilla[][] matriz;

    // Constructora
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
        Casilla unaCasilla = matriz[pFila-1][pColumna-1];
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
        Casilla unaCasilla = matriz[pFila-1][pColumna-1];
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
        Casilla unaCasilla = matriz[pFila-1][pColumna-1];
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
        Casilla unaCasilla = matriz[pFila-1][pColumna-1];
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
        Casilla unaCasilla = matriz[pFila-1][pColumna-1];
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
        return matriz[pFila-1][pColumna-1];
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
       pIdZona = pIdZona - 1;//S�lo si las zonas se numeran de 1-9
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


}
