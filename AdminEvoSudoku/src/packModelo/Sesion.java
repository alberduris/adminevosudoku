package packModelo;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class Sesion extends Observable implements Observer {

    private static Sesion mSesion;
	private String nombreUsuario;
	private Tablero tablero;
	private int pistas;
	private int puntos;
	private GestorBD bd = GestorBD.getGestorBD();

	private Sesion()
	{
		Tablero.obtTablero().addObserver(this);
		pistas = 10;
	}
	
	public static Sesion obtSesion()
	{
		if (mSesion == null)
		{
			mSesion = new Sesion();
		}
		return mSesion;
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
		CatalogoSudoku.getCatalogoSudoku().obtIteradorSudokus(pNivel);
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
				bd.Update("INSERT INTO Jugadores (NombreUsuario, CorreoElectrónico, Contraseña) VALUES ('"+pNombreUsuario+"', '"+pCorreoElectronico+"','"+contrasena+"')");
				enviarCorreo(pCorreoElectronico, "Bienvenido", "Bienvenido a AdminEvoSudoku.\nSu nuevos datos de ingreso son:\nNombre de Usuario: "+pNombreUsuario.trim()+"\nContraseña: "+pContrasena);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public boolean[] identificarse(String pNombreUsuario, String pContrasena){
		ResultSet res =bd.Select("SELECT NombreUsuario, CorreoElectrónico, Contraseña, Pistas FROM Jugadores WHERE NombreUsuario='"+pNombreUsuario+"' or CorreoElectrónico='"+pNombreUsuario+"'");
		boolean[] resultado = new boolean[2];
		resultado[0] = true;
		resultado[1] = true;
		try {
			if(res.next()){
				if(!SHA1.getStringMensageDigest(pContrasena).equals(res.getString("Contraseña"))){
					resultado[1] = false;
				}else{
					nombreUsuario = res.getString("NombreUsuario");
					pistas = res.getInt("Pistas");
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
			/*int puntosPartida  = 0;
	    	if (tablero.obtNumErrores() == 0){
		    Date horaFinal = new Date();
		    int tiempo = (int)(horaFinal.getTime() - horaInicio.getTime())/1000;
		    puntosPartida = (30000*tablero.obtNivel()/tiempo);
			this.puntos = Math.max(puntos,puntosPartida);
		    }		
			horaInicio = null;*/
		}
		
	}

	/**
	 * @return
	 */
	public int obtNivel(){
		return Tablero.obtTablero().obtNivel();
	}

	/**
	 * 
	 */
	public void actualizarPuntuaciones()
	{
	   Jugador jugador = new Jugador(nombreUsuario);
	   jugador.actualizarPuntos(puntos);
	   
	   //TODO
    }
	
	public void actualizarPistas(int pCantidad){
		pistas += pCantidad;
	}
	
	public int obtPistas(){
		return pistas;
	}
	
	public void finSesion(boolean pIniciado){
    	try {
    		if(pIniciado){
    			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            	ObjectOutputStream oos = new ObjectOutputStream(byteArray);
    	    	oos.writeObject(Tablero.obtTablero());
    	    	bd.Update("UPDATE Jugadores SET Tablero=?, Pistas="+pistas+" WHERE NombreUsuario='"+nombreUsuario+"'", byteArray);
    		}
    		nombreUsuario = "";
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void borrarTablero(){
		bd.Update("UPDATE Jugadores SET Tablero=NULL WHERE NombreUsuario='"+nombreUsuario+"'");
		
		if(tablero != null){
			int id = tablero.obtIdSudoku();;
			bd.Update("INSERT INTO Ranking (NombreUsuario, IdSudoku, Puntuación) VALUES ('"+nombreUsuario+"',"+id+",0)");
		}
	}
	
	public void anadirSudokuEnJuego(Tablero pTablero){
		tablero = pTablero;
	}
	
	public boolean recuperarCotrasena(String pCampo){
		
		ResultSet res =bd.Select("SELECT NombreUsuario, CorreoElectrónico FROM Jugadores WHERE NombreUsuario='"+pCampo+"' OR CorreoElectrónico='"+pCampo+"'");
		String correo;
		boolean resultado = true;
		try {
			if(res.next()){
				correo = res.getString("CorreoElectrónico");
				String contrasena = crearContrasena();
				enviarCorreo(correo, "Recuperación de contraseña", "Su nueva contraseña es: " + contrasena);
				bd.Update("UPDATE Jugadores SET Contraseña ='"+SHA1.getStringMensageDigest(contrasena)+"' WHERE CorreoElectrónico = '"+correo+"'");
			}else{
				resultado = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;	
	}
	
	public void actualizarNombreDeUsuario(String pNombreUsuario){
		
		String pUsuario = obtSesion().nombreUsuario;
		
		bd.Update("UPDATE Jugadores SET NombreUsuario ='"+pNombreUsuario+"' "
				+ "WHERE NombreUsuario = '"+pUsuario+"'");
		
	}
	
	public void actualizarEmail(String pEmail){
		
		String pUsuario = obtSesion().nombreUsuario;
		
		bd.Update("UPDATE Jugadores SET CorreoElectrónico ='"+pEmail+"' "
				+ "WHERE NombreUsuario = '"+pUsuario+"'");
		
	}
	
	public boolean actualizarPassword(String pAnteriorPass, String pPass){
		boolean correcto = false;
		ResultSet res = bd.Select("SELECT Contraseña FROM Jugadores WHERE NombreUsuario='"+nombreUsuario+"'");
		try {
			if(res.next()){
				if(SHA1.getStringMensageDigest(pAnteriorPass).equals(res.getString("Contraseña"))){
					String contrasena = SHA1.getStringMensageDigest(pPass);
					bd.Update("UPDATE Jugadores SET Contraseña ='"+contrasena+"' "
							+ "WHERE NombreUsuario = '"+nombreUsuario+"'");
					correcto = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return correcto;
		
	}
	
	private String crearContrasena(){
		String cont ="";
		char [] caracter = new char[8];
		int valor = 0;
		Random rnd = new Random();
		for(int i = 0; i<8; i++){
			valor = rnd.nextInt(51);
			if(valor < 26){
				caracter[i] = (char) (valor+65);
			}else{
				caracter[i] = (char)(valor+72);
			}
		}
		for(int i = 0; i<8; i++){
			cont += caracter[i];
		}
		return cont;	
	}
	
	private void enviarCorreo(String pCorreoElectronico, String pAsunto, String pMensaje){
		Properties props = System.getProperties();

		props.setProperty("mail.smtp.host", "adevosudoku@gmail.com");
		
		// Nombre del host de correo, es smtp.gmail.com
		props.setProperty("mail.smtp.host", "smtp.gmail.com");

		// TLS si está disponible
		props.setProperty("mail.smtp.starttls.enable", "true");

		// Puerto de gmail para envio de correos
		props.setProperty("mail.smtp.port","587");

		// Nombre del usuario
		props.setProperty("mail.smtp.user", "adevosudoku@gmail.com");

		// Si requiere o no usuario y password para conectarse.
		props.setProperty("mail.smtp.auth", "true");
		
		//Iniciamos sesión
		Session session = Session.getDefaultInstance(props);
		
		//Creamos el mensaje
		MimeMessage message = new MimeMessage(session);
				
		try {
			// Quien envia el correo
			message.setFrom(new InternetAddress("adevosudoku@gmail.com"));
			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(pCorreoElectronico));
			//Asunto
			message.setSubject(pAsunto);
			//Texto
			message.setText(pMensaje);
			Transport t = session.getTransport("smtp");
			t.connect("adevosudoku@gmail.com", "adminevosudoku");
			t.sendMessage(message,message.getAllRecipients());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//http://www.chuidiang.com/java/herramientas/javamail/enviar-correo-javamail.php		
	}
	
	public void lanzarSudoku(int pNivel, int pId, int pTiempo){
		Tablero tab = Tablero.obtTablero();
		try{
			ResultSet res = GestorBD.getGestorBD().Select("SELECT Sudoku FROM Sudokus WHERE Nivel="+pNivel+" AND Identificador NOT IN(SELECT IdSudoku FROM Ranking WHERE NombreUsuario='"+nombreUsuario+"' AND Puntuación!=0)ORDER BY RAND() LIMIT 1, 1");
			if(res.next()){
				byte[] b = res.getBytes("Sudoku");
				ByteArrayInputStream byteArray = new ByteArrayInputStream(b);
				ObjectInputStream oos = new ObjectInputStream(byteArray);
				Sudoku sud = (Sudoku) oos.readObject();
				tab.inicializar(sud, null);
			}else{
				tab.reiniciar();
			}
			if(tablero != null){
				borrarTablero();
			}
			tab.pausado(false);
		}catch (ClassNotFoundException | SQLException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(pTiempo != 0){
			tab.configTiempo(pTiempo);
		}
	}
	
	public String[] obtJugadores(){
		int id = Tablero.obtTablero().obtIdSudoku();
		ResultSet count = GestorBD.getGestorBD().Select("SELECT COUNT(NombreUsuario) FROM Jugadores WHERE NombreUsuario NOT IN(SELECT NombreUsuario FROM Ranking WHERE IdSudoku="+id+" AND Puntuación!=0)");
		
		String[] aux = null;
		
		try {
			
			if(count.next()){
				aux = new String[(int) count.getLong(1)];
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultSet resultado = GestorBD.getGestorBD().Select("SELECT NombreUsuario FROM Jugadores WHERE NombreUsuario NOT IN(SELECT NombreUsuario FROM Ranking WHERE IdSudoku="+id+" AND Puntuación!=0)");
		int i = 0;		
		
		try {
			while(resultado.next()){
				aux[i] = resultado.getString("NombreUsuario");
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aux;
	}
	
	public void compartir(String pMensaje){
		try{			
			if(java.awt.Desktop.isDesktopSupported()){
				Desktop dk = Desktop.getDesktop();
				dk.browse(new URI("www.twitter.com/home?status="+pMensaje));
			}
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null,  "Error: "+e1);
		}	
	}
}

