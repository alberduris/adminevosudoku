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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import packModelo.FiltroTexto;
import packModelo.Sesion;


public class VentanaRegistro extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	
	private JLabel lblEmail;
	private JTextField txtEmail;
	
	
	private JLabel lblUsuario;
	private JTextField txtUsuario;
	
	private JLabel lblPass;
	private JPasswordField txtPass;
	
	private JButton btnRegistro;
	
	private JButton btnAtras;
	
	private Dimension dimVentana = new Dimension(250, 375);
	private Dimension dimAreaTexto = new Dimension(200, 25);
	private Dimension dimBoton = new Dimension(150,30);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaRegistro();
	}

	/**
	 * Create the frame.
	 */
	public VentanaRegistro() {
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

		getTituloRegistro();
		
		getLblEmail();
		getTxtEmail();
		
		getLblUsuario();
		getTxtUsuario();
		
		getLblPass();
		getTxtPass();
		
		getBtnRegistro();
		
		getBtnAtras();
		
		setVisible(true);
	}

	private void getTituloRegistro() {
	
		lblTitulo = new JLabel("Registro - Beta");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}

	private void getLblEmail() {
		lblEmail = new JLabel("Email");
		lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(lblEmail);
	}

	private void getTxtEmail(){
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Serif", Font.ITALIC, 16));
		txtEmail.setPreferredSize(dimAreaTexto);
		
		
		panelConBoxLayout.add(txtEmail);
	}

	private void getLblUsuario() {
		lblUsuario = new JLabel("Usuario");
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
	
	private void getLblPass() {
		lblPass = new JLabel("Contrase�a");
		lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(lblPass);
	}

	private void getTxtPass(){
		
		txtPass = new JPasswordField(10);
		txtPass.setPreferredSize(dimAreaTexto);
	
		
		panelConBoxLayout.add(txtPass);
		
		
	}
	
	private void getBtnRegistro(){
		btnRegistro = new JButton("Registro");
		btnRegistro.setAlignmentX(CENTER_ALIGNMENT);
		btnRegistro.setMinimumSize(dimBoton);
		btnRegistro.setPreferredSize(dimBoton);
		btnRegistro.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnRegistro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!FiltroTexto.esEmail(txtEmail.getText())){//Si no es un mail valido
					JOptionPane.showMessageDialog(contentPane, "No es un mail valido");
				}
				else if(!FiltroTexto.caracterAdmitido(txtUsuario.getText())){//Si el usuario tiene carac no admitidos
					JOptionPane.showMessageDialog(contentPane, "El usuario tiene car�cteres no v�lidos");
				}
				else if(txtPass.getPassword().length <= 4){
					JOptionPane.showMessageDialog(contentPane, "La contrase�a es demasiado corta");
				}
				else{
					
					JOptionPane.showMessageDialog(contentPane, "Registro realizado con �xito");
					Sesion.obtSesion().registrarse(txtUsuario.getText(), txtEmail.getText(), FiltroTexto.getContrase�a(txtPass.getPassword()));
				}
			}
		});
		
		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0,25)));
		panelConBoxLayout.add(btnRegistro);
	}
	
	private void getBtnAtras() {
		btnAtras = new JButton("<");
		btnAtras.setEnabled(true);
		btnAtras.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaStart();
				dispose();
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(leftJustify(btnAtras));
		
	}
	
	private Component leftJustify( JButton btn )  {
	    Box  b = Box.createHorizontalBox();
	    b.add( btn );
	    b.add( Box.createHorizontalGlue() );
	  
	    return b;
	}
	
	
	
	
	
	
	
	
	
	
	

}