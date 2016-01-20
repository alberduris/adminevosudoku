package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

public class DialogoPausa extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JButton btnContinuar;
	private JButton btnSalirSinGuardar;
	private JButton btnSalirYGuardar;
	
	
	

	private Dimension dimBtn = new Dimension(200, 30);
	private Dimension dimVentana = new Dimension(250, 250);

	/**
	 * Create the frame.
	 */
	public DialogoPausa() {
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

		getTituloDialogoPausa();
		getbtnContinuar();
		getbtnSalirSinGuardar();
		getbtnSalirYGuardar();
		
		setVisible(true);

	}

	private void getTituloDialogoPausa() {
	
		lblTitulo = new JLabel("Pausa");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}

	
	

	

	private void getbtnContinuar() {
		btnContinuar = new JButton("Continuar");
		btnContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnContinuar.setMinimumSize(dimBtn);
		btnContinuar.setPreferredSize(dimBtn);
		btnContinuar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnContinuar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Tablero.obtTablero().pausado(false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnContinuar);
	}

	private void getbtnSalirSinGuardar() {
		btnSalirSinGuardar = new JButton("Salir sin guardar");
		btnSalirSinGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSalirSinGuardar.setMinimumSize(dimBtn);
		btnSalirSinGuardar.setPreferredSize(dimBtn);
		btnSalirSinGuardar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnSalirSinGuardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(contentPane, "El Sudoku no se guardará y se cerrará la aplicación");
				Sesion.obtSesion().borrarTablero();
				Sesion.obtSesion().finSesion(false);
				System.exit(EXIT_ON_CLOSE);
			}
			
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnSalirSinGuardar);
	}

	
	private void getbtnSalirYGuardar() {
		btnSalirYGuardar = new JButton("Salir y guardar");
		btnSalirYGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSalirYGuardar.setMinimumSize(dimBtn);
		btnSalirYGuardar.setPreferredSize(dimBtn);
		btnSalirYGuardar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnSalirYGuardar.setEnabled(true);
		
		btnSalirYGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "El sudoku se continuará la proxima vez que juege");
				Sesion.obtSesion().finSesion(true);
				System.exit(EXIT_ON_CLOSE);
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnSalirYGuardar);
	}
}