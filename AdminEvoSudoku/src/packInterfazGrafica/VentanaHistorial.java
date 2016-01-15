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

public class VentanaHistorial extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel norte, centro, sur;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JPanel lista;
	private JPanel[] combinaciones;
	private JComboBox<String> tipos, usuarios;
	
	private JButton btnAtras;

	GestorEstadisticas gE = GestorEstadisticas.obtGestorEstadisticas();
	
	private Dimension dimVentana = new Dimension(490, 500);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaHistorial();
	}

	/**
	 * Create the frame.
	 */

	public VentanaHistorial() {
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
		
		panelConBoxLayout = new JPanel();

		BoxLayout box = new BoxLayout(panelConBoxLayout, BoxLayout.Y_AXIS);
		panelConBoxLayout.setLayout(box);

		getTituloVentanaTitulo();
		
		crearNorte(Sesion.obtSesion().obtNombreUsuario(), 0);
		
		getSudokus();
			
		getBtnAtras();
		
		setVisible(true);
	}

	private void getTituloVentanaTitulo() {
	
		lblTitulo = new JLabel("Historial");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 23));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		norte.add(lblTitulo);

	}
	
	private void crearNorte(String pUs, int pTipo){	
		tipos = null;
		String[] valTipos = {"Sudokus", "Retos", "Premios"};
		tipos = new JComboBox<String>(valTipos);
		tipos.setSelectedIndex(pTipo);
		tipos.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				centro.removeAll();
				if(tipos.getSelectedItem() == "Sudokus"){
					getSudokus();
				}else if(tipos.getSelectedItem() == "Retos"){
					getRetos();
				}else{
					getPremios();
				}
				centro.updateUI();
				crearNorte((String)usuarios.getSelectedItem(), tipos.getSelectedIndex());
			}
			
		});
		usuarios = null;
		String[] valUsus = gE.obtJugadores();
		usuarios = new JComboBox<String>(valUsus);
		usuarios.setSelectedItem(pUs);
		usuarios.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				centro.removeAll();
				if(tipos.getSelectedItem() == "Sudokus"){
					getSudokus();
				}else if(tipos.getSelectedItem() == "Retos"){
					getRetos();
				}else{
					getPremios();
				}
				centro.updateUI();
				crearNorte((String)usuarios.getSelectedItem(), tipos.getSelectedIndex());

			}
			
		});
		
		JPanel centroNorte = new JPanel(new GridLayout(1,4));
		JLabel est = new JLabel("Mostrar: ");
		est.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		JLabel est1 = new JLabel("Usuario: ");
		est1.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		centroNorte.add(est1);
		centroNorte.add(usuarios);
		centroNorte.add(est);
		centroNorte.add(tipos);
		centro.add(centroNorte, BorderLayout.NORTH);
	}
	
	private void getSudokus(){
		String[][] com = new String[0][0];
		com = gE.obtPuntSudokusJugados((String)usuarios.getSelectedItem());
		lista = new JPanel(new GridLayout(com.length+1, 1));
		lista.setAutoscrolls(false);
		JButton btnCompartir = null;
		if(com.length == 0){		
			JLabel txt = new JLabel("<html><big>No Existe Sudokus jugados por "+(String)usuarios.getSelectedItem()+"</big></html>");
			txt.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
			centro.add(txt);
			}else{
			combinaciones = new JPanel[com.length+1];
			for(int i = 0; i<com.length+1; i++){
				combinaciones[i] = new JPanel(new GridLayout(1, 3));
				combinaciones[i].setBackground(Color.WHITE);
				JLabel l1, l2;
				if(i==0){
					l1 = new JLabel("<html><font SIZE=4><u>IdSudoku</u></font></html>");
					l2 = new JLabel("<html><font SIZE=4><u>Puntuacion</u></font></html>");
				}else{
					l1 = new JLabel(com[i-1][0]);
					l2 = new JLabel(com[i-1][1]);
					btnCompartir = new JButton("Compartir");
					btnCompartir.setActionCommand(String.valueOf(i));
					btnCompartir.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int num = Integer.valueOf(e.getActionCommand());
							getCompartir(num);
						}
					});
				}
				l1.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
				l2.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
				combinaciones[i].add(l1);
				combinaciones[i].add(l2);
				if(i==0){
					combinaciones[i].add(new JLabel());
				}else{
					combinaciones[i].add(btnCompartir);	
				}
				lista.add(combinaciones[i]);
				JScrollPane scroll = new JScrollPane(lista);
				centro.add(scroll, BorderLayout.CENTER);
				
				centro.add(Box.createRigidArea(new Dimension(0, 15)));
				centro.add(scroll);
			}			
		}
	}
	
	
	private void getRetos(){
		String[][] com = gE.obtenerRetosFinalizados();
		lista = new JPanel(new GridLayout(com.length+1, 1));
		lista.setAutoscrolls(false);
		JButton btnCompartir = null;
		if(com.length == 0){				
			JLabel txt = new JLabel("<html><big>No Existe Retos finalizados</big></html>");
			txt.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
			centro.add(txt);
		}else{
			combinaciones = new JPanel[com.length+1];
			for(int i = 0; i<com.length+1; i++){
				combinaciones[i] = new JPanel(new GridLayout(1, 5));
				if(i==0){
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>Usuario</u></font></html>"));
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>IdSudoku</u></font></html>"));
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>Puntuacion</u></font></html>"));
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>Estado</u></font></html>"));
					combinaciones[i].add(new JLabel());
				}else{
					combinaciones[i].add(new JLabel(com[i-1][0]));
					combinaciones[i].add(new JLabel(com[i-1][1]));
					combinaciones[i].add(new JLabel(com[i-1][2]));
					if(com[i-1][3] == "true"){
						combinaciones[i].add(new JLabel("SUPERADO"));
					}else{
						combinaciones[i].add(new JLabel("NO SUPERADO"));
					}
					btnCompartir = new JButton("Compartir");
					btnCompartir.setActionCommand(String.valueOf(i));
					btnCompartir.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int num = Integer.valueOf(e.getActionCommand());
							getCompartir(num);
						}
					});
					combinaciones[i].add(btnCompartir);
				}
				lista.add(combinaciones[i]);
			}
			JScrollPane scroll = new JScrollPane(lista);
			centro.setLayout(new BorderLayout());
			centro.add(scroll, BorderLayout.CENTER);
			
			centro.add(Box.createRigidArea(new Dimension(0, 15)));
			centro.add(scroll);
		}
	}
	
	private void getPremios(){
		String[][] com = gE.obtInfoPremios((String)usuarios.getSelectedItem());
		lista = new JPanel(new GridLayout(com.length+1, 1));
		lista.setAutoscrolls(false);
		JButton btnCompartir = null;
		if(com.length == 0){				
			JLabel txt = new JLabel("<html><big>Este usuario no ha obtenido ningun premio</big></html>");
			txt.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
			centro.add(txt);
		}else{
			combinaciones = new JPanel[com.length+1];
			for(int i = 0; i<com.length+1; i++){
				combinaciones[i] = new JPanel(new GridLayout(1, 4));
				combinaciones[i].setBackground(Color.WHITE);
				JLabel nom, obj, fech;
				if(i==0){
					nom = new JLabel("<html><font SIZE=4><u>Premio</u></font></html>");
					obj = new JLabel("<html><font SIZE=4><u>Objetivo</u></font></html>");
					fech = new JLabel("<html><font SIZE=4><u>Fecha</u></font></html>");
				}else{					
					nom = new JLabel("<html>"+com[i-1][0]+"</html>");
					if(com[i-1][4].equals("Tiempo")){
						obj = new JLabel("<html>Se ha completado el<br>sudoku "+com[i-1][3]+" en un<br>tiempo menor a "+com[i-1][2]+"</html>");
					}else{
						obj = new JLabel("<html>Se ha completado el<br> sudoku "+com[i-1][3]+" con una<br>puntuacion mayor a "+com[i-1][2]+"</html>");
					}
					fech = new JLabel("<html>"+com[i-1][1]+"</html>");
					btnCompartir = new JButton("Compartir");
					btnCompartir.setActionCommand(String.valueOf(i));
					btnCompartir.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int num = Integer.valueOf(e.getActionCommand());
							getCompartir(num);
						}
					});
				}		
				nom.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
				obj.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
				fech.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
				combinaciones[i].add(nom);
				combinaciones[i].add(obj);
				combinaciones[i].add(fech);
				if(i==0){
					combinaciones[i].add(new JLabel());
				}else{
					combinaciones[i].add(btnCompartir);	
				}
				lista.add(combinaciones[i]);
			}
			JScrollPane scroll = new JScrollPane(lista);
			centro.setLayout(new BorderLayout());
			centro.add(scroll, BorderLayout.CENTER);
			
			centro.add(Box.createRigidArea(new Dimension(0, 15)));
			centro.add(scroll);
		}
	}	

	private void getBtnAtras() {
		btnAtras = new JButton("Atras");
		btnAtras.setEnabled(true);
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Sesion.obtSesion().obtNombreUsuario() == "Admin"){
					new VentanaMenuPpalAdministrador();
				}else{
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
	
	private void getCompartir(int num) {
		String textoAPoner;
		if(tipos.getSelectedItem().equals("Premios")){
			JLabel jL = (JLabel) combinaciones[num].getComponent(0);
			String nom = jL.getText();
			jL = (JLabel) combinaciones[num].getComponent(1);
			String obj = jL.getText();
			textoAPoner="He conseguido el premio "+nom+" con el objetivo de '"+obj+"' en %23AdminEvoSudoku. ¿A que no lo consigues?";
		}else if(tipos.getSelectedItem().equals("Retos")){
			JLabel jL = (JLabel) combinaciones[num].getComponent(2);
			int punt = Integer.valueOf(jL.getText().trim());
			jL = (JLabel) combinaciones[num].getComponent(1);
			int id = Integer.valueOf(jL.getText().trim());
			jL = (JLabel) combinaciones[num].getComponent(0);
			String nombre = jL.getText().trim();
			jL = (JLabel) combinaciones[num].getComponent(0);
			if(jL.getText().trim().equals("SUPERADO")){
				textoAPoner="He ganado un reto al jugador "+nombre+" con una puntuacion de "+punt+" puntos en el% sudoku "+id+" de %23AdminEvoSudoku. ¿A que no lo superas?";
			}else{
				textoAPoner="He perdido un reto con el jugador "+nombre+" con una puntuacion de "+punt+" puntos en el% sudoku "+id+" de %23AdminEvoSudoku. ¿A que no lo superas?";
			}
		}else{
			JLabel jL = (JLabel) combinaciones[num].getComponent(1);
			int punt = Integer.valueOf(jL.getText().trim());
			jL = (JLabel) combinaciones[num].getComponent(0);
			int id = Integer.valueOf(jL.getText().trim());
			textoAPoner="He conseguido "+punt+" puntos al completar el sudoku "+id+" de %23AdminEvoSudoku. ¿A que no lo superas?";
		}
		Sesion.obtSesion().compartir(quitarEspacios(textoAPoner));
				
	}	
	
	private String quitarEspacios(String pTxt){
		String texto = "";
		int i = 0;
		while(i<pTxt.length()){
			if(pTxt.charAt(i) == ' '){
				texto+="%20";
				i++;
			}else if(pTxt.charAt(i) == '<'){
				int j = 1;
				while(pTxt.charAt(i+j)!='>'){
					j++;
				}
				i += j+1;
				if(j==3){
					texto+="%20";
				}
			}else{
				texto+=pTxt.charAt(i);
				i++;
			}
		}
		return texto;
	}
	
	
	private Component leftJustify(JButton btn)  {
	    Box  b = Box.createHorizontalBox();
	    b.add(btn);
	    b.add( Box.createHorizontalGlue() );
	  
	    return b;
	}
}
