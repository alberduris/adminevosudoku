package packModelo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Observable;

import packBD.GestorBD;

public class GestorAdministrador extends Observable{

	private static GestorAdministrador miGestor;
	private Sudoku sud;
	
	private GestorAdministrador(){
	}
	
	public static GestorAdministrador getGestorAdministrador(){
		if(miGestor == null){
			miGestor = new GestorAdministrador();
		}
		return miGestor;
	}
	
	public boolean[][] crearSudoku(int pDif, int[][] pSud, boolean[][] pFijas){
		System.out.println(pDif);
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
			GestorBD.getGestorBD().updateTablero("INSERT INTO Sudokus values ("+id+","+pDif+",?)",byteArray);
			
		}
		return result;
	}
	
	public void introducirSudoku(int pIdSudoku){
		sud = CatalogoSudoku.getCatalogoSudoku().buscarSudokuPorId(pIdSudoku);
	}
	
	public int[][] obtValoresAModificar(){
		int[][] numero = new int[9][9];
		for(int i = 0; i<9; i++){
			for(int j =0 ; j<9; j++){
				numero[i][j] = sud.obtMatriz().obtValor(i, j);
			}
		}
		return numero;
	}
	
	public boolean[][] obtFijas(){
		boolean[][] fijas = new boolean[9][9];
		for(int i = 0; i<9; i++){
			for(int j =0 ; j<9; j++){
				fijas[i][j] = sud.obtMatriz().obtCasilla(i, j).esInicial();
			}
		}
		return fijas;
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
			GestorBD.getGestorBD().Update("UPDATE Jugadores SET Tablero=? WHERE NombreUsuario='"+nombreUsuario+"'", byteArray);
			
		}
		return result;
	}
	
}
