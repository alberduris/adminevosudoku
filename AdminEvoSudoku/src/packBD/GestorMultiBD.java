package packBD;

import java.beans.PropertyVetoException;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class GestorMultiBD {
	private static GestorMultiBD miGestorBD;
	private String ConexionBD = "jdbc:mysql://158.227.106.21:3306/Xavelez012_AdminEvoSudoku";
	private String SentenciaSQL;
	private String user = "Xavelez012";
	private String password = "3BjAdMgd";
	private Connection CanalBD;
	private Statement Instruccion;
	private ResultSet Resultado;
	private ComboPooledDataSource pool;
	
	private GestorMultiBD(){
		try{
			pool = new ComboPooledDataSource();
			pool.setDriverClass("com.mysql.jdbc.Driver");
			pool.setJdbcUrl(ConexionBD);
			pool.setUser(user);
			pool.setPassword(password);
			pool.setMaxPoolSize(100);
			pool.setMinPoolSize(10);
			pool.setBreakAfterAcquireFailure(true);
			
			CanalBD = pool.getConnection();
			Instruccion = CanalBD.createStatement();
			
		}catch(PropertyVetoException | SQLException e){
			JOptionPane.showMessageDialog(null, "Error en la conexion con BD\nERROR : "+e.getMessage());
		}
	}
	
	public static GestorMultiBD getGestorMultiBD(){
		if(miGestorBD == null){
			miGestorBD = new GestorMultiBD();
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

	public ResultSet Select(String pSentenciaSQL) throws PropertyVetoException{
		Connection con = null;
		Statement st = null;
		try{
			con = pool.getConnection();
			st = con.createStatement();
			
			this.SentenciaSQL = pSentenciaSQL;
			Resultado = st.executeQuery(this.SentenciaSQL);
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error al cargar los datos\nERROR : "+e.getMessage());			
		}finally{
			try{
				if(con != null){
					con.close();
				}
				if(st != null){
					st.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return Resultado;
	}
	
	public void close(){
		try{
			if(CanalBD != null){
				CanalBD.close();
			}
			if(Instruccion != null){
				Instruccion.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void setTamano(int pMax){
		pool.setMaxPoolSize(pMax);
	}
	
	
	public static void main(String[] arg){
		GestorMultiBD gB = GestorMultiBD.getGestorMultiBD();
		try {
			ResultSet res1 = gB.Select("SELECT Sudoku FROM Sudokus LIMIT 0,100");
			System.out.println("ENTRA1");
			ResultSet res2 = gB.Select("SELECT Sudoku FROM Sudokus LIMIT 100,100");
			System.out.println("ENTRA2");
			ResultSet res3 = gB.Select("SELECT Sudoku FROM Sudokus LIMIT 200,81");
			System.out.println("ENTRA3");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
