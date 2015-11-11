package packModelo;

public class Sudoku {

    // Identificador del sudoku
    private String identificador;
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
    public Sudoku(String pIdentificador, int pDificultad) {
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
    public String obtIdentificador() {
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
}
