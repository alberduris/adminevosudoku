package packModelo;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class GestorBD {
	
	private static GestorBD miGestorBD;
	private String ConexionBD = "jdbc:mysql://158.227.106.21:3306/Xavelez012_AdminEvoSudoku";
	private String SentenciaSQL;
	private String user = "Xavelez012";
	private String password = "3BjAdMgd";
	private Connection CanalBD;
	private Statement Instruccion;
	private ResultSet Resultado;
	
	private GestorBD(){
		try{
			
			this.CanalBD = DriverManager.getConnection(this.ConexionBD, user, password);
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
		
	public void Update(String SentenciaSQL, ByteArrayOutputStream byteArray){
		this.SentenciaSQL = SentenciaSQL;
		try{
			PreparedStatement ps = CanalBD.prepareStatement(SentenciaSQL);
			if(byteArray != null){
				byte[] bt = byteArray.toByteArray();
				ps.setBytes(1, bt);
			}
				ps.executeUpdate();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error Al actualizar Tablero\nERROR : "+e.getMessage());			
		}
	}
	
	public void Update(String SentenciaSQL){
		this.SentenciaSQL = SentenciaSQL;
		try{
			this.Instruccion.executeUpdate(this.SentenciaSQL);
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error Al modificar\nERROR : "+e.getMessage());			
		}
	}
		
	public ResultSet Select(String SentenciaSQL){
		this.SentenciaSQL = SentenciaSQL;
		
		try{
			this.Resultado = Instruccion.executeQuery(this.SentenciaSQL);
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error al cargar los datos\nERROR : "+e.getMessage());			
		}
		return Resultado;
	}
	
	public static void main(String[] arg) throws SQLException{
		GestorBD gBD = GestorBD.getGestorBD();
		ResultSet TABLA = gBD.Select("SELECT * FROM Jugadores");
		System.out.println(TABLA.next());
		/*for(int i= 0; i < TABLA.getRowCount(); i++){
			for(int j = 0; j < TABLA.getColumnCount(); j++){
				System.out.print(TABLA.getValueAt(i, j) + " | ");
			}
			System.out.println(" ");
		}*/
	}
	
}
