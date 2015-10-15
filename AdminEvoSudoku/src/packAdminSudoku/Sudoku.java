package packAdminSudoku;

public class Sudoku {
	int dificultad;
	int id;
	Casilla[][] matriz;
	
	public Sudoku(int pDificultad, int pId){
		this.dificultad = pDificultad;
		this.id = pId;
		matriz = new Casilla[9][9];
	}
	
	public int getId(){
		return id;
	}
	
	public int getDificultad(){
		return dificultad;
	}
	public void anadirCasilla(int pI, int pJ, char num, char sol){
		matriz[pI][pJ] = new Casilla(num, sol);
	}
	
	public void anadirNumero(int pI, int pJ, char num){
		matriz[pI][pJ].anadirNumero(num);
	}

	public void borrarNumero(int pI, int pJ) {
		matriz[pI][pJ].borrarNumero();	
	}
	
	public char obtenerNum(int pI, int pJ){
		return matriz[pI][pJ].getNumero();
	}
	
	public char[] obtenerListaNotas(int pI,int pJ){
		return matriz[pI][pJ].getListaNotas();		
	}
	
	public boolean obtenerFija(int pI, int pJ){
		return matriz[pI][pJ].obtenerFija();
	}
	
	public boolean[][] comprobarCorrecto(){
		boolean[][] casillasFallo;
		if(!comprobarCompleto()){
			casillasFallo= new boolean[9][9]; //Mapeo de todo
			for(int i =0; i<9; i++){
				for(int j =0; j<9; j++){
					casillasFallo[i][j] = false;
				}
			}
			for(int i= 0; i<9; i++){
				for(int j =0; j<9; j++){
					matriz[i][j].rellenarListaPosibles(comprobarListaPosibles(i, j, matriz));
					if(!matriz[i][j].comprobarCorrecto()){
						casillasFallo[i][j] = true; 
					}
				}
			}
		}else{
			casillasFallo= new boolean[0][0];
		}
		return casillasFallo;
	}
	
	public boolean[][] todosLosNumeros(char pNum){
		boolean[][] casillasNumero;
		casillasNumero = new boolean[9][9];
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(matriz[i][j].getNumero()==pNum){
					casillasNumero[i][j] = true;
				}else{
					casillasNumero[i][j] = false;
				}
			}
		}
		return casillasNumero;
	}
	
	private char[] comprobarListaPosibles(int pI, int pJ, Casilla[][] pCas){
		char[] lista = new char[9];
		for(int i=0; i<9; i++) lista[i] = Integer.toString(i+1).charAt(0);
		for(int i= 0; i<9; i++){
			if(i!=pI){
				if(esta(lista, pCas[i][pJ].getNumero())){
					lista[Character.getNumericValue(pCas[i][pJ].getNumero()-1)] = ' ';
				}
			}
		}
		for(int j =0; j<9; j++){
			if(j!= pJ){
				if(esta(lista, pCas[pI][j].getNumero())){
					lista[Character.getNumericValue(pCas[pI][j].getNumero()-1)] = ' ';
				}	
			}
		}
		int i = 0, j = 0, iMax = 3, jMax = 3;
		if(pI<3){ i = 0; iMax = 3;}
		else if(pI<6 && pI >=3){ i = 3; iMax = 6;}
		else if(pI>=6){ i = 6; iMax = 9;}
		if(pJ<3){ j = 0; jMax = 3;}
		else if(pJ<6 && pJ >=3){ j = 3; jMax = 6;}
		else if(pJ>=6){ j = 6; jMax = 9;}
		while(i<iMax){
			j = jMax-3;
			while(j<jMax){
				if(j!= pJ || i!=pI){
					if(esta(lista, pCas[i][j].getNumero())){
						lista[Character.getNumericValue(pCas[i][j].getNumero()-1)] = ' ';
					}	
				}
				j++;
			}
			i++;
		}
		return lista;
	}
	
	private boolean esta(char[] pLista, char pNumero){
		boolean esta = false;
		if(Character.isDigit(pNumero)){
			for(int i=0; i<pLista.length && !esta; i++){
				if(pLista[i] == pNumero){
					esta = true;
				}
			}
		}
		return esta;
	}
	
	private boolean comprobarCompleto(){
		boolean completo = true;
		for(int i =0; i<9 && completo; i++){
			for(int j =0; j<9 && completo; j++){
				completo = matriz[i][j].comprobarCompleto();
			}
		}
		return completo;
	}
	
	public boolean estaEn(ListaPuntuaciones pLista){
		return pLista.esta(id);
	}	
	
	//Metodo destinado a pruebas
	public void imprimirSudoku(){
		
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				System.out.print(matriz[i][j].getNumero() + " ");
			}
			System.out.println("\n");
		}		
	}
	
	public boolean eliminateValues(){
		Casilla[][] casillasAux = new Casilla[9][9];
		casillasAux = crearCasillasAux();
		boolean modificado = false;
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				matriz[i][j].rellenarListaPosibles(comprobarListaPosibles(i, j, casillasAux));
				if(matriz[i][j].eliminateValues()){
					modificado = true;
				}
			}
		}
		return modificado;
	}
	
	private Casilla[][] crearCasillasAux(){
		Casilla[][] cas = new Casilla [9][9];
		char[] listAux = new char[9];
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(Character.isDigit(matriz[i][j].getNumero())){
					cas[i][j] = new Casilla(matriz[i][j].getNumero(), matriz[i][j].getSolucion());					
				}else{
					cas[i][j] = new Casilla(' ', matriz[i][j].getSolucion());					
					listAux = matriz[i][j].getListaPosibles();
					for(int k =0; k<9; k++){
						if(Character.isDigit(listAux[k])){
							cas[i][j].anadirNumero(listAux[k]);
						}						
					}
				}
				cas[i][j] = matriz[i][j];
			}
		}
		return cas;
	}
}
