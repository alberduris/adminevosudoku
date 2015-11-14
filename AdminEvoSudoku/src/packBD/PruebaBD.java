package packBD;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import packModelo.Tablero;

public class PruebaBD {

	private Tablero tab;
	
	public PruebaBD() throws SQLException, ClassNotFoundException, IOException{
		GestorBD gBD = GestorBD.getGestorBD();
		ResultSet resultado = gBD.Select("SELECT Tablero FROM Jugadores");
		if(resultado.next()){
			byte[] b = resultado.getBytes("Tablero");
			System.out.println(b);
			ByteArrayInputStream byteArray = new ByteArrayInputStream(b);
	    	ObjectInputStream oos = new ObjectInputStream(byteArray);
	    	tab = (Tablero) oos.readObject();
	    	tab.getMatriz().imprimir();
		}
		//byte[] b = TABLA.getValueAt(0, 0).getBytes;
    	//oos.writeObject(this);
	}
	public static void main(String arg[]) throws SQLException, ClassNotFoundException, IOException{
		 new PruebaBD();
	}
}

