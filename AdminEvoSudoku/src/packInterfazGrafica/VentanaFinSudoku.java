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
import javax.swing.border.Border;

import packModelo.Sesion;
import packModelo.Tablero;

public class VentanaFinSudoku extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	
	private JLabel lblPuntuacion;
	

	private JButton btnRetar;
	private JButton btnCompartir;
	private JButton btnMenu;
	
	private Dimension dimBtn = new Dimension(200, 30);
	private Dimension dimVentana = new Dimension(325, 300);
	
	private int punt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new VentanaFinSudoku();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaFinSudoku() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(dimVentana);
		setLocationRelativeTo(null);

		Tablero.obtTablero().terminar();
		
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

		punt = Tablero.obtTablero().obtPuntuacion();
		
		getTituloFinSudoku();
		getLblPuntuacion();
		
		getbtnRetar();
		getbtnCompartir();
		getbtnMenu();
		
		setVisible(true);

	}

	private void getTituloFinSudoku() {
	
		if(punt == 0){
			lblTitulo = new JLabel("Sudoku no finalizado");
		}else{
			lblTitulo = new JLabel("Sudoku finalizado");
		}
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}

	private void getLblPuntuacion() {
		lblPuntuacion = new JLabel();
		if(punt == 0){
			lblPuntuacion.setText("No has conseguido completar el sudoku");
		}else{
			lblPuntuacion.setText("Has conseguido "+String.valueOf(punt)+" puntos");	
		}		
		lblPuntuacion.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPuntuacion.setHorizontalAlignment(0);
		lblPuntuacion.setMinimumSize(dimBtn);
		lblPuntuacion.setPreferredSize(dimBtn);
		lblPuntuacion.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		
	

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(lblPuntuacion);
	}
	

	

	private void getbtnRetar() {
		btnRetar = new JButton("Retar");
		btnRetar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRetar.setMinimumSize(dimBtn);
		btnRetar.setPreferredSize(dimBtn);
		btnRetar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnRetar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Has pulsado retar");
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnRetar);
	}

	private void getbtnCompartir() {
		btnCompartir = new JButton("Compartir");
		btnCompartir.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCompartir.setMinimumSize(dimBtn);
		btnCompartir.setPreferredSize(dimBtn);
		btnCompartir.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnCompartir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(contentPane, "Has pulsado compartir");
			}
			
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnCompartir);
	}

	
	private void getbtnMenu() {
		btnMenu = new JButton("Menú principal");
		btnMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnMenu.setMinimumSize(dimBtn);
		btnMenu.setPreferredSize(dimBtn);
		btnMenu.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnMenu.setEnabled(true);
		
		btnMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(Sesion.obtSesion().obtNombreUsuario().equals("Admin")){
						new VentanaMenuPpalAdministrador();
					}
					else{
						new VentanaMenuPpalUsuario();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				dispose();
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnMenu);
	}
}