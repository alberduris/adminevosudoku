package packModelo;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

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
		}else{
			String consulta;
			int tamano = 0;
			ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(DISTINCT NombreUsuario) FROM Ranking WHERE IdSudoku="+pIdSudoku+"");
			try {
				if(res.next()){
					tamano = res.getInt(1);
				}
				ranking = new String[tamano][2];
				consulta = "SELECT NombreUsuario, Puntuación FROM Ranking WHERE IdSudoku="+pIdSudoku+" ORDER BY Puntuación DESC";
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
	
	public String[] obtSudokusJugados(String pNombre){
		ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(IdSudoku) FROM Ranking WHERE NombreUsuario='"+pNombre+"' AND Puntuación != 0" );
		int tam;
		String[] lista = new String[0];
		try {
			res.next();
			tam = res.getInt(1);
			lista = new String[tam];
			res = GestorBD.getGestorBD().Select("SELECT IdSudoku FROM Ranking WHERE NombreUsuario='"+pNombre+"' AND Puntuación != 0" );
			int i = 0;
			while(res.next()){
				lista[i] = String.valueOf(res.getInt("IdSudoku"));
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	
	public String[][] obtPuntSudokusJugados(String pNombre){
		ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(IdSudoku) FROM Ranking WHERE NombreUsuario='"+pNombre+"' AND Puntuación != 0" );
		int tam;
		String[][] lista = new String[0][2];
		try {
			res.next();
			tam = res.getInt(1);
			lista = new String[tam][2];
			res = GestorBD.getGestorBD().Select("SELECT IdSudoku, Puntuación FROM Ranking WHERE NombreUsuario='"+pNombre+"' AND Puntuación != 0" );
			int i = 0;
			while(res.next()){
				lista[i][0] = String.valueOf(res.getInt("IdSudoku"));
				lista[i][1] = String.valueOf(res.getInt("Puntuación"));
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	
	public String[] obtInfoSudoku(int pId){
		String[] info = new String[3];
		try {
			ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(NombreUsuario) FROM Ranking WHERE Puntuación!=0 AND IdSudoku="+pId+"");
			res.next();
			info[0] = String.valueOf(res.getInt(1));
			res = GestorBD.getGestorBD().Select("SELECT COUNT(P.NombreUsuario), COUNT(DISTINCT(S.NombreUsuario)) FROM Ranking P, Ranking S WHERE S.Puntuación!=0 AND S.IdSudoku= P.IdSudoku AND P.IdSudoku="+pId+"");
			res.next();
			float p1 = res.getInt(1);
			float result;
			if(p1 == 0){
				result = 0;
			}else{
				result = (float)(res.getInt(2))/(float)(p1)*100;
			}
			info[1] = String.valueOf(result);
			res = GestorBD.getGestorBD().Select("SELECT AVG(Tiempo) FROM Ranking WHERE Puntuación!=0 AND IdSudoku="+pId+"");
			res.next();
			info[2] = String.valueOf(res.getLong(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return info;
	}
	
	public String[] obtInfoUsuario(String pNombreUsuario){
		String[] info = new String[7];
		try {
			ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(IdSudoku) FROM Ranking WHERE Puntuación!=0 AND NombreUsuario='"+pNombreUsuario+"'");
			res.next();
			info[0] = String.valueOf(res.getInt(1));
			res = GestorBD.getGestorBD().Select("SELECT COUNT(P.IdSudoku), COUNT(DISTINCT(S.IdSudoku)) FROM Ranking P, Ranking S WHERE S.Puntuación!=0 AND S.IdSudoku= P.NombreUsuario AND P.NombreUsuario='"+pNombreUsuario+"'");
			res.next();
			float p1 = res.getInt(1);
			float result;
			if(p1 == 0){
				result = 0;
			}else{
				result = (float)(res.getInt(2))/(float)(p1)*100;
			}
			info[1] = String.valueOf(result);
			for(int i =1; i<6; i++){
				res = GestorBD.getGestorBD().Select("SELECT AVG(Tiempo) FROM Ranking WHERE Puntuación!=0 AND NombreUsuario='"+pNombreUsuario+"' AND IdSudoku IN (SELECT Identificador FROM Sudokus WHERE Nivel="+i+")");
				res.next();
				info[i+1] = String.valueOf(res.getInt(1));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return info;
	}
	
	public String[] obtJugadores(){
		ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(NombreUsuario) FROM Jugadores");
		String[] lista = new String[0];
		try {
			res.next();
			lista = new String[res.getInt(1)];
			res = GestorBD.getGestorBD().Select("SELECT NombreUsuario FROM Jugadores");
			int i = 0;
			while(res.next()){
				lista[i] = res.getString("NombreUsuario");
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;		
	}

	public String[][] obtenerRetosFinalizados() {
		int tamano;
		String nom = Sesion.obtSesion().obtNombreUsuario();
		String[][] list = new String[0][0];
		ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(*) FROM ListaRetos WHERE NombreUsuarioRetado='"+nom+"' AND Estado=0");
		try {
			if(res.next()){
				tamano = res.getInt(1);
				list = new String[tamano][4];
				res = GestorBD.getGestorBD().Select("SELECT L.NombreUsuario, L.IdSudoku, R.Puntuación, L.Estado FROM ListaRetos L JOIN Ranking R  WHERE NombreUsuarioRetado='"+nom+"' AND L.Estado!=0 AND L.NombreUsuario=R.NombreUsuario AND L.IdSudoku=R.IdSudoku ORDER BY L.IdSudoku");
				int i = 0;
				while(res.next()){
					list[i][0]=res.getString("NombreUsuario");
					list[i][1]=String.valueOf(res.getInt("IdSudoku"));
					list[i][2]=String.valueOf(res.getInt("Puntuación"));
					list[i][3]=String.valueOf(res.getBoolean("Estado"));
					i++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;		
	}
	
	public String[][] obtInfoPremios(String pNom){
		String[][] info = new String[0][0];
		try {
			ResultSet res = GestorBD.getGestorBD().Select("SELECT COUNT(NombreUsuario) FROM ListaPremiados WHERE NombreUsuario='"+pNom+"'");
			res.next();
			info = new String[res.getInt(1)][5];
			res = GestorBD.getGestorBD().Select("SELECT LP.NombrePremio, LP.Fecha, P.Limite, P.IdSudoku, P.Tipo FROM ListaPremiados LP, Premios P WHERE LP.NombreUsuario='"+pNom+"' AND P.NombrePremio=LP.NombrePremio");
			int i = 0;
			while(res.next()){
				info[i][0] = res.getString("NombrePremio");
				info[i][1] = res.getDate("Fecha").toString();
				info[i][2] = String.valueOf(res.getInt("Limite"));
				info[i][3] = String.valueOf(res.getInt("IdSudoku"));
				info[i][4] = res.getString("Tipo");
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return info;
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
