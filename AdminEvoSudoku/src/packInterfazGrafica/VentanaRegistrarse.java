package packInterfazGrafica;

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
import packModelo.Sesion;

public class VentanaRegistrarse extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
		
	
	//Panel superior + Label
	private JPanel panelSuperior;
	private JLabel lblBienvenidos;
	
	
	//Panel login + componentes
	private JPanel panelLogin;
	private JLabel lblNombre;
	private JTextField txtFieldNombre;
	private JLabel lblCorreo;
	private JTextField txtFieldCorreo;
	private JLabel lblContr;
	private JTextField txtFieldContr;
	

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
	public VentanaRegistrarse() throws IOException {
		super();
		//Opciones JFrame base
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		//AÃ±adir el JPanel base y modificarlo
		
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
		getLblCorreo();
		getTxtFieldCorreo();
		getLblContr();
		getTxtFieldContr();

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
				public void paintComponent(Graphics g){
				            super.paintComponent(g);
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

				public void paintComponent(Graphics g){
				            super.paintComponent(g);
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
	
	private JLabel getLblCorreo(){

		GridBagConstraints cs = new GridBagConstraints(); 
		Font fuente = new Font("Arial",Font.BOLD,16);

		if(lblCorreo == null){
			lblCorreo = new JLabel("Correo");
			lblCorreo.setForeground(Color.WHITE);
			lblCorreo.setFont(fuente);
			
			cs.weighty = 0;
			cs.gridy = 2;
			//
			panelLogin.add(lblCorreo,cs);
		}
		return lblCorreo;
	}
	
	private JTextField getTxtFieldCorreo(){

		GridBagConstraints cs = new GridBagConstraints(); 
		Font fuente = new Font("Arial",Font.ITALIC,16);

		if(txtFieldCorreo == null){
			txtFieldCorreo = new JTextField(10);
			txtFieldCorreo.setForeground(Color.BLACK);
			txtFieldCorreo.setFont(fuente);
			
			cs.weighty = 0;
			cs.gridy = 3;
			//
			panelLogin.add(txtFieldCorreo,cs);
		}
		return txtFieldCorreo;
	}
	
	private JLabel getLblContr(){

		GridBagConstraints cs = new GridBagConstraints(); 
		Font fuente = new Font("Arial",Font.BOLD,16);

		if(lblContr == null){
			lblContr = new JLabel("Contraseña");
			lblContr.setForeground(Color.WHITE);
			lblContr.setFont(fuente);
			
			cs.weighty = 0;
			cs.gridy = 2;
			//
			panelLogin.add(lblContr,cs);
		}
		return lblContr;
	}
	
	private JTextField getTxtFieldContr(){

		GridBagConstraints cs = new GridBagConstraints(); 
		Font fuente = new Font("Arial",Font.ITALIC,16);

		if(txtFieldContr == null){
			txtFieldContr = new JTextField(10);
			txtFieldContr.setForeground(Color.BLACK);
			txtFieldContr.setFont(fuente);
			
			cs.weighty = 0;
			cs.gridy = 3;
			//
			panelLogin.add(txtFieldContr,cs);
		}
		return txtFieldContr;
	}
	
	private JPanel getPanelBtnLogin(Color pCol) throws IOException{
		
		
		
		if(panelBtnLogin == null){
			FlowLayout fl3 = new FlowLayout(1);
			fl3.setVgap(12);
			
			panelBtnLogin = new JPanel(fl3){
				private static final long serialVersionUID = 1L;

				public void paintComponent(Graphics g){
				            super.paintComponent(g);
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
		String nombre = txtFieldNombre.getText();
		String correo = txtFieldCorreo.getText();
		String contrasena = txtFieldContr.getText();
		boolean[] error = Sesion.obtSesion().registrarse(nombre, correo, contrasena);
		if(!error[0] && !error[1]){
			System.out.println("Nombre y Correo Existente");
		}else if(!error[0]){
			System.out.println("Nombre Existente");
		}else if(!error[1]){
			System.out.println("Correo Existente");
		}
		dispose();
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
	
	public static void main(String arg[]){
		try {
			VentanaRegistrarse vnt = new VentanaRegistrarse();
			vnt.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
