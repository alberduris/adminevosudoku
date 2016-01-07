package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class VentanaOpciones extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JButton btnCambiarNombreUsuario;
	private JButton btnCambiarEmail;
	private JButton btnCambiarPassword;
	
	private JButton btnAtras;
	
	

	private Dimension dimBtn = new Dimension(200, 30);
	private Dimension dimVentana = new Dimension(250, 300);

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaOpciones();
	}

	/**
	 * Create the frame.
	 */
	public VentanaOpciones() {
		
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

		getTituloMenuPpalUsuario();
		

		getbtnCambiarNombreUsuario();
		getbtnCambiarEmail();
		getbtnCambiarPassword();
		getBtnAtras();
		setVisible(true);

	}

	private void getTituloMenuPpalUsuario() {
	
		lblTitulo = new JLabel("Opciones");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}

	
	

	

	private void getbtnCambiarNombreUsuario() {
		btnCambiarNombreUsuario = new JButton("Cambiar nombre usuario");
		btnCambiarNombreUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCambiarNombreUsuario.setMinimumSize(dimBtn);
		btnCambiarNombreUsuario.setPreferredSize(dimBtn);
		btnCambiarNombreUsuario.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnCambiarNombreUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCambiarNombreUsuario();
				dispose();
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnCambiarNombreUsuario);
	}

	private void getbtnCambiarEmail() {
		btnCambiarEmail = new JButton("Cambiar email");
		btnCambiarEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCambiarEmail.setMinimumSize(dimBtn);
		btnCambiarEmail.setPreferredSize(dimBtn);
		btnCambiarEmail.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnCambiarEmail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new VentanaCambiarEmail();				
				dispose();	
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnCambiarEmail);
	}

	
	private void getbtnCambiarPassword() {
		btnCambiarPassword = new JButton("Cambiar contraseña");
		btnCambiarPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCambiarPassword.setMinimumSize(dimBtn);
		btnCambiarPassword.setPreferredSize(dimBtn);
		btnCambiarPassword.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnCambiarPassword.setEnabled(true);
		
		btnCambiarPassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCambiarPassword();				
				dispose();
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnCambiarPassword);
	}
	
	private void getBtnAtras() {
		btnAtras = new JButton("<");
		btnAtras.setEnabled(true);
		btnAtras.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaMenuPpalUsuario();				
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
	
	
	

}