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
import packBD.GestorMultiBD;

public class CatalogoSudoku {
	private ListaSudokus lista;
	private static CatalogoSudoku miListaSudoku;
	
	private CatalogoSudoku(){
		lista = new ListaSudokus();
		//leerBD();
		leerFichero(getClass().getResource("sudokus.save").getPath());
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
	
	public void leerBD(){
		final GestorBD gBD = GestorBD.getGestorBD();
		ResultSet res = gBD.Select("SELECT COUNT(*) FROM Sudokus");

		if(lista.tamano() ==0){
			try {
				res.next();
				int tamano = res.getInt(1);getClass();
				int cantRes = tamano/100;
				int resto = tamano%100;
				if(resto != 0){
					cantRes++;
				}
				GestorMultiBD.getGestorMultiBD().setTamano(cantRes);
				class Hilo implements Runnable{
					ResultSet res;
					String sentencia;
					
					public Hilo(String pSentencia){
						sentencia = pSentencia;
					}
					
					public ResultSet obtResultado(){
						return res;
					}
					
					
					public void run(){
						try{
							System.out.println(sentencia);
							res = GestorMultiBD.getGestorMultiBD().Select(sentencia);
						} catch (PropertyVetoException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
						//	CatalogoJugadores.obtListaJugadores().notify();
						}						
						System.out.println(sentencia);
						System.out.println(obtResultado());
					}
				}
				ResultSet[] result = new ResultSet[cantRes];
				Hilo[] multihilo = new Hilo[cantRes];
				Thread[] mh = new Thread[cantRes];
				synchronized(this){
					for(int i = 0, j = 0; i<tamano+1; i+=100, j++){
						if(j+1 == cantRes && resto != 0){

							multihilo[j] = new Hilo("SELECT Sudoku FROM Sudokus LIMIT "+i+","+resto+"");
						}else{

							multihilo[j] = new Hilo("SELECT Sudoku FROM Sudokus LIMIT "+i+",100");
						}
						
					}
					for(int i = 0; i<cantRes; i++){
						mh[i] = new Thread(multihilo[i]);
						mh[i].start();
					}
					for(int i = 0; i<cantRes; i++){
						while(mh[i].isAlive()){
						}
						System.out.println(i);
					}
				}
				
				System.out.println("ENRA");
				if(tamano==0){
					CogerSudokus.cogerSudoku();
				}
				System.out.println("");
				Sudoku sudo;
				for(int i=0; i<cantRes-1; i++){
					while(result[i].next()){
						byte[] b = result[i].getBytes("Sudoku");
						ByteArrayInputStream byteArray = new ByteArrayInputStream(b);
						ObjectInputStream oos = new ObjectInputStream(byteArray);
						sudo = (Sudoku) oos.readObject();
						anadirSudoku(sudo);
						System.out.println(sudo.obtIdentificador());
					}	
				}	
			} catch (ClassNotFoundException | SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	public static void main(String[] arg){
		String[] lis = CatalogoSudoku.getCatalogoSudoku().obtListaIdent(1);
		for(int i = 0; i< lis.length; i++){
			System.out.print(lis[i] +" , ");
		}
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
	
	public Sudoku buscarSudokuPorId(int pIdSudoku){
		return lista.buscarSudokuPorId(pIdSudoku);
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
	
	public void eliminarSudoku(int pId){
		lista.eliminarSudoku(pId);	
	}
	
}
