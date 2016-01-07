package packModelo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

public class GestorAdministrador extends Observable{

	private static GestorAdministrador miGestor;
	private Sudoku sud;
	
	private GestorAdministrador(){
		sud = null;
	}
	
	public static GestorAdministrador getGestorAdministrador(){
		if(miGestor == null){
			miGestor = new GestorAdministrador();
		}
		return miGestor;
	}
		
	public boolean[][] crearSudoku(int pDif, int[][] pSud, boolean[][] pFijas){
		int id = CatalogoSudoku.getCatalogoSudoku().buscarPrimerIdDisp();
		sud = new Sudoku(id, pDif);
		boolean[][] result;
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				if(pFijas[i][j]){
					sud.asgValorInicial(i, j, pSud[i][j]);
				}else{
					sud.asgValor(i, j, pSud[i][j]);
				}
			}
		}
		result = sud.comprobarCorrecto(sud.obtMatriz());
		if(result.length == 0){
			CatalogoSudoku.getCatalogoSudoku().anadirSudoku(sud);
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(byteArray);
				oos.writeObject(sud);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GestorBD.getGestorBD().Update("INSERT INTO Sudokus values ("+id+","+pDif+",?)",byteArray);
			
		}
		return result;
	}
	
	public void introducirSudoku(int pIdSudoku){
		ResultSet res = GestorBD.getGestorBD().Select("SELECT Sudoku FROM Sudokus WHERE Identificador="+pIdSudoku+"");
		try {
			if(res.next()){
				byte[] b = res.getBytes("Sudoku");
				ByteArrayInputStream byteArray = new ByteArrayInputStream(b);
				ObjectInputStream oos = new ObjectInputStream(byteArray);
				sud = (Sudoku) oos.readObject();

			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int obtValorCasilla(int pI, int pJ){
		return sud.obtMatriz().obtValor(pI, pJ);
	}
	
	public boolean obtFijas(int pI, int pJ){
		return sud.obtMatriz().obtCasilla(pI, pJ).esInicial();
	}
	
	public boolean[][] modificarSudoku(int pDif, int[][] pSud, boolean[][] pFijas){
		int id = sud.obtIdentificador();
		sud = new Sudoku(id, pDif);
		boolean[][] result;
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				if(pFijas[i][j]){
					sud.asgValorInicial(i, j, pSud[i][j]);
				}else{
					sud.asgValor(i, j, pSud[i][j]);
				}
			}
		}
		result = sud.comprobarCorrecto(sud.obtMatriz());
		if(result.length == 0){
			CatalogoSudoku.getCatalogoSudoku().anadirSudoku(sud);
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(byteArray);
				oos.writeObject(sud);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GestorBD.getGestorBD().Update("UPDATE Sudokus SET Sudoku=? WHERE id='"+id+"'", byteArray);
			
		}
		return result;
	}

	public Sudoku obtSud(){
		return sud;
	}
	
	public void eliminarSudoku(){
		GestorBD.getGestorBD().Update("DELETE FROM Sudokus WHERE Identificador="+sud.obtIdentificador()+"");
		CatalogoSudoku.getCatalogoSudoku().eliminarSudoku(sud.obtIdentificador());
	}
	
	public void modificarEstadoSudoku(boolean pEstado){
		GestorBD.getGestorBD().Update("UPDATE Sudokus SET Activo="+pEstado+" WHERE Identificador="+sud.obtIdentificador()+"");
		CatalogoSudoku.getCatalogoSudoku().modificarEstadoSudoku(sud.obtIdentificador(), pEstado);
	}
	
	public boolean anadirPremio(String pNombrePremio, int pIdSudoku, int pLimite, String pTipo){
		ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(*) FROM Premios WHERE NombrePremio='"+pNombrePremio+"'");
		boolean error = false;
		try {
			if(res.next()){
				error = true;
			}else{
				GestorBD.getGestorBD().Update("INSERT Premios(NombrePremio, IdSudoku, Limite, Tipo) VALUES ('"+pNombrePremio+"',"+pIdSudoku+","+pLimite+",'"+pTipo+"')");
				res.close();
				if(pTipo.equals("Tiempo")){
					res.close();
					res = GestorBD.getGestorBD().Select("SELECT NombreUsuario WHERE IdSudoku="+pIdSudoku+" AND Tiempo<"+pLimite);
					while(res.next()){
						GestorBD.getGestorBD().Update("INSERT ListaPremiados(NombreUsuario, NombrePremio) VALUES ('"+res.getString("NombreUsuario")+"','"+pNombrePremio+"')");
					}
				}else{
					res.close();
					res = GestorBD.getGestorBD().Select("SELECT NombreUsuario WHERE IdSudoku="+pIdSudoku+" AND Puntuación>"+pLimite);
					while(res.next()){
						GestorBD.getGestorBD().Update("INSERT ListaPremiados(NombreUsuario, NombrePremio) VALUES ('"+res.getString("NombreUsuario")+"','"+pNombrePremio+"')");
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return error;
	}
	
	public void eliminarPremio(String pNombrePremio){
		GestorBD.getGestorBD().Update("DELETE FROM Premios WHERE NombrePremio='"+pNombrePremio+"'");
		GestorBD.getGestorBD().Update("DELETE FROM ListaPremiados WHERE NombrePremio='"+pNombrePremio+"'");
	}
	
	public String[][] obtPremios(){
		String[][] info = new String[0][0];
		try {
			ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(NombrePremio) FROM Premios");
			res.next();
			info = new String[res.getInt(1)][4];
			res = GestorBD.getGestorBD().Select("SELECT NombrePremio, IdSudoku, Limite, Tipo FROM Premios");
			int i = 0;
			while(res.next()){
				info[i][0] = res.getString("NombrePremio");
				info[i][1] = String.valueOf(res.getInt("IdSudoku"));
				info[i][2] = String.valueOf(res.getInt("Limite"));
				info[i][3] = res.getString("Tipo");
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return info;
	}
	
}