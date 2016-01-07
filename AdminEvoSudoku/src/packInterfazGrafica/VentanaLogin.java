package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import packModelo.CatalogoSudoku;
import packModelo.FiltroTexto;
import packModelo.Sesion;


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
	
	private boolean terminar = false;
	
	private Dimension dimVentana = new Dimension(250, 330);
	private Dimension dimAreaTexto = new Dimension(200, 25);
	private Dimension dimBoton = new Dimension(150,30);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaLogin();
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
		
		setVisible(true);
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
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean[] resultIdent = Sesion.obtSesion().identificarse(txtUsuario.getText(), FiltroTexto.getContraseña(txtPass.getPassword()));
				if(!resultIdent[0] || !resultIdent[1]){
					JOptionPane.showMessageDialog(contentPane, "Datos incorrectos");
				}
				else{
					ejecutarBoton();
				}			
			}
		});
		
		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0,25)));
		panelConBoxLayout.add(btnLogin);
	}
	
	private void getLblOlvido(){
		lblOlvido = new JLabel("¿Olvidó su contraseña?");
		lblOlvido.setAlignmentX(CENTER_ALIGNMENT);
		
		lblOlvido.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				new VentanaRecuperacion();
				dispose();
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
				new VentanaStart();				
				dispose();
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(leftJustify(btnAtras));
		
	}
	
	private void ejecutarBoton(){
		new Thread(new Runnable()
		{
			public void run()
			{
				CatalogoSudoku.getCatalogoSudoku();
				terminar = true;
			}
		}).start();
		setVisible(false);
		new Thread(new Runnable()
		{
			public void run()
			{
				obtCargando();
				if(Sesion.obtSesion().obtNombreUsuario().trim().equalsIgnoreCase("Admin")){
					new VentanaMenuPpalAdministrador();
				}else{
					new VentanaMenuPpalUsuario();
				}
			}
		}).start();		
	}
	
	private void obtCargando(){
		JDialog cargando = new  JDialog();
		cargando.setVisible(true);
		cargando.setTitle("Cargando");
		cargando.setLocationRelativeTo(this);
		cargando.setSize(new Dimension(200, 125));
		JLabel texto = new JLabel();
		cargando.add(texto);
		texto.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		texto.setText("Cargando Sudokus...");
		while(!terminar){
			try {
				Thread.sleep(1000);
				texto.setText("Cargando Sudokus.");
				cargando.repaint();
				Thread.sleep(1000);
				texto.setText("Cargando Sudokus..");
				cargando.repaint();
				Thread.sleep(1000);
				texto.setText("Cargando Sudokus...");
				cargando.repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cargando.dispose();
		dispose();
	}
	
	private Component leftJustify(JButton btn)  {
	    Box  b = Box.createHorizontalBox();
	    b.add(btn);
	    b.add( Box.createHorizontalGlue() );
	  
	    return b;
	}
}