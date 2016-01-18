package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import packExcepciones.NoHaySudokuCargadoException;
import packModelo.CatalogoSudoku;
import packModelo.GestorAdministrador;

public class VentanaAnadirSudoku extends JDialog implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static VentanaTablero ventana;
	JLabel[][] cajas;
	JPanel[][] paneles;
	JLabel tiempo;
	JPanel centro, sur;
	JPanel norte;
	JButton btn1,btn2,btn3;
	JComboBox<String> dif;
	JButton[] listBotones;
	KeyListener keyListener;
	JDialog dialogFinal;
	
	int dificultad = 0;
	int filaColumna = 0;
	
	//boolean flag;
	static final int MAX = 9;
	int[] activado;
	GestorAdministrador gA = GestorAdministrador.getGestorAdministrador();
	CatalogoSudoku cS = CatalogoSudoku.getCatalogoSudoku();

	/**
	 * Create the frame.
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 */
	public VentanaAnadirSudoku() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		gA = GestorAdministrador.getGestorAdministrador();
		cS = CatalogoSudoku.getCatalogoSudoku();
		gA.addObserver(this);
		setSize(500,500);
		setLocationRelativeTo(null);
		setVisible(true);
		norte = new JPanel();
		tiempo = new JLabel();
		
		
		crearListener();
		centro = new JPanel();
		centro.setLayout(new GridLayout(3,3,5,5));
		cajas = new JLabel[MAX][MAX];
		paneles = new JPanel[3][3];
		listBotones = new JButton[MAX];
		centro.setBackground(Color.black);
		activado = new int[2]; activado[0] = -1; activado[1] = -1;
		setLayout(new BorderLayout());

		add(norte,"North");
		add(centro);
		
		crearPaneles();
		crearCajas();
				
		sur = new JPanel();
		sur.setLayout(new GridLayout(3,4,0,2));
		add(sur,"South");
		
		getBotones();

		norte.setLayout(new GridLayout(1, 4));
		crearNorte(String.valueOf(cS.buscarPrimerIdDisp()));
		
		try{
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					try {
						getSeguro();
					} catch (NoHaySudokuCargadoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	
	private void getSeguro() throws NoHaySudokuCargadoException{
		int valor = JOptionPane.showConfirmDialog(this, "Â¿Estas seguro de que quieres cerrar?", "CERRAR", JOptionPane.YES_NO_OPTION);
		if(valor==JOptionPane.YES_OPTION){
			JOptionPane.showMessageDialog(null, "Gracias por jugar", "Gracias", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}
		
	private void crearPaneles(){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3;j++){
				paneles[i][j] = new JPanel();
				paneles[i][j].setLayout(new GridLayout(3,3,0,0));
				centro.add(paneles[i][j]);
				setColorPanel(i,j);
			}
		}
	}
	
	private void setColorPanel(int pI,int pJ){
		Color gris = Color.decode("0xE0E0E0");
		Color blanco = Color.decode("0xFAFAFA");
		if(((pI == 0 || pI == 2) && (pJ == 0 || pJ ==2)) || ((pI == 1 || pI == 3) && (pJ == 1 || pJ ==3))){
			paneles[pI][pJ].setBackground(blanco);
		}
		else{
			paneles[pI][pJ].setBackground(gris);

		}
		
	}
		
	private void crearCajas(){
		int f,c;
		for(int i = 0; i < MAX; i++){
			for (int j = 0; j < MAX; j++){
				cajas[i][j] = crearJLabel(i, j, " ");
							
				f = (i+1)/3;
				if((i+1)%3 > 0) f++;
				c = (j+1)/3;
				if((j+1) % 3 > 0) c++;

				paneles[f-1][c-1].add(cajas[i][j]);
				
			}
		}
	}
	
	private void crearListener(){
		keyListener = new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				char num = e.getKeyChar();
				if(Character.isDigit(num)&& num != '0'){
					if(activado[0]>=0){
						cajas[activado[0]][activado[1]].setText(num+"");
						//tab.asgValor(activado[0], activado[1],(int)(num-'0'));
					}
				}
				
			}
			
		};
	}
	
	private JLabel crearJLabel(final int pI,final int pJ, String pNum){
		Font fuente;
		
		fuente = new Font("Monospaced",Font.BOLD,20);
		
		final JLabel j = new JLabel();
		j.setText(pNum+"");
		j.setFont(fuente);
		j.setBackground(Color.green);
		j.setHorizontalAlignment(WIDTH/2);
		j.setBorder(LineBorder.createBlackLineBorder());
		j.setFocusable(true);
		j.addKeyListener(keyListener);
		MouseListener mouseListener1 = new MouseListener(){

			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(j.getBackground() != Color.green){
					desactivar();
					j.setBackground(Color.green);
					j.setOpaque(true);
					activado[0] = pI;
					activado[1] = pJ;
				}else{
					if(!j.isOpaque()){
						desactivar();
						j.setBackground(Color.green);
						j.setOpaque(true);
						activado[0] = pI;
						activado[1] = pJ;
					}else{
						j.setOpaque(false);
						activado[0] = -1;
						activado[1] = -1;
					}
				}
				//todosLosNumeros(tab.obtValorCasilla(pI, pJ));
				j.updateUI();					
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		};
		
		j.addMouseListener(mouseListener1);
		return j;

	}
	
	private void desactivar(){
		for(int i = 0; i<MAX; i++){
			for(int j = 0; j<MAX; j++){
				if(cajas[i][j].getForeground() != Color.WHITE){
					cajas[i][j].setOpaque(false);
					cajas[i][j].updateUI();
				}else{
					cajas[i][j].setBackground(Color.BLACK);
					cajas[i][j].setOpaque(true);
					cajas[i][j].updateUI();
				}
			}
		}
	}	
	
	private void getBotones(){
		getBtn1();
		for(int k=0; k<MAX; k++){			
			final String numero = Integer.toString(k+1);
			
			
			if(k == 3){
				getBtn2();
			}else if(k == 6){
				getBtn3();
			}
			
			if(listBotones[k] == null){
				listBotones[k] = new JButton(numero);
				sur.add(listBotones[k]);
				listBotones[k].addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(activado[0]>=0){
							if(String.valueOf(numero) == cajas[activado[0]][activado[1]].getText()){
								cajas[activado[0]][activado[1]].setText(" ");
							}else{
								cajas[activado[0]][activado[1]].setText(numero);
							}
						}
					}
					
				});
			}
		}
	}
	
	private void crearNorte(String pIdent){	
		dif = null;
		String[] valNivel = { "Muy Fácil", "Fácil", "Normal", "Difícil", "Muy Difícil"};
		dif = new JComboBox<String>(valNivel);
		dif.setSelectedIndex(0);	
		norte.add(new JLabel());
		norte.add(new JLabel("Sudoku Nº: "));
		norte.add(new JLabel(pIdent));
		norte.add(new JLabel());
		norte.add(new JLabel("Dificultad: "));
		norte.add(dif);
		norte.add(new JLabel());
	}
	
	private JButton getBtn1() {
		if(btn1 == null){
			btn1 = new JButton("Añadir");
			sur.add(btn1);
			btn1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean[][] error = finalizar();
					if(error == null){
						crearDialogoNoCompleto();
					}else if(error.length != 0){
						mostrarErrores(error);
					}else{
						crearDialogoFinal();
					}
				}
			});
		}
		return btn1;
	}
		
	private void mostrarErrores(boolean[][] pCasillasFallo){
		for(int i=0; i<MAX; i++){
			for(int j = 0; j<MAX; j++){
				if(pCasillasFallo[i][j]){
					cajas[i][j].setBackground(Color.red);
					cajas[i][j].setOpaque(true);
					cajas[i][j].updateUI();
				}
				else if(!cajas[i][j].getText().trim().isEmpty() && cajas[i][j].getForeground()!=Color.red && cajas[i][j].getText().length()<2){
					cajas[i][j].setBackground(Color.blue);
					cajas[i][j].setOpaque(true);
					cajas[i][j].updateUI();
				}
			}
		}
	}
		
	private JButton getBtn2() {
		if(btn2 == null){
			btn2 = new JButton("Hacer Fija");
			sur.add(btn2);
		}
		btn2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
					if(activado[0]>=0 && cajas[activado[0]][activado[1]].getForeground()!=Color.WHITE){
						cajas[activado[0]][ activado[1]].setBackground(Color.BLACK);
						cajas[activado[0]][ activado[1]].setForeground(Color.WHITE);	
					}else{
						cajas[activado[0]][ activado[1]].setBackground(Color.WHITE);
						cajas[activado[0]][ activado[1]].setForeground(Color.BLACK);
						desactivar();
					}
			}
		});
		return btn2;
		
	}
	private JButton getBtn3() {
		if(btn3 == null){
			btn3 = new JButton("Atrás");
			sur.add(btn3);
		}
		btn3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				VentanaAdministrarSudokus vnt = new VentanaAdministrarSudokus();
				vnt.setVisible(true);
				dispose();
			}
		});
		return btn3;
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		for(int j = 0; j<MAX; j++){
			for(int i=0; i<MAX; i++){
			}
		}
	}
	
	private boolean[][] finalizar(){
		int[][] casillas= new int[MAX][MAX];
		boolean[][] fijas = new boolean[MAX][MAX];
		boolean[][] result;
		if(completo()){
			for(int i = 0; i<MAX; i++){
				for(int j=0; j<MAX; j++){
					casillas[i][j] = cajas[i][j].getText().charAt(0)-'0';
					if(cajas[i][j].getForeground() == Color.WHITE){
						fijas[i][j] = true;
					}else{
						fijas[i][j] = false;
					}
				}
			}
			result = gA.crearSudoku(dif.getSelectedIndex()+1, casillas, fijas); 
			if(result.length == 0){
				getDialogFinal();
			}
		}else{
			result = null;
		}
		return result;
	}
	
	private void getDialogFinal(){
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel txt = new JLabel("El Sudoku se ha añadido correctamente");
		JButton boton = new JButton("Continuar");
		
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				VentanaAdministrarSudokus vnt = new VentanaAdministrarSudokus();
				vnt.setVisible(true);
				dialogFinal.dispose();
				dispose();
			}
			
		});
		
		dialogFinal = new JDialog();
		dialogFinal.setSize(300,125);
		dialogFinal.setModal(false);
		dialogFinal.setVisible(true);
		dialogFinal.setLocationRelativeTo(this);
		dialogFinal.setTitle("CORRECTO");
		
		dialogFinal.setLayout(new GridBagLayout());
	
		dialogFinal.add(txt,csTexto);
		csTexto.gridy = 1;
		dialogFinal.add(boton,csBoton);
	}
	
	private boolean completo(){
		boolean comp = true;
		for(int i = 0; i<MAX && comp; i++){
			for(int j=0; j<MAX && comp; j++){
				if(!Character.isDigit(cajas[i][j].getText().charAt(0))){
					comp = false;
				}
			}
		}	
		return comp;
	}
	
	private void crearDialogoNoCompleto(){
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		final JDialog dialogFinal = new JDialog();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel txt = new JLabel("El Sudoku no esta completo");
		JButton boton = new JButton("Continuar");
		
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				btn1.setEnabled(true);
				dialogFinal.dispose();
				
			}
			
		});
		
		dialogFinal.setSize(300,125);
		dialogFinal.setAlwaysOnTop(true);
		dialogFinal.setModal(false);
		dialogFinal.setVisible(true);
		dialogFinal.setLocationRelativeTo(this);
		dialogFinal.setTitle("Error");
		
		dialogFinal.setLayout(new GridBagLayout());
	
		dialogFinal.add(txt,csTexto);
		csTexto.gridy = 1;
		dialogFinal.add(boton,csBoton);
	}
	
	
	private void crearDialogoFinal(){
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		final JDialog dialogFinal = new JDialog();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel txt = new JLabel("El Sudoku se ha añadido correctamente");
		JButton boton = new JButton("Continuar");
		
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				btn1.setEnabled(true);
				dialogFinal.dispose();
				
			}
			
		});
		
		dialogFinal.setSize(300,125);
		dialogFinal.setAlwaysOnTop(true);
		dialogFinal.setModal(false);
		dialogFinal.setVisible(true);
		dialogFinal.setLocationRelativeTo(this);
		dialogFinal.setTitle("CORRECTO");
		
		dialogFinal.setLayout(new GridBagLayout());
	
		dialogFinal.add(txt,csTexto);
		csTexto.gridy = 1;
		dialogFinal.add(boton,csBoton);
	}
	
	public static void main(String arg[]) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		new VentanaAnadirSudoku();
	}
}