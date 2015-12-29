package packModelo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import packBD.GestorBD;

public class GestorEstadisticas {
	
	private static GestorEstadisticas miGestorEstadisticas = new GestorEstadisticas();
	
	private GestorEstadisticas(){
		
	}
	
	public static GestorEstadisticas obtGestorEstadisticas(){
		if(miGestorEstadisticas == null){
			miGestorEstadisticas = new GestorEstadisticas();
		}
		return miGestorEstadisticas;
	}
		
	public String[][] obtenerRetos(){
		int tamano;
		String nom = Sesion.obtSesion().obtNombreUsuario();
		String[][] list = new String[0][0];
		ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(*) FROM ListaRetos WHERE NombreUsuarioRetado='"+nom+"' AND Estado=0");
		try {
			if(res.next()){
				tamano = res.getInt(1);
				list = new String[tamano][3];
				res = GestorBD.getGestorBD().Select("SELECT L.NombreUsuario, L.IdSudoku, R.Puntuación FROM ListaRetos L JOIN Ranking R  WHERE NombreUsuarioRetado='"+nom+"' AND L.Estado=0 AND L.NombreUsuario=R.NombreUsuario AND L.IdSudoku=R.IdSudoku ORDER BY L.IdSudoku");
				int i = 0;
				while(res.next()){
					list[i][0]=res.getString("NombreUsuario");
					list[i][1]=String.valueOf(res.getInt("IdSudoku"));
					list[i][2]=String.valueOf(res.getInt("Puntuación"));
					i++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;
	}
	
	public void aceptarReto(String pNombre, int pId){
		ResultSet res = GestorBD.getGestorBD().Select("SELECT Sudoku FROM Sudokus WHERE Identificador="+pId+"");
		try {
			if(res.next()){
				byte[] b = res.getBytes("Sudoku");
				ByteArrayInputStream byteArray = new ByteArrayInputStream(b);
				ObjectInputStream oos = new ObjectInputStream(byteArray);
				Sudoku sud = (Sudoku) oos.readObject();
				Tablero.obtTablero().inicializar(sud, null);
				String nombre = Sesion.obtSesion().obtNombreUsuario();
				GestorBD.getGestorBD().Update("UPDATE ListaRetos SET Estado=2 WHERE NombreUsuarioRetado='"+nombre+"' AND IdSUdoku="+pId+"");
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rechazarReto(String pNombre, int pId){
		String nombre = Sesion.obtSesion().obtNombreUsuario();
		GestorBD.getGestorBD().Update("UPDATE ListaRetos SET Estado=2 WHERE NombreUsuarioRetado='"+nombre+"' AND IdSudoku="+pId+" AND NombreUsuario='"+pNombre+"'");
	}
	
	public String[][] obtenerRanking(int pTamano, int pIdSudoku){
		String[][] ranking = new String[0][0];
		if(pIdSudoku == 0){
			String consulta;
			int tamano = 0;
			ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(DISTINCT NombreUsuario) FROM Ranking");
			try {
				if(res.next()){
					tamano = res.getInt(1);
				}
				if(pTamano == 0){
					ranking = new String[tamano][2];
					consulta = "SELECT NombreUsuario, SUM(Puntuación) AS Puntuación FROM Ranking GROUP BY NombreUsuario ORDER BY Puntuación DESC";
				}else{
					if(tamano >= pTamano){
						ranking = new String[pTamano][2];
					}else{
						ranking = new String[tamano][2];
					}
					consulta = "SELECT NombreUsuario, SUM(Puntuación) AS Puntuación FROM Ranking GROUP BY NombreUsuario ORDER BY Puntuación DESC LIMIT 0,"+pTamano+"";
				}
				res = GestorBD.getGestorBD().Select(consulta);
				int i = 0;
				while(res.next()){
					ranking[i][0] = res.getString("NombreUsuario");
					ranking[i][1] = res.getString("Puntuación");
					i++;
				}			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return ranking;		
	}
}
