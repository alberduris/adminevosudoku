package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import packModelo.Sesion;


public class VentanaRecuperacion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	
	private JLabel lblUsuario;
	private JTextField txtUsuario;
	
	private JButton btnRecuperar;
	
	private JButton btnAtras;
	
	private Dimension dimVentana = new Dimension(350, 265);
	private Dimension dimAreaTexto = new Dimension(200, 25);
	private Dimension dimBoton = new Dimension(150,30);

	/**
	 * Create the frame.
	 */
	public VentanaRecuperacion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(dimVentana);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		setContentPane(contentPane);
		panelConBorderLayout = new JPanel();
		panelConBoxLayout = new JPanel();

		BorderLayout border = new BorderLayout();
		panelConBorderLayout.setLayout(border);
		BoxLayout box = new BoxLayout(panelConBoxLayout, BoxLayout.Y_AXIS);
		panelConBoxLayout.setLayout(box);

		contentPane.add(panelConBorderLayout);
		panelConBorderLayout.add(panelConBoxLayout, BorderLayout.CENTER);

		getTituloRecuperar();
		
		getLblUsuario();
		getTxtUsuario();
		
		getBtnRecuperar();
	
		getBtnAtras();
		
		setVisible(true);
	}

	private void getTituloRecuperar() {
	
		lblTitulo = new JLabel("Recuperar contraseña");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}


	private void getLblUsuario() {
		lblUsuario = new JLabel("Usuario o email");
		lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(lblUsuario);
	}

	private void getTxtUsuario(){
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Serif", Font.ITALIC, 16));
		txtUsuario.setPreferredSize(dimAreaTexto);
		
		
		panelConBoxLayout.add(txtUsuario);
	}
	
	
	private void getBtnRecuperar(){
		btnRecuperar = new JButton("Recuperar");
		btnRecuperar.setAlignmentX(CENTER_ALIGNMENT);
		btnRecuperar.setMinimumSize(dimBoton);
		btnRecuperar.setPreferredSize(dimBoton);
		btnRecuperar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		btnRecuperar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Sesion.obtSesion().recuperarCotrasena(txtUsuario.getText())){
					crearDialogCorrecto();
				}else{
					crearDialogoNoExisten();
				}
			}
		});
		
		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0,25)));
		panelConBoxLayout.add(btnRecuperar);
	}
	
	private void getBtnAtras() {
		btnAtras = new JButton("<");
		btnAtras.setEnabled(true);
		btnAtras.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaLogin();				
				dispose();
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(leftJustify(btnAtras));
		
	}
	
	
	private Component leftJustify(JButton btn)  {
	    Box  b = Box.createHorizontalBox();
	    b.add(btn);
	    b.add( Box.createHorizontalGlue() );
	  
	    return b;
	}
	
	private void crearDialogoNoExisten(){
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		final JDialog dialogExiste = new JDialog();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel txt = new JLabel("No existe ningun usuario con ese nombre o correo");
		JButton boton = new JButton("Continuar");
		
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialogExiste.dispose();
			}
			
		});
		
		dialogExiste.setSize(450,125);
		dialogExiste.setAlwaysOnTop(true);
		dialogExiste.setModal(false);
		dialogExiste.setVisible(true);
		dialogExiste.setLocationRelativeTo(this);
		dialogExiste.setTitle("ERROR");
		
		dialogExiste.setLayout(new GridBagLayout());
	
		dialogExiste.add(txt,csTexto);
		csTexto.gridy = 1;
		dialogExiste.add(boton,csBoton);
	}	
	
	private void crearDialogCorrecto(){
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		final JDialog dialogExiste = new JDialog();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel txt = new JLabel("Se le ha enviado un correo con la nueva contraseña");
		JButton boton = new JButton("Continuar");
		
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new VentanaLogin();		
				dialogExiste.dispose();
				dispose();		
			}
			
		});
		
		dialogExiste.setSize(450,125);
		dialogExiste.setAlwaysOnTop(true);
		dialogExiste.setModal(false);
		dialogExiste.setVisible(true);
		dialogExiste.setLocationRelativeTo(this);
		dialogExiste.setTitle("CORRECTO");
		
		dialogExiste.setLayout(new GridBagLayout());
	
		dialogExiste.add(txt,csTexto);
		csTexto.gridy = 1;
		dialogExiste.add(boton,csBoton);
	}	

}