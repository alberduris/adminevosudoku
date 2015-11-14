package packModelo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.DefaultTableModel;

import packBD.GestorBD;
import packExcepciones.ExcepcionListaLlena;
import packExcepciones.NoHaySudokuCargadoException;

public class Sesion extends Observable implements Observer {

    private static Sesion mSesion;
	private String nombreUsuario;
	private Iterator<Sudoku> iter;
	private Tablero tablero;
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
	
	public void iniciarJuego() throws NoHaySudokuCargadoException
	{
		try
		{
			obtEnJuego();
			if(tablero == null || CatalogoSudoku.getCatalogoSudoku().buscarSudokuPorId(tablero.obtIdSudoku()) == null){
			    Tablero.obtTablero().inicializar(iter.next(), null);
			}else{
				Tablero.obtTablero().establecerTablero(tablero);
			}
		    horaInicio = new Date();
		} catch (RuntimeException e)
		{
		    horaInicio = null;
		}
	}
	
	
	public void obtEnJuego(){
		GestorBD gBD = GestorBD.getGestorBD();
		ResultSet resultado = gBD.Select("SELECT Tablero FROM Jugadores WHERE NombreUsuario='"+nombreUsuario+"'");
		try {
			if(resultado.next()){
				byte[] b = resultado.getBytes("Tablero");
				ByteArrayInputStream byteArray = new ByteArrayInputStream(b);
				ObjectInputStream oos = new ObjectInputStream(byteArray);
				tablero = (Tablero) oos.readObject();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String obtNombreUsuario()
	{
		return nombreUsuario;
	}
	
	public void ponNivel(int pNivel)
	{
		iter = CatalogoSudoku.getCatalogoSudoku().obtIteradorSudokus(pNivel);
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
	
	public void finSesion(){
		GestorBD bd = GestorBD.getGestorBD();
    	try {
    		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        	ObjectOutputStream oos = new ObjectOutputStream(byteArray);
	    	oos.writeObject(Tablero.obtTablero());
	    	bd.InsertarTablero("INSERT INTO Jugadores values ('"+nombreUsuario+"', 'aa', 'aaa', ?)", byteArray);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void anadirSudokuEnJuego(Tablero pTablero){
		tablero = pTablero;
	}
}

