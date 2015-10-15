package packVistaAdminSudoku;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import packAdminSudoku.GestorLogin;

public class VentanaLogin extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
		
	private JLabel lblBienvenidos;
	
	private JLabel lblNombre;
	private JTextField txtFieldNombre;
	
	private JLabel lblNivel;
	private JTextField txtFieldNivel;
	
	private JButton btnLogin;
	
	private JDialog dialogError;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin frame = new VentanaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public VentanaLogin() throws IOException {
		super();
		
		//Colores y fuentes
		
		Color myBrown = new Color(0x99004C);
		
		
	
		//Opciones JFrame base
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,250);
		setLocationRelativeTo(null);
		
		
		
		//Añadir el JPanel base y modificarlo
		contentPane = new JPanel(new GridBagLayout());
		getContentPane().add(contentPane);
		contentPane.setBackground(myBrown);

		
		
		//Añadir resto elementos
		getLblBienvenidos();
		
		getLblNombre();
		
		getTxtFieldNombre();
	
		getLblNivel();
		
		getTxtFieldNivel();
		
		getBtnLogin();
		
	
	}
	
	private JTextField getTxtFieldNivel(){
		GridBagConstraints cs4 = new GridBagConstraints();
		cs4.fill = GridBagConstraints.HORIZONTAL;
		
		if(txtFieldNivel == null){
			txtFieldNivel = new JTextField(1);
			cs4.gridx = 1;
			cs4.gridy = 3;
			cs4.gridwidth = 2;
			contentPane.add(txtFieldNivel,cs4);
		}
		return txtFieldNivel;
	}
	
	private JLabel getLblNivel(){
		GridBagConstraints cs3 = new GridBagConstraints();

		if(lblNivel == null){
			lblNivel = new JLabel("Nivel: ");
			lblNivel.setForeground(Color.WHITE);
			cs3.gridx = 0;
			cs3.gridy = 3;
			cs3.gridwidth = 1;
			cs3.anchor = GridBagConstraints.CENTER;
			contentPane.add(lblNivel,cs3);
		}
		return lblNivel;
	}
	
	private JTextField getTxtFieldNombre(){
		GridBagConstraints cs2 = new GridBagConstraints();
		cs2.fill = GridBagConstraints.HORIZONTAL;
		
		if(txtFieldNombre == null){
			txtFieldNombre = new JTextField(20);
			cs2.gridx = 1;
			cs2.gridy = 1;
			cs2.gridwidth = 2;
			contentPane.add(txtFieldNombre,cs2);
		}
		return txtFieldNombre;
		
		
	}
	
	private JLabel getLblNombre(){
		GridBagConstraints cs = new GridBagConstraints();

		if(lblNombre == null){
			lblNombre = new JLabel("Nombre: ");
			lblNombre.setForeground(Color.WHITE);
			cs.gridx = 0;
			cs.gridy = 1;
			cs.gridwidth = 1;
			cs.anchor = GridBagConstraints.CENTER;
			contentPane.add(lblNombre,cs);
		}
		return lblNombre;
	}
	
	private JLabel getLblBienvenidos(){
		Font fuente = new Font("Arial",Font.BOLD,16);
		GridBagConstraints cs = new GridBagConstraints();

		if(lblBienvenidos == null){
			lblBienvenidos = new JLabel("Bienvenido");
			lblBienvenidos.setFont(fuente);
			lblBienvenidos.setForeground(Color.WHITE);
			
			cs.gridx = 0;
			cs.gridy = 0;
			cs.gridwidth = 3;
		
			
			contentPane.add(lblBienvenidos,cs);
		}
		return lblBienvenidos;
	}
	
	private JButton getBtnLogin(){
		
		
		
		if(btnLogin == null){
			btnLogin = new JButton("Login");
			
			GridBagConstraints cs5 = new GridBagConstraints();
			cs5.fill = GridBagConstraints.HORIZONTAL;
			
			cs5.gridx = 1;
			cs5.gridy = 5;
			cs5.gridwidth = 2;
			cs5.anchor = GridBagConstraints.CENTER;
			contentPane.add(btnLogin,cs5);
			ActionListener al1 = new ActionListener(){
				public void actionPerformed(ActionEvent arg0){	
					pulsarBoton();
				}
			};
			btnLogin.addActionListener(al1);
		
		}
		
		return btnLogin;
		
	}
	
	private void getDialogErrorNombre(){
		
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel texto = new JLabel("Has introducido un nombre incorrecto");
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
		dialogError.setTitle("Nombre incorrecto");
		
		
		dialogError.setLayout(new GridBagLayout());
		dialogError.getContentPane().setBackground(new Color(0x2E9AFE));
	

		dialogError.add(texto,csTexto);
		dialogError.add(boton,csBoton);
		
		
	}
		
	private void getDialogErrorNivel(){
		
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel texto = new JLabel("<html><body><p align=center>Has introducido un nivel incorrecto<br>Introduce un numero del 1 al 5</p></body></html>");
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
		dialogError.setTitle("Nombre incorrecto");
		
		dialogError.setLayout(new GridBagLayout());
		dialogError.getContentPane().setBackground(new Color(0x2E9AFE));
	

		dialogError.add(texto,csTexto);
		dialogError.add(boton,csBoton);
		
		
	}	

	private void getDialogErrorAmbos(){
		
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel texto = new JLabel("Has introducido el nombre y el nivel mal.");
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
		dialogError.setTitle("Nombre incorrecto");
		
		dialogError.setLayout(new GridBagLayout());
		dialogError.getContentPane().setBackground(new Color(0x2E9AFE));
	

		dialogError.add(texto,csTexto);
		dialogError.add(boton,csBoton);
		
		
	}

	private void pulsarBoton(){
		int nivel = 0;
		String nombre = txtFieldNombre.getText();
		String stringNivel = txtFieldNivel.getText();
		
		try{
			nivel = Integer.parseInt(stringNivel);
		}
		catch(Exception e){
			//ATOPEEE CON LAS EXCEPCIONES!!!
		}
		if((nombre.trim().isEmpty()) && (nivel < 1 || nivel > 5)){
			getDialogErrorAmbos();
		}
		else if(nombre.trim().isEmpty()){
			getDialogErrorNombre();
		}
		else if(nivel < 1 || nivel > 5){
			getDialogErrorNivel();
		}
		else{
			//GestorLogin.getGestorLogin().gestionarJugador(nombre,nivel);
			try{
			VentanaSudoku vnt = new VentanaSudoku();
			vnt.setVisible(true);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			dispose();
		}
	}
}
