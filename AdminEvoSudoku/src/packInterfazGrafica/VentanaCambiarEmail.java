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
import javax.swing.JTextField;
import javax.swing.border.Border;

import packModelo.FiltroTexto;
import packModelo.Sesion;


public class VentanaCambiarEmail extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	
	private JLabel lbl;
	private JTextField txt;
	
	private JButton btnAccion;
	
	private JButton btnAtras;

	
	


	
	private Dimension dimVentana = new Dimension(285, 265);
	private Dimension dimAreaTexto = new Dimension(200, 25);
	private Dimension dimBoton = new Dimension(150,30);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaCambiarEmail();
	}

	/**
	 * Create the frame.
	 */
	public VentanaCambiarEmail() {
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

		getTitulo();
		
		getLbl();
		getTxt();
		
		getBtn();
	
		getBtnAtras();
		
		setVisible(true);
	}

	private void getTitulo() {
	
		lblTitulo = new JLabel("Cambiar email");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}


	private void getLbl() {
		lbl = new JLabel("Email");
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(lbl);
	}

	private void getTxt(){
		txt = new JTextField();
		txt.setFont(new Font("Serif", Font.ITALIC, 16));
		txt.setPreferredSize(dimAreaTexto);
	
		
		
		
		panelConBoxLayout.add(txt);
	}
	
	
	private void getBtn(){
		btnAccion = new JButton("Cambiar");
		btnAccion.setAlignmentX(CENTER_ALIGNMENT);
		btnAccion.setMinimumSize(dimBoton);
		btnAccion.setPreferredSize(dimBoton);
		btnAccion.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnAccion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = txt.getText();
				
				if(!FiltroTexto.esEmail(email)){
					JOptionPane.showMessageDialog(contentPane, "No es un mail valido");				}
				else{
					Sesion.obtSesion().actualizarEmail(email);
					JOptionPane.showMessageDialog(contentPane, "Email cambiado con éxito");
					txt.setText("");
				}
				
			}
		});
		
		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0,25)));
		panelConBoxLayout.add(btnAccion);
	}
	
	
	
	
	private void getBtnAtras() {
		btnAtras = new JButton("<");
		btnAtras.setEnabled(true);
		btnAtras.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaOpciones();
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