package packBD;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GestorBD {
	
	private static GestorBD miGestorBD;
	private String ConexionBD = "jdbc:ucanaccess://" + System.getProperty("user.dir")+"/BDJugadores.accdb";
	private String SentenciaSQL;
	private Connection CanalBD;
	private Statement Instruccion;
	private ResultSet Resultado;
	
	private GestorBD(){
		try{
			this.CanalBD = DriverManager.getConnection(this.ConexionBD);
			this.Instruccion = this.CanalBD.createStatement();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error en la conexion con BD\nERROR : "+e.getMessage());
		}
	}
	
	public static GestorBD getGestorBD(){
		if(miGestorBD == null){
			miGestorBD = new GestorBD();
		}
		return miGestorBD;
	}
	
	public void Insertar(String SentenciaSQL){
		this.SentenciaSQL = SentenciaSQL;
		try{
			this.Instruccion.executeUpdate(this.SentenciaSQL);
			JOptionPane.showMessageDialog(null, "CORRECTO");
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error Al insertar\nERROR : "+e.getMessage());			
		}
	}
	
	public void InsertarTablero(String SentenciaSQL, ByteArrayOutputStream byteArray){
		this.SentenciaSQL = SentenciaSQL;
		try{
			PreparedStatement ps = CanalBD.prepareStatement(SentenciaSQL);
			ps.setBytes(1, byteArray.toByteArray());
			ps.execute();
			
		//	this.Instruccion.executeUpdate(this.SentenciaSQL);
			JOptionPane.showMessageDialog(null, "CORRECTO");
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error Al insertar Tablero\nERROR : "+e.getMessage());			
		}
	}
	
	public void Update(String SentenciaSQL){
		this.SentenciaSQL = SentenciaSQL;
		try{
			this.Instruccion.executeUpdate(this.SentenciaSQL);
			JOptionPane.showMessageDialog(null, "CORRECTO MODIFICAR");
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error Al modificar\nERROR : "+e.getMessage());			
		}
	}
	
	public void Eliminar(String SentenciaSQL){
		this.SentenciaSQL = SentenciaSQL;
		try{
			this.Instruccion.executeUpdate(this.SentenciaSQL);
			JOptionPane.showMessageDialog(null, "CORRECTO ELIMINADO");
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error Al eliminar\nERROR : "+e.getMessage());			
		}
	}
	
	public DefaultTableModel Select(String SentenciaSQL){
		this.SentenciaSQL = SentenciaSQL;
		
		String[] TITULOS = {"Nombre Usuario", "Correo Electr�nico", "Contrase�a", "Tablero"};
		String[] REGISTRO = new String[5];
		
		DefaultTableModel TABLA = new DefaultTableModel(null, TITULOS);
		
		try{
			this.Resultado = Instruccion.executeQuery(this.SentenciaSQL);
			while(Resultado.next()){
				REGISTRO[0] = String.valueOf(Resultado.getInt("Identificador"));
				REGISTRO[1] = Resultado.getString("Nombre Usuario");
				REGISTRO[2] = Resultado.getString("Corre Electronico");
				REGISTRO[3] = Resultado.getString("Contrase�a");
				REGISTRO[4] = Resultado.getString("Tablero");
				
				TABLA.addRow(REGISTRO);
			};
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error al cargar los datos\nERROR : "+e.getMessage());			
		}
		return TABLA;
	}
	
	public static void main(String[] arg){
		GestorBD gBD = GestorBD.getGestorBD();
		DefaultTableModel TABLA = gBD.Select("SELECT * FROM Jugadores");
		for(int i= 0; i < TABLA.getRowCount(); i++){
			for(int j = 0; j < TABLA.getColumnCount(); j++){
				System.out.print(TABLA.getValueAt(i, j) + " | ");
			}
			System.out.println(" ");
		}
	}
	
}