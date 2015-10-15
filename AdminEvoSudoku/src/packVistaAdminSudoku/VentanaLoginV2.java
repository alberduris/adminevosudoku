package packVistaAdminSudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import packAdminSudoku.GestorLogin;

public class VentanaLoginV2 extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
		
	
	//Panel superior + Label
	private JPanel panelSuperior;
	private JLabel lblBienvenidos;
	
	
	//Panel login + componentes
	private JPanel panelLogin;
	private JLabel lblNombre;
	private JTextField txtFieldNombre;
	private JLabel lblNivel;
	private JTextField txtFieldNivel;
	

	JProgressBar barra = new JProgressBar(1, 500) ;
	
	//Panel boton + boton
	private JPanel panelBtnLogin;
	
	private JButton btnLogin;
	
	//Dialog
	private JDialog dialogError, dialogCargando;
	
	private static Clip sonido;
	
	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public VentanaLoginV2(Clip pSonido) throws IOException {
		super();
		sonido = pSonido;
		//Opciones JFrame base
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		//Añadir el JPanel base y modificarlo
		
		BorderLayout bordercontenido = new BorderLayout();
		contentPane = new JPanel(bordercontenido);
		getContentPane().add(contentPane);
		contentPane.setBackground(Color.BLACK);
				
		Color col = setColor();
		
		getPanelSuperior(col);
		getLblBienvenidos();
		
		getPanelLogin(col);
		getLblNombre();
		getTxtFieldNombre();
		getLblNivel();
		getTxtFieldNivel();

		getPanelBtnLogin(col);
		getBtnLogin();
		
		pack();
	}
	

	
	private Color setColor(){
		return Color.red;
	}
	
	private JPanel getPanelSuperior(Color pCol) throws IOException{
		if(panelSuperior == null){
			GridBagLayout gridbagsuperior = new GridBagLayout();
			panelSuperior = new JPanel(gridbagsuperior){
				private static final long serialVersionUID = 1L;
				BufferedImage img = ImageIO.read(this.getClass().getResource("backloginsuperior.jpg"));
				public void paintComponent(Graphics g){
				            super.paintComponent(g);
				            g.drawImage(img, 0, 0, 400, 120, this);
				        }
			};
			
			
			
			//panelSuperior.setBackground(pCol);
			panelSuperior.setPreferredSize(new Dimension(400,120));
			
			contentPane.add(panelSuperior,"North");
		}
		return panelSuperior;
	}
	
	private JLabel getLblBienvenidos() {
       
		/*
		String path = "logologin.jpg";  
		URL url = this.getClass().getResource(path);  
		ImageIcon icon = new ImageIcon(url); 
		*/
		if(lblBienvenidos == null){
			lblBienvenidos = new JLabel();
		
			
			lblBienvenidos.setHorizontalAlignment(0);
			//lblBienvenidos.setIcon(icon);
			
			panelSuperior.add(lblBienvenidos);
		}
		return lblBienvenidos;
    }
	
	private JPanel getPanelLogin(Color pCol) throws IOException{
		
		if(panelLogin == null){
			GridBagLayout gl2 = new GridBagLayout();
			panelLogin = new JPanel(gl2){
				private static final long serialVersionUID = 1L;
				BufferedImage img = ImageIO.read(this.getClass().getResource("backlogincentro.jpg"));
				public void paintComponent(Graphics g){
				            super.paintComponent(g);
				            g.drawImage(img, 0, 0, 400, 150, this);
				        }
			};
			
			
			
			//panelLogin.setBackground(pCol);
			panelLogin.setPreferredSize(new Dimension(400,150));

			
			contentPane.add(panelLogin,"Center");
		}
		return panelLogin;
	}
	
	private JLabel getLblNombre(){

		GridBagConstraints cs = new GridBagConstraints(); 
		Font fuente = new Font("Arial",Font.BOLD,16);

		
		if(lblNombre == null){
			lblNombre = new JLabel("Nombre: ");
			lblNombre.setFont(fuente);
			lblNombre.setForeground(Color.WHITE);


			cs.weighty = 0;
			cs.gridy = 0;
			//
			panelLogin.add(lblNombre,cs);
		}
		return lblNombre;
	}
	
	
	
	private JTextField getTxtFieldNombre(){

		GridBagConstraints cs = new GridBagConstraints(); 
		Font fuente = new Font("Arial",Font.ITALIC,16);

		
		if(txtFieldNombre == null){
			txtFieldNombre = new JTextField(10);
			txtFieldNombre.setForeground(Color.BLACK);
			txtFieldNombre.setFont(fuente);
			
			cs.fill = GridBagConstraints.HORIZONTAL;
			
			cs.weighty = 0;
			cs.gridy = 1;
			//
			panelLogin.add(txtFieldNombre,cs);
		}
		return txtFieldNombre;
	}
	
	private JLabel getLblNivel(){

		GridBagConstraints cs = new GridBagConstraints(); 
		Font fuente = new Font("Arial",Font.BOLD,16);

		if(lblNivel == null){
			lblNivel = new JLabel("Nivel [1- 5]:");
			lblNivel.setForeground(Color.WHITE);
			lblNivel.setFont(fuente);
			
			cs.weighty = 0;
			cs.gridy = 2;
			//
			panelLogin.add(lblNivel,cs);
		}
		return lblNivel;
	}
	
	private JTextField getTxtFieldNivel(){

		GridBagConstraints cs = new GridBagConstraints(); 
		Font fuente = new Font("Arial",Font.ITALIC,16);

		if(txtFieldNivel == null){
			txtFieldNivel = new JTextField(10);
			txtFieldNivel.setForeground(Color.BLACK);
			txtFieldNivel.setFont(fuente);
			
			cs.weighty = 0;
			cs.gridy = 3;
			//
			panelLogin.add(txtFieldNivel,cs);
		}
		return txtFieldNivel;
	}
	
	private JPanel getPanelBtnLogin(Color pCol) throws IOException{
		
		
		
		if(panelBtnLogin == null){
			FlowLayout fl3 = new FlowLayout(1);
			fl3.setVgap(12);
			
			panelBtnLogin = new JPanel(fl3){
				private static final long serialVersionUID = 1L;
				BufferedImage img = ImageIO.read(this.getClass().getResource("backlogininferior.jpg"));
				public void paintComponent(Graphics g){
				            super.paintComponent(g);
				            g.drawImage(img, 0, 0, 400, 50, this);
				        }
			};
			
			//panelBtnLogin.setBackground(pCol);
			panelBtnLogin.setPreferredSize(new Dimension(400,50));

			
			contentPane.add(panelBtnLogin,"South");
		}
		return panelBtnLogin;
	}
	
	private JButton getBtnLogin(){
		
		if(btnLogin == null){
			btnLogin = new JButton();
			String path = "botonlogin.png";  
			URL url = this.getClass().getResource(path);  
			ImageIcon icon = new ImageIcon(url); 
			
			String path2 = "botonloginover.png";  
			URL url2 = this.getClass().getResource(path2);  
			ImageIcon icon2 = new ImageIcon(url2); 
			
			String path3 = "botonloginpressed.png";  
			URL url3 = this.getClass().getResource(path3);  
			ImageIcon icon3 = new ImageIcon(url3); 
			
			btnLogin.setRolloverIcon(icon2);
			btnLogin.setPressedIcon(icon3);
			
			btnLogin.setPreferredSize(new Dimension(70,25));
			
			btnLogin.setIcon(icon);
			btnLogin.setBorderPainted(false);
			
			panelBtnLogin.add(btnLogin);

			
			ActionListener al1 = new ActionListener(){
				
				@Override
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
		dialogError.getContentPane().setBackground(new Color(0xFFFFFF));
	

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
		dialogError.setTitle("Nivel incorrecto");
		
		dialogError.setLayout(new GridBagLayout());
		dialogError.getContentPane().setBackground(new Color(0xFFFFFF));
	

		dialogError.add(texto,csTexto);
		dialogError.add(boton,csBoton);
		
		
	}
	
	private void getDialogNumberFormatException(){
		
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel texto = new JLabel("<html><body><p align=center>No has introducido un numero<br>El nivel debe ser un número del 1 al 5</p></body></html>");
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
		dialogError.setTitle("Formato de entrada incorrecto");
		
		dialogError.setLayout(new GridBagLayout());
		dialogError.getContentPane().setBackground(new Color(0xFFFFFF));
	

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
		dialogError.setTitle("Datos incorrectos");
		
		dialogError.setLayout(new GridBagLayout());
		dialogError.getContentPane().setBackground(new Color(0xFFFFFF));
	

		dialogError.add(texto,csTexto);
		dialogError.add(boton,csBoton);
		
		
	}
	
	private void getDialogCargando(){
		
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBarra = new GridBagConstraints();

		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBarra.weighty = 1;
		csBarra.gridx = 0;
		csBarra.gridy = 1;

		JLabel lblCargando = new JLabel("Cargando Sudoku");
		dialogCargando = new JDialog();

		dialogCargando.setSize(300,125);
		dialogCargando.setModal(false);
		dialogCargando.setVisible(true);
		dialogCargando.setLocationRelativeTo(contentPane);
		dialogCargando.setTitle("Cargando");
		
		dialogCargando.setLayout(new GridBagLayout());
		dialogCargando.getContentPane().setBackground(new Color(0xFFFFFF));
	
		dialogCargando.add(lblCargando,csTexto);
		barra.setStringPainted(true);
		barra.setValue(1);
		dialogCargando.add(barra, csBarra);
	}
	
	private void pulsarBoton(){
		boolean excepcion = false;
		int nivel = 0;
		String nombre = txtFieldNombre.getText();
		String stringNivel = txtFieldNivel.getText();
		
		try{
			nivel = Integer.parseInt(stringNivel);
		}
		catch(Exception NumberFormatException){
			getDialogNumberFormatException();
			excepcion = true;
		}
		if((nombre.trim().isEmpty()) && (nivel < 1 || nivel > 5)){
			getDialogErrorAmbos();
		}
		else if(nombre.trim().isEmpty()){
			getDialogErrorNombre();
		}
		else if((nivel < 1 || nivel > 5) && !excepcion){
			getDialogErrorNivel();
		}
		else if(!excepcion){
			getDialogCargando();
			Runnable r = new GestorLogin(nombre, nivel, this, dialogCargando);
			Thread proceso = new Thread(r);
			proceso.start();
			try{
				sonido.close();
				
				
			}catch(Exception e){
				
			}
			dispose();
		}
	}
	
	public void actualizarBarra(){
		barra.setValue(barra.getValue()+1);
		if(barra.getValue() == 500){
			dialogCargando.dispose();
		}
	}
	
	public void setBarra(int pProceso){
		barra.setValue(pProceso);
	}
	
	

}
