package packModelo;

import java.util.Date;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import packExcepciones.ExcepcionListaLlena;
import packExcepciones.NoHaySudokuCargadoException;

public class Sesion extends Observable implements Observer {

    private static Sesion mSesion;
	private String nombreUsuario;
	private Iterator<Sudoku> iter;
	private Date horaInicio;
	private int puntos;

	private Sesion()
	{
		Tablero.obtTablero().addObserver(this);
		nombreUsuario = "Anonimo";
	}
	
	public static Sesion obtSesion()
	{
		if (mSesion == null)
		{
			mSesion = new Sesion();
		}
		return mSesion;
	}
	
	public void iniciarJuego()
	{
		try
		{
		    Tablero.obtTablero().inicializar(iter.next());
		    horaInicio = new Date();
		} catch (RuntimeException e)
		{
		    horaInicio = null;
		}
	}
	
	
	
	public String obtNombreUsuario()
	{
		return nombreUsuario;
	}
	
	public void ponNivel(int pNivel)
	{
		iter = ListaSudokus.obtListaSudokus().obtIterador(pNivel);
	}
	
	public void ponNombreUsuario(String pNombre)
	{
		nombreUsuario = pNombre;
	}
	
	public void update(Observable pObservador, Object pObjeto) {
		Tablero tablero = Tablero.obtTablero();
		if (tablero.finalDeJuego())
		{
			int puntosPartida  = 0;
		    	if (tablero.obtNumErrores() == 0)
			{
			    Date horaFinal = new Date();
			    int tiempo = (int)(horaFinal.getTime() - horaInicio.getTime())/1000;
			    try
			    {
				puntosPartida = (30000*tablero.obtNivel()/tiempo);
				this.puntos = Math.max(puntos,puntosPartida);
			    } catch (NoHaySudokuCargadoException e)
			    {
			    }
			}			
			horaInicio = null;
		}
		
	}

	/**
	 * @return
	 */
	public int obtNivel()
	{
	    try
	    {
		return Tablero.obtTablero().obtNivel();
	    } catch (NoHaySudokuCargadoException e)
	    {
		return 1;
	    }
	}

	/**
	 * 
	 */
	public void actualizarPuntuaciones()
	{
	   Jugador jugador = new Jugador(nombreUsuario);
	   jugador.actualizarPuntos(puntos);
	   try
	   {
	      ListaJugadores.obtListaJugadores().anadirJugador(jugador);
	   }
	   catch (ExcepcionListaLlena e)
	   {

	   }
    }
}

