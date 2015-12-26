package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import packModelo.GestorEstadisticas;
import packModelo.Sesion;

public class VentanaAdministrarRetos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel norte, centro, sur;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	
	private JPanel lista;
	private JPanel[] combinaciones;
	
	private JButton btnAtras;

	GestorEstadisticas gE = GestorEstadisticas.obtGestorEstadisticas();
	
	private Dimension dimVentana = new Dimension(500, 400);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new VentanaAdministrarRetos();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaAdministrarRetos() {
		gE = GestorEstadisticas.obtGestorEstadisticas();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(dimVentana);
		setLocationRelativeTo(null);

		norte = new JPanel();
		add(norte, BorderLayout.NORTH);
		centro = new JPanel();
		add(centro, BorderLayout.CENTER);
		sur = new JPanel();
		add(sur, BorderLayout.SOUTH);
		
		panelConBorderLayout = new JPanel();
		panelConBoxLayout = new JPanel();

		BorderLayout border = new BorderLayout();
		panelConBorderLayout.setLayout(border);
		BoxLayout box = new BoxLayout(panelConBoxLayout, BoxLayout.Y_AXIS);
		panelConBoxLayout.setLayout(box);

		//contentPane.add(panelConBorderLayout);
		panelConBorderLayout.add(panelConBoxLayout, BorderLayout.CENTER);

		getTituloVentanaOpcionesAdmin();
		

		getLista();
		
		getBtnAtras();
		
		setVisible(true);
	}

	private void getTituloVentanaOpcionesAdmin() {
	
		lblTitulo = new JLabel("Administrar Retos");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		norte.add(lblTitulo);

	}
	
	private void getLista() {
		String[][] com = gE.obtenerRetos();
		lista = new JPanel(new GridLayout(com.length+1, 1));
		lista.setAutoscrolls(false);
		JButton btnAceptar, btnRechazar;
		if(com.length == 0){				
			centro.add(Box.createRigidArea(new Dimension(0, 15)));
			centro.add(new JLabel("<html><big>No Existe Retos para Aceptar ni Rechazar</big></html>"));
		}else{
			System.out.println(com.length);
			combinaciones = new JPanel[com.length+1];
			for(int i = 0; i<com.length+1; i++){
				combinaciones[i] = new JPanel(new GridLayout(1, 5));
				if(i==0){
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>Usuario</u></font></html>"));
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>IdSudoku</u></font></html>"));
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>Puntuación</u></font></html>"));
					combinaciones[i].add(new JLabel());
					combinaciones[i].add(new JLabel());
				}else{
					combinaciones[i].add(new JLabel(com[i-1][0]));
					combinaciones[i].add(new JLabel(com[i-1][1]));
					combinaciones[i].add(new JLabel(com[i-1][2]));
					btnAceptar = new JButton("Aceptar");
					btnAceptar.setActionCommand(String.valueOf(i));
					btnAceptar.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int num = Integer.valueOf(e.getActionCommand());
							getDialogSeguro(num, true);
						}	
					});
					btnRechazar = new JButton("Rechazar");
					btnRechazar.setActionCommand(String.valueOf(i));
					btnRechazar.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int num = Integer.valueOf(e.getActionCommand());
							getDialogSeguro(num, false);
						}	
					});
					combinaciones[i].add(btnAceptar);
					combinaciones[i].add(btnRechazar);
				}
				lista.add(combinaciones[i]);
				JScrollPane scroll = new JScrollPane(lista);
				centro.setLayout(new BorderLayout());
				centro.add(scroll, BorderLayout.CENTER);
				
				centro.add(Box.createRigidArea(new Dimension(0, 15)));
				centro.add(scroll);
			}			
		}
	}

	private void getBtnAtras() {
		btnAtras = new JButton("Atrás");
		btnAtras.setEnabled(true);
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(Sesion.obtSesion().obtNombreUsuario().equals("Admin")){
						new VentanaMenuPpalAdministrador();
					}
					else{
						new VentanaMenuPpalUsuario();
					}				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				dispose();
				
			}
		});
		JPanel panelBox = new JPanel();
		
		panelBox.setLayout(new BoxLayout(panelBox, BoxLayout.Y_AXIS));
		panelBox.add(Box.createRigidArea(new Dimension(5, 5)));
		panelBox.add(leftJustify(btnAtras));
		sur.add(panelBox);
		
	}
	
	
	private Component leftJustify(JButton btn)  {
	    Box  b = Box.createHorizontalBox();
	    b.add(btn);
	    b.add( Box.createHorizontalGlue() );
	  
	    return b;
	}
	
	private void getDialogSeguro(final int pNum, final boolean pAceptar){
		setEnabled(false);
		final JDialog dialogSeguro = new JDialog();
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel texto;
		if(pAceptar){
			texto = new JLabel("<html>¿Quiere aceptar el Reto?<br>Se borrará el sudoku guardado<br>y se rechazarán los retos con este sudoku</html>");
		}else{
			texto = new JLabel("<html>¿Quiere rechazar el Reto?<br>Esta acción no se puede echar atrás</html>");	
		}
		JButton botonSi = new JButton("Sí");
		
		botonSi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {	
				JLabel p = (JLabel) combinaciones[pNum].getComponent(0); 
				String nombre =  p.getText();
				p = (JLabel) combinaciones[pNum].getComponent(1); 
				int id = Integer.parseInt(p.getText());
				if(pAceptar){
					gE.aceptarReto(nombre, id);
					new VentanaTablero();
					dispose();
				}else{
					gE.rechazarReto(nombre, id);	
					setEnabled(true);
					centro.removeAll();
					lista.removeAll();
					getLista();
					centro.updateUI();
					
					
					dialogSeguro.dispose();
				}
			}
			
			
		});
		
		JButton botonNo = new JButton("No");
		
		botonNo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setEnabled(true);
				dialogSeguro.dispose();
			}
			
			
		});
		
		dialogSeguro.setSize(300,125);
		dialogSeguro.setModal(false);
		dialogSeguro.setVisible(true);
		dialogSeguro.setTitle("Seguro");
		dialogSeguro.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		dialogSeguro.setLocationRelativeTo(this);		
		dialogSeguro.setLayout(new GridBagLayout());
		dialogSeguro.getContentPane().setBackground(new Color(0xFFFFFF));
		
		botonSi.setAlignmentX(Component.LEFT_ALIGNMENT);
		botonNo.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JPanel respuesta = new JPanel();
		respuesta.add(botonSi,csBoton);
		respuesta.add(botonNo,csBoton);
		
		dialogSeguro.add(texto,csTexto);
		dialogSeguro.add(respuesta, csBoton);	

	}

}