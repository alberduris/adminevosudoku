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
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
	private GestorBD bd = GestorBD.getGestorBD();

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
			CatalogoSudoku.getCatalogoSudoku().leerBD();
			obtEnJuego();
			if(tablero == null || CatalogoSudoku.getCatalogoSudoku().buscarDificultadPorId(tablero.obtIdSudoku()) == 0){
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
				if(byteArray.available() > 0){
					ObjectInputStream oos = new ObjectInputStream(byteArray);
					tablero = (Tablero) oos.readObject();
				}
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
	
	public boolean[] registrarse(String pNombreUsuario, String pCorreoElectronico, String pContrasena){
		ResultSet res =bd.Select("SELECT NombreUsuario, CorreoElectrónico FROM Jugadores WHERE NombreUsuario='"+pNombreUsuario+"' or CorreoElectrónico='"+pCorreoElectronico+"'");
		boolean[] resultado = new boolean[2];
		resultado[0] = true;
		resultado[1] = true;
		try {
			while(res.next()){
				String correo = res.getString("CorreoElectrónico");
				String nombre = res.getString("NombreUsuario");
				System.out.println(pNombreUsuario+ " , " + nombre.trim());
				System.out.println(pCorreoElectronico+ " , " + correo.trim() );
				if(correo.trim().equals(pCorreoElectronico.trim())){
					resultado[1] = false;
					if(nombre.trim().equals(pNombreUsuario.trim())){
						resultado[0] = false;
					}
				}else if(pNombreUsuario.trim().equals(nombre.trim())){
					resultado[0] = false;
				}
			}
			if(resultado[0] && resultado[1]){
				String contrasena = SHA1.getStringMensageDigest(pContrasena);
				bd.Insertar("INSERT INTO Jugadores (NombreUsuario, CorreoElectrónico, Contraseña) values ('"+pNombreUsuario+"', '"+pCorreoElectronico+"','"+contrasena+"')");
				enviarCorreo(pCorreoElectronico);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public boolean[] identificarse(String pNombreUsuario, String pContrasena){
		ResultSet res =bd.Select("SELECT Contraseña FROM Jugadores WHERE NombreUsuario='"+pNombreUsuario+"' or CorreoElectrónico='"+pNombreUsuario+"'");
		boolean[] resultado = new boolean[2];
		resultado[0] = true;
		resultado[1] = true;
		try {
			if(res.next()){
				if(!SHA1.getStringMensageDigest(pContrasena).equals(res.getString("Contraseña"))){
					resultado[1] = false;
				}
			}else{
				resultado[0] = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;		
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
	      CatalogoJugadores.obtListaJugadores().anadirJugador(jugador);
	   }
	   catch (ExcepcionListaLlena e)
	   {

	   }
    }
	
	public void finSesion(){
    	try {
    		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        	ObjectOutputStream oos = new ObjectOutputStream(byteArray);
	    	oos.writeObject(Tablero.obtTablero());
	    	bd.Update("UPDATE Jugadores SET Tablero=? WHERE NombreUsuario='"+nombreUsuario+"'", byteArray);
//	    	bd.InsertarTablero("INSERT INTO Jugadores values ('"+nombreUsuario+"', 'aa', 'aaa', ?)", byteArray);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void borrarTablero(){
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		bd.Update("UPDATE Jugadores SET Tablero=? WHERE NombreUsuario='"+nombreUsuario+"'", byteArray);
	}
	
	public void anadirSudokuEnJuego(Tablero pTablero){
		tablero = pTablero;
	}
	
	public void recuperarCotrasena(String pCorreoElectronico){
		String contrasena = crearContrasena();
	}
	
	private String crearContrasena(){
		String cont ="";
		char [] caracter;
		int valor = 0;
		Random rnd = new Random();
		for(int i = 0; i<8; i++){
			valor = rnd.nextInt(63);
			if(valor < 10){
			//	caracter = String.valueOf(valor).charAt(0);
				
			}
		}
		return cont;
		
	}
	
	private void enviarCorreo(String pCorreoElectronico){
		Properties props = new Properties();

		// Nombre del host de correo, es smtp.gmail.com
		props.setProperty("mail.smtp.host", "smtp.gmail.com");

		// TLS si está disponible
		props.setProperty("mail.smtp.starttls.enable", "true");

		// Puerto de gmail para envio de correos
		props.setProperty("mail.smtp.port","587");

		// Nombre del usuario
		props.setProperty("mail.smtp.user", "ejemplo@gmail.com");

		// Si requiere o no usuario y password para conectarse.
		props.setProperty("mail.smtp.auth", "true");
		
		//Iniciamos sesión
		Session session = Session.getDefaultInstance(props);
		
		//Creamos el mensaje
		MimeMessage message = new MimeMessage(session);
				
		try {
			// Quien envia el correo
			message.setFrom(new InternetAddress("ejemplo@gmail.com"));
			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(pCorreoElectronico));
			//Asunto
			message.setSubject("Hola");
			//Texto
			message.setText("Mensajito con Java Mail" +
			"de los buenos." +
			"poque si");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//http://www.chuidiang.com/java/herramientas/javamail/enviar-correo-javamail.php		
	}
}

