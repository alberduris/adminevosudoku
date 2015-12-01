package packModelo;

import java.io.Serializable;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import packExcepciones.NoHaySudokuCargadoException;

public class Tablero extends Observable implements Serializable{
	private static Tablero miTablero = new Tablero();
	private Sudoku sudoku;
	private Matriz matrizJuego;
	private boolean terminado = false;
	
	private int tiempo;
	private static Timer time;

	private Tablero() {
            matrizJuego = new Matriz();
            tiempo = -1;
            terminado = false;
            time = new Timer();
            TimerTask timerTask = new TimerTask(){
            	@Override
            	public void run(){
            		if(!terminado){
            			aumentarTiempo();
            		}
            	}
            };
            time.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public static Tablero obtTablero()
    {
        return miTablero;
    }
    
    private void aumentarTiempo(){
    	tiempo++;
    	notifyObservers();
    	setChanged();
    }

    public int obtTiempo(){
    	return tiempo;
    }
    
    /**
     * inicializar
     *
     * @param pSudoku Sudoku
     * @todo Implement this packSudoku.ITablero method
     */
    public void inicializar(Sudoku pSudoku, Matriz pMatriz) {
    	sudoku=pSudoku;
    	if(pMatriz == null){
	        Matriz matrizSolucion = sudoku.obtMatriz();
	    	int valor;
	       for (int fila=0; fila<9; fila++)
	            for (int columna=0; columna<9; columna++){
	            	if (matrizSolucion.esInicial(fila,columna)){
	                    valor = matrizSolucion.obtValor(fila,columna);
	                    matrizJuego.asgValorInicial(fila,columna,valor);
	            	}
	            }
    	}else{
    		matrizJuego = pMatriz;
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
    public int obtValorCasilla(int pFila, int pColumna){
		if (!esValorInicial(pFila,pColumna))
		{
		    return matrizJuego.obtValor(pFila, pColumna);
		}
		else
		{
		    return matrizJuego.obtValor(pFila, pColumna);
		}
    }
    
    public void borrarNumero(int pI, int pJ){
		matrizJuego.quitarValor(pI, pJ);
		setChanged();
		notifyObservers();
	}
    

    /**
     * @return
     */
    public int obtIdSudoku() throws NoHaySudokuCargadoException {
		if (sudoku != null)
		return sudoku.obtIdentificador();
		else
		    throw new NoHaySudokuCargadoException();
	}
    
    public void eliminateValues(){
    	if(matrizJuego.eliminateValues()){
    		tiempo += 300;
    		setChanged();
    		notifyObservers();
    	}
    }
    
    public char[] obtListaNotas(int pFila, int pColumna){
    	return matrizJuego.obtListaNotas(pFila, pColumna);
    }
    
    public boolean[][] comprobarCorrecto(){
    	return sudoku.comprobarCorrecto(matrizJuego);
    }
    
    public boolean[][] todosLosNumeros(int pValor){
    	return matrizJuego.todosLosNumeros(pValor);
    }

    public void terminar(){
    	System.out.println("HA terminado");
		
	}
    
    public int puntuacionConPenalizacion(){
		int penalizacion = 1;
		if(sudoku.obtDificultad()==5){
			penalizacion = 1;
		}else if(sudoku.obtDificultad()==4){
			penalizacion = 2;
		}else{
			penalizacion = (int) (Math.pow(2,(5-sudoku.obtDificultad()))-1);
		}
		
		return tiempo*penalizacion;
	}
    
    public void lanzarRanking(){
    	System.out.println("RANKING LANZADO");
	}
    
    public void reiniciar(){
    	tiempo = 0;
    }
    
    public Sudoku getSudoku(){return sudoku;}
    public Matriz getMatriz(){return matrizJuego;}
    
    public void establecerTablero(Tablero pTablero){
    	tiempo = pTablero.obtTiempo();
    	sudoku = pTablero.getSudoku();
    	matrizJuego = pTablero.getMatriz();
    }
    
    
}
