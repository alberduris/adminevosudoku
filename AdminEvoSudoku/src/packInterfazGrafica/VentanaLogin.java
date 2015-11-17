package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class VentanaLogin extends JFrame {

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
	
	private JLabel lblPass;
	private JPasswordField txtPass;
	
	private JButton btnLogin;
	
	private JButton btnAtras;
	
	private JLabel lblOlvido;
	
	


	
	private Dimension dimVentana = new Dimension(250, 330);
	private Dimension dimAreaTexto = new Dimension(200, 25);
	private Dimension dimBoton = new Dimension(150,30);

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
	 */
	public VentanaLogin() {
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

		getTituloLogin();
		
		getLblUsuario();
		getTxtUsuario();
		
		getLblPass();
		getTxtPass();
		
		getBtnLogin();
	
		getLblOlvido();
		getBtnAtras();
	}

	private void getTituloLogin() {
	
		lblTitulo = new JLabel("Login - Beta");
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
		lblPass = new JLabel("Contraseña");
		lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(lblPass);
	}

	private void getTxtPass(){
		
		txtPass = new JPasswordField(10);
		txtPass.setPreferredSize(dimAreaTexto);
	
		
		panelConBoxLayout.add(txtPass);
		
		
	}
	
	private void getBtnLogin(){
		btnLogin = new JButton("Login");
		btnLogin.setAlignmentX(CENTER_ALIGNMENT);
		btnLogin.setMinimumSize(dimBoton);
		btnLogin.setPreferredSize(dimBoton);
		btnLogin.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0,25)));
		panelConBoxLayout.add(btnLogin);
	}
	
	private void getLblOlvido(){
		lblOlvido = new JLabel("¿Olvidó su contraseña?");
		lblOlvido.setAlignmentX(CENTER_ALIGNMENT);
		
		lblOlvido.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Label pulsado");	
			}
			@Override
			public void mouseClicked(MouseEvent e) {		
				// TODO NADA
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO NADA	
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO NADA	
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO NADA
				
			}
		});
		
		panelConBoxLayout.add(lblOlvido);
		
		
	}
	
	
	private void getBtnAtras() {
		btnAtras = new JButton("<");
		btnAtras.setEnabled(true);
		btnAtras.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//TODO
					//frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
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