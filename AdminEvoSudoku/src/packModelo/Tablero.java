package packModelo;

import java.io.Serializable;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Tablero extends Observable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Tablero miTablero = new Tablero();
	private Sudoku sudoku;
	private Matriz matrizJuego;
	private boolean terminado = false;
	private boolean pausado = false;
	private boolean tiempoAjustado = false;
	private int intentos;
	
	private int tiempo;
	private static Timer time;

	private Tablero() {
			intentos = 5;
            matrizJuego = new Matriz();
            terminado = false;
            pausado = true;
            tiempo = -1;
            time = new Timer();
            tiempoAjustado = false;
            TimerTask timerTask = new TimerTask(){
            	@Override
            	public void run(){
            		if(!terminado && !pausado){
            			cambiarTiempo();
            		}
            	}
            };
            time.scheduleAtFixedRate(timerTask, 0, 1000);
    }
	
    public static Tablero obtTablero()
    {
        return miTablero;
    }
    
    public void configTiempo(int pTiempo){
    	tiempoAjustado = true;
    	tiempo = pTiempo;
    }
    
    public void pausado (boolean pEstado){
    	pausado = pEstado;
    }
    
    private void cambiarTiempo(){
    	if(tiempoAjustado){
    		if(tiempo == 0){
    			terminar();
    		}else{
        		tiempo--;    			
    		}
    	}else{
        	tiempo++;
    	}
    	notifyObservers();
    	setChanged();
    }
    
    public boolean obtTiempoAjustado(){
    	return tiempoAjustado;
    }
    
    public int obtIntentos(){
		return intentos;
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
    

   public int obtNumErrores(){
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
    public int obtNivel(){
	if (sudoku != null){
	    return sudoku.obtDificultad();
	}else{
		return 0;
	}
    }

    /**
     * @return
     */
    public boolean finalDeJuego(){
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
    
    public boolean obtPausado(){
    	return pausado;
    }
    /**
     * @return
     */
    public int obtIdSudoku(){
		if (sudoku != null)
		return sudoku.obtIdentificador();
		else
		  return 0;
	}
    
    public void eliminateValues(){
    	if(matrizJuego.eliminateValues()){
    		Sesion.obtSesion().actualizarPistas(-1);
    		setChanged();
    		notifyObservers();
    	}
    }
    
    public char[] obtListaNotas(int pFila, int pColumna){
    	return matrizJuego.obtListaNotas(pFila, pColumna);
    }
    
    public boolean[][] comprobarCorrecto(){
    	boolean[][] errores = sudoku.comprobarCorrecto(matrizJuego);
    	if(errores.length != 0){
    		intentos--;
    	}
    	return errores;
    }
    
    public boolean[][] todosLosNumeros(int pValor){
    	return matrizJuego.todosLosNumeros(pValor);
    }

    public void terminar(){
    	terminado = true;
    	String nom = Sesion.obtSesion().obtNombreUsuario();
    	int id = sudoku.obtIdentificador();
    	int punt = obtPuntuacion();  
    	GestorBD.getGestorBD().Update("UPDATE Jugadores SET Pistas="+Sesion.obtSesion().obtPistas()+" WHERE NombreUsuario='"+nom+"'");
    	GestorBD.getGestorBD().Update("UPDATE ListaRetos SET Estado=2 WHERE NombreUsuarioRetado='"+nom+"' AND IdSudoku="+id+" AND Estado=0");
    	GestorBD.getGestorBD().Update("INSERT INTO Ranking (NombreUsuario, IdSudoku, Puntuación, Tiempo) VALUES ('"+nom+"',"+id+","+punt+", "+tiempo+")");
	}
    
    public int obtPuntuacion(){
		int puntuacion = 0;
		double pen = 0;
    	if(tiempo != 0){
    		pen = (double)(tiempo)/(double)(sudoku.obtDificultad()*1000);
			puntuacion = (int) (1000/pen);
    	}		
		return puntuacion;
	}
    
    public void reiniciar(){
    	tiempo = 0;
    }
    
    public Sudoku getSudoku(){return sudoku;}
    public Matriz getMatriz(){return matrizJuego;}
    
    public void establecerTablero(Tablero pTablero){
    	intentos = pTablero.obtIntentos();
    	tiempo = pTablero.obtTiempo();
    	sudoku = pTablero.getSudoku();
    	matrizJuego = pTablero.getMatriz();
    	tiempoAjustado = pTablero.obtTiempoAjustado();
    	pausado = true;
    }    
}
