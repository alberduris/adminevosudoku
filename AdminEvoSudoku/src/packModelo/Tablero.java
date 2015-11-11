package packModelo;

import java.util.Observable;

import packExcepciones.NoHaySudokuCargadoException;

public class Tablero extends Observable {
	private static Tablero miTablero = new Tablero();
	private Sudoku sudoku;
	private Matriz matrizJuego;
	private boolean terminado = false;

	private Tablero() {
            matrizJuego = new Matriz();
    }

    public static Tablero obtTablero()
    {
        return miTablero;
    }

    /**
     * inicializar
     *
     * @param pSudoku Sudoku
     * @todo Implement this packSudoku.ITablero method
     */
    public void inicializar(Sudoku pSudoku) {
    	sudoku=pSudoku;
        Matriz matrizSolucion = sudoku.obtMatriz();
    	int valor;
       for (int fila=1; fila<=9; fila++)
            for (int columna=1; columna<=9; columna++){
            	if (matrizSolucion.esInicial(fila,columna)){
                    valor = matrizSolucion.obtValor(fila,columna);
                    matrizJuego.asgValorInicial(fila,columna,valor);
            	}
            }
       setChanged();
       notifyObservers();
    }

    /**
     * asgValor
     *
     * @param pFila int
     * @param pColumna int
     * @param valor int
     * @todo Implement this packSudoku.ITablero method
     */
    public void asgValor(int pFila, int pColumna, int valor) {
    	matrizJuego.asgValor(pFila,pColumna, valor);
    	if (matrizJuego.estaCompleto() && obtNumErrores() == 0)
    	{
    	    terminado = true;
    	}
    	 setChanged();
         notifyObservers();
    }

    /**
     * quitarValor
     *
     * @param pFila int
     * @param pColumna int
     * @todo Implement this packSudoku.ITablero method
     */
    public void quitarValor(int pFila, int pColumna) {
       	matrizJuego.quitarValor(pFila,pColumna);
       	terminado = false;
        setChanged();
        notifyObservers();;
    }

    public boolean hayInconsistencias(char pArea, int pIdArea)
    {
	  return matrizJuego.descubrirInconsistencias(pArea, pIdArea);
    }
    

   public int obtNumErrores()
   {
      return matrizJuego.comprobarSolucion(sudoku.obtMatriz());
   }



    /**
     * jugar
     *
     * @return boolean
     * @todo Implement this packSudoku.ITablero method
     */
  

    public boolean esValorInicial(int pFila, int pColumna) {
        return matrizJuego.esInicial(pFila,pColumna);
    }

    public boolean estaLibre(int pFila, int pColumna) {
        return matrizJuego.estaLibre(pFila,pColumna);
    }

    /**
     * @return
     */
    public int obtNivel() throws NoHaySudokuCargadoException
    {
	if (sudoku == null)
	{
	    throw new NoHaySudokuCargadoException();
	 }
	else
	{
	    return sudoku.obtDificultad();
	}
    }

    /**
     * @return
     */
    public boolean finalDeJuego()
    {
	return terminado;
    }

    /**
     * @return
     */
    public int obtValorCasilla(int pFila, int pColumna)
    {
	if (!esValorInicial(pFila,pColumna))
	{
	    return matrizJuego.obtValor(pFila, pColumna);
	}
	else
	{
	    return sudoku.obtMatriz().obtValor(pFila, pColumna);
	}
    }

    /**
     * @return
     */
    public String obtIdSudoku() throws NoHaySudokuCargadoException
    {
	if (sudoku != null)
	return sudoku.obtIdentificador();
	else
	    throw new NoHaySudokuCargadoException();
    }

}
