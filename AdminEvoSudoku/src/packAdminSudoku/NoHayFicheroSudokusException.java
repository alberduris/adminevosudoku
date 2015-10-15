package packAdminSudoku;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NoHayFicheroSudokusException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JDialog dialogError;

	public NoHayFicheroSudokusException(){
		super();
	}
	
	public boolean lanzarExcepcion(){
		
		getDialogNoHayFicheroSudokus();
		
		
		return false;
	}
	
private void getDialogNoHayFicheroSudokus(){
		
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel texto = new JLabel("<html><body><p align=center>Parece que hay un error con el servidor.<br>Por favor, intentelo mas tarde.</p></body></html>");
		JButton boton = new JButton("Cerrar");
		
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialogError.dispose();
			}

		
			
		});
		
		dialogError = new JDialog();
		dialogError.setSize(300,125);
		dialogError.setModal(false);
		dialogError.setVisible(true);
		dialogError.setLocationRelativeTo(contentPane);
		dialogError.setTitle("Recursos no disponibles");
		
		
		dialogError.setLayout(new GridBagLayout());
		dialogError.getContentPane().setBackground(new Color(0xFFFFFF));
	

		dialogError.add(texto,csTexto);
		dialogError.add(boton,csBoton);
		
		
	}

}
