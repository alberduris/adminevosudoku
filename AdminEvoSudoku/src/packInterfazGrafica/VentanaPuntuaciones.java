package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import packModelo.GestorEstadisticas;
import packModelo.Sesion;

public class VentanaPuntuaciones extends JFrame {

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
	private JComboBox<String> tam, principal;
	
	private JButton btnAtras;

	GestorEstadisticas gE = GestorEstadisticas.obtGestorEstadisticas();
	
	private Dimension dimVentana = new Dimension(400, 400);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaPuntuaciones();
	}

	/**
	 * Create the frame.
	 */
	public VentanaPuntuaciones() {
		gE = GestorEstadisticas.obtGestorEstadisticas();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(dimVentana);
		setLocationRelativeTo(null);

		norte = new JPanel();
		add(norte, BorderLayout.NORTH);
		centro = new JPanel();
		centro.setLayout(new BorderLayout());
		add(centro, BorderLayout.CENTER);
		sur = new JPanel();
		add(sur, BorderLayout.SOUTH);
		
		panelConBorderLayout = new JPanel();
		panelConBoxLayout = new JPanel();

		BorderLayout border = new BorderLayout();
		panelConBorderLayout.setLayout(border);
		BoxLayout box = new BoxLayout(panelConBoxLayout, BoxLayout.Y_AXIS);
		panelConBoxLayout.setLayout(box);

		panelConBorderLayout.add(panelConBoxLayout, BorderLayout.CENTER);

		getTituloVentanaTitulo();
		
		crearNorte("Total", 0);
		
		getCentro();
		
		getBtnAtras();
		
		setVisible(true);
	}

	private void getTituloVentanaTitulo() {
	
		lblTitulo = new JLabel("Ranking Global");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		norte.add(lblTitulo);

	}
	
	private void crearNorte(String pPrincipal, int pTam){	
		principal = null;
		String[] valTamano = {"Total", "Sudoku"};
		principal = new JComboBox<String>(valTamano);
		principal.setSelectedItem(pPrincipal);
		principal.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				centro.removeAll();
				centro.updateUI();
				crearNorte((String) principal.getSelectedItem(), 0);
				getCentro();
			}
			
		});
		tam = null;
		if(pPrincipal == "Total"){
			String[] valTamano1 = {"Todos", "5", "10", "50", "75"};
			tam = new JComboBox<String>(valTamano1);
		}else{
			String[] valTamano1 = gE.obtSudokusJugados(Sesion.obtSesion().obtNombreUsuario());
			tam = new JComboBox<String>(valTamano1);
		}
		tam.setSelectedIndex(pTam);
		tam.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				centro.removeAll();
				centro.updateUI();
				crearNorte((String) principal.getSelectedItem(), tam.getSelectedIndex());
				getCentro();
			}
			
		});
		JPanel centroNorte = new JPanel(new GridLayout(1,4));
		JLabel prin = new JLabel("Ranking: ");
		prin.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		JLabel est;
		if(principal.getSelectedItem()=="Total"){
			est = new JLabel("Sudoku: ");
		}else{
			est = new JLabel("Cantidad: ");
		}
		est.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		centroNorte.add(prin);
		centroNorte.add(principal);
		centroNorte.add(est);
		centroNorte.add(tam);
		centro.add(centroNorte, BorderLayout.NORTH);
	}
	
	private void getCentro(){
		if(principal.getSelectedItem()=="Total"){
			getListaTotal();
		}else{
			getListaSudokus();
		}
	}
	
	private void getListaTotal() {
		String[][] com = new String[0][0];
		JLabel[] fila = new JLabel[3];
		if(tam.getSelectedItem() == "Todos"){
			com = gE.obtRanking(0, 0);
		}else{
			com = gE.obtRanking(Integer.valueOf((String) tam.getSelectedItem()),0);
		}
		lista = new JPanel(new GridLayout(com.length+1, 1));
		lista.setAutoscrolls(false);
		if(com.length == 0){				
			JLabel txt = new JLabel("<html><big>No Existe Jugadores en el Ranking</big></html>"); 
			txt.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
			centro.add(txt);
		}else{
			combinaciones = new JPanel[com.length+1];
			for(int i = 0; i<com.length+1; i++){
				combinaciones[i] = new JPanel(new GridLayout(1, 3));
				combinaciones[i].setBackground(Color.WHITE);
				if(i==0){
					fila[0] = new JLabel("<html><font SIZE=4><u>Posición</u></font></html>");
					fila[1] = new JLabel("<html><font SIZE=4><u>Usuario</u></font></html>");
					fila[2] = new JLabel("<html><font SIZE=4><u>Puntuación</u></font></html>");
				}else{
					
					fila[0] = new JLabel(String.valueOf(i)+".");
					fila[1] = new JLabel(com[i-1][0]);
					fila[2] = new JLabel(com[i-1][1]);
				}
				for(int j = 0; j<3; j++){
					fila[j].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
					combinaciones[i].add(fila[j]);
				}
				lista.add(combinaciones[i]);
				JScrollPane scroll = new JScrollPane(lista);
				centro.add(scroll, BorderLayout.CENTER);
				
				centro.add(Box.createRigidArea(new Dimension(0, 15)));
				centro.add(scroll);
			}			
		}
	}
	
	private void getListaSudokus() {
		String[][] com = new String[0][0];
		com = gE.obtRanking(0, Integer.valueOf((String) tam.getSelectedItem()));
		lista = new JPanel(new GridLayout(com.length+1, 1));
		lista.setAutoscrolls(false);
		if(com.length == 0){				
			centro.add(Box.createRigidArea(new Dimension(0, 15)));
			centro.add(new JLabel("<html><big>No Existe Retos para Aceptar ni Rechazar</big></html>"));
		}else{
			combinaciones = new JPanel[com.length+1];
			for(int i = 0; i<com.length+1; i++){
				combinaciones[i] = new JPanel(new GridLayout(1, 4));
				combinaciones[i].setBackground(Color.WHITE);
				if(i==0){
					combinaciones[i].add(new JLabel(" "));
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>Posición</u></font></html>"));
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>Usuario</u></font></html>"));
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>Puntuación</u></font></html>"));
					combinaciones[i].add(new JLabel(" "));
				}else{
					combinaciones[i].add(new JLabel(" "));
					combinaciones[i].add(new JLabel(String.valueOf(i)+"."));
					combinaciones[i].add(new JLabel(com[i-1][0]));
					combinaciones[i].add(new JLabel(com[i-1][1]));
					combinaciones[i].add(new JLabel(" "));
				}
				lista.add(combinaciones[i]);
				JScrollPane scroll = new JScrollPane(lista);
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
					if(Sesion.obtSesion().obtNombreUsuario().equals("Admin")){
						new VentanaMenuPpalAdministrador();
					}
					else{
						new VentanaMenuPpalUsuario();
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
}