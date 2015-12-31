package packModelo;

import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;

import packModelo.CatalogoSudoku;
import packAdminSudoku.NoHayFicheroSudokusException;
import packModelo.Sudoku;
import packModelo.CogerSudokus;
import packBD.GestorBD;

public class CatalogoSudoku {
	private ListaSudokus lista;
	private static CatalogoSudoku miListaSudoku;
	
	private CatalogoSudoku(){
		lista = new ListaSudokus();
		leerBD();
	}
	
	public static CatalogoSudoku getCatalogoSudoku(){
		if(miListaSudoku == null){
			miListaSudoku = new CatalogoSudoku();
		}
		return miListaSudoku;
	}
	
	public Iterator<Sudoku> obtIteradorSudokus(int pNivel){
		return lista.seleccionarSudoku(pNivel);
	}
	
	public void anadirSudoku(Sudoku sud){
		lista.anadirSudoku(sud);
	}
	
	public boolean leerBD(){
		boolean lanzar = true;
		GestorBD gBD = GestorBD.getGestorBD();
		ResultSet res = gBD.Select("SELECT Identificador, Nivel, Activo FROM Sudokus");
		try {
			if(!res.next()){
				try {
					CogerSudokus.cogerSudoku();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				if(lista.tamano() ==0){
					Sudoku sudo;					
					if(lanzar){
						try {
							sudo = new Sudoku(res.getInt("Identificador"), res.getInt("Nivel"));
							if(res.getBoolean("Activo")){
								sudo.setActivado(true);	
							}else{
								sudo.setActivado(false);
							}
							anadirSudoku(sudo);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			Sudoku sudo;
			while(res.next()){
				sudo = new Sudoku(res.getInt("Identificador"), res.getInt("Nivel"));
				sudo.setActivado(res.getBoolean("Activo"));
				anadirSudoku(sudo);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		return lanzar;
	}

	
	public static void main(String[] arg){
		CatalogoSudoku gS = CatalogoSudoku.getCatalogoSudoku();
		gS.imprimir();
		String[] lis = gS.obtListaIdent();
		/*for(int i = 0; i<lis.length; i++){
			System.out.print(lis[i] + ", ");
			if(i == 999 | i == 1999 | i == 2999 | i == 3999 | i == 4999){
				System.out.println("");
				
			}
		}*/
	}
	
	public boolean leerFichero(String fich){
		boolean descargadoConExito = true;
		boolean lanzar = true;
		if(lista.tamano() ==0){
			char[] numero, solucion;
			Sudoku sudo;
			
			int idSudoku = 1;
			int dificultad = 1;
			int j;		
			int contador = 9;
			try{
			File fichero = new File(fich);
			if(!fichero.exists()){
				try{
					descargadoConExito = CogerSudokus.cogerSudoku();
					
					if(!descargadoConExito){
						NoHayFicheroSudokusException ex = new NoHayFicheroSudokusException();
						throw ex;
					}
				}
				catch(NoHayFicheroSudokusException ex){
					lanzar = ex.lanzarExcepcion();
				}
				
			}
			
			if(lanzar){
				Scanner entradaNumero = new Scanner(new FileReader(fich));
				Scanner entradaSolucion = new Scanner(new FileReader(fich));
				
				for(int i=0; i<10; i++){
					entradaSolucion.nextLine();
				}
				 while (entradaSolucion.hasNext() && contador >=0) {
					 sudo = new Sudoku(idSudoku, dificultad);		
					 j = 0;
					 while(j< 9){
						 numero = getCharDeLinea(entradaNumero, contador);
						 solucion = getCharDeLinea(entradaSolucion, 20);
						 contador --;
						 while(!Character.isDigit(numero[0])){
							 numero = getCharDeLinea(entradaNumero, contador);					 
						 }
						 while(!Character.isDigit(solucion[0])){
							 solucion = getCharDeLinea(entradaSolucion, 20);
							 contador--;
						 }
						 if(comprobarLineaEnBlanco(numero, solucion)){
							 for(int i=0; i<9; i++){
								sudo.asgValor(j, i,solucion[i]);
							 }
							 j++;
							 solucion = getCharDeLinea(entradaSolucion, 20);
							 while(!Character.isDigit(solucion[0])){
								 solucion = getCharDeLinea(entradaSolucion, 20);
								 contador --;
							 }
						 }
						 for(int i=0; i<9; i++){
							 if(estaEnNumero(solucion[i], numero) != -1){
								 sudo.asgValorInicial(j, i,solucion[i] ); 
							 }else{
								 sudo.asgValor(j, i,solucion[i]);
							 }
						 }
						 j++;
					 }
					 anadirSudoku(sudo);
					dificultad = configurarDificultad(idSudoku);
					contador = saltar(entradaNumero);
					contador = saltar(entradaSolucion);
					idSudoku++;
				}
				 entradaNumero.close();
				 entradaSolucion.close();		
				}
			}
				 catch(IOException e) {e.printStackTrace();}
			}
		return lanzar;
	}

	private int configurarDificultad(int idSudoku){
		int dificultad = 1;
		if(idSudoku >= 1000 && idSudoku < 2000){
			 dificultad = 2;
		 }else if(idSudoku >= 2000 && idSudoku < 3000){
			 dificultad = 3;
		 }else if(idSudoku >= 3000 && idSudoku < 4000){
			 dificultad = 4;
		 }else if(idSudoku >= 4000 && idSudoku < 5000){
			 dificultad = 5;
		 }
		return dificultad;
	}
	private boolean comprobarLineaEnBlanco(char[] pNumero, char[] pSolucion){
		boolean lineaBlanco = false;
		if(tamanoReal(pNumero)>1){
			for(int i= 0; i<tamanoReal(pNumero)-1 &&!lineaBlanco; i++){
				if(posicionNumero(pNumero[i], pSolucion) > posicionNumero(pNumero[i+1], pSolucion)){
					lineaBlanco = true;
				}
			}
		}
		return lineaBlanco;
	}
	
	private int posicionNumero(char numero, char[] pLista){
		boolean encontrado = false;
		int i = 0;
		while(i< tamanoReal(pLista) && !encontrado){
			if(numero == pLista[i]){
				encontrado = true;
			}else{
				i++;
			}
		}
		return i;
	}
	
	private int saltar(Scanner pArchivo){
		String linea;
		int contador = 0;
		boolean fin = false;
		if(pArchivo.hasNext()){
			linea = pArchivo.nextLine();
			if(!Character.isDigit(linea.charAt(0))){
				if(pArchivo.hasNext()){
					linea = pArchivo.nextLine();
					if(!Character.isDigit(linea.charAt(0))){
						if(pArchivo.hasNext()){
							linea = pArchivo.nextLine();
						}else{
							fin = true;
							contador = -100;
						}
					}else{
						contador++;
					}
				}else{
					fin = true;
					contador = -100;
				}
			}else{
				contador++;
			}
			while(Character.isDigit(linea.charAt(0))&& !fin){
				if(pArchivo.hasNext()){
					linea = pArchivo.nextLine();
					contador++;
				}else{
					fin = true;
					contador = -100;
				}
			}
		}
		return contador;
	}
	
	private char[] getCharDeLinea(Scanner pScanner, int pContador){
		String linea;
		char[] result = new char[9];
		int i = 0, j=0;
		if(pContador != 0){
			if(pScanner.hasNext()){
				linea = pScanner.nextLine();
				while( i < linea.length()){
					try{
						result[j] = linea.charAt(i);
					}catch(Exception e){
						return result;
					}
					j++;
					i +=2;
				}
			}
		}else{
			result[0] = '0';
		}
		return result;		
	}
	
	private int tamanoReal(char[] pList){
		int tamano = 0;
		while(pList[tamano]!= '\0' && tamano<pList.length-1){
			tamano++;
		}
		return tamano;
	}
	
	private int estaEnNumero(char pSol, char[] listNum){
		boolean esta = false;
		int posicion = -1;
		for(int i = 0; i< tamanoReal(listNum) && !esta; i++){
			if(pSol == listNum[i]){
				esta=true;
				posicion = i;
			}
		}
		return posicion;
	}	
	
	//Método para las JUnits
	public void resetear(){
		lista.resetear();
	}
	
	public int buscarDificultadPorId(int pIdSudoku){
		return lista.buscarDificultadPorId(pIdSudoku);
	}
	
	public int buscarPrimerIdDisp(){
		return lista.buscarPrimerIdDisp();
	}
	
	public int getTamano(){
		return lista.getTamano();
	}
	
	public String[] obtListaIdent(int pNivel){
		return lista.obtListaIdent(pNivel);
	}
	
	public String[] obtListaIdent(){
		return lista.obtListaIdent();
	}
	
	public void eliminarSudoku(int pId){
		lista.eliminarSudoku(pId);	
	}
	
	public String[] obtListaEstado(boolean pEstado){
		return lista.obtListaEstado(pEstado);
	}
	
	public String[] obtListaEstado(int pNivel, boolean pEstado){
		return lista.obtListaEstado(pNivel, pEstado);
	}
	
	public void modificarEstadoSudoku(int pIdSudoku, boolean pEstado){
		lista.modificarEstadoSudoku(pIdSudoku, pEstado);
	}
	
	public void imprimir(){
		lista.imprimir();
	}
}
