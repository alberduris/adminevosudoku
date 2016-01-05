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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import packModelo.GestorBD;
import packModelo.Sesion;
import packModelo.Sudoku;
import packModelo.Tablero;

public class VentanaTablero extends JDialog implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static VentanaTablero ventana;
	JLabel[][] cajas;
	JPanel[][] paneles;
	JLabel tiempo, intentos, pistas;
	JPanel centro, sur;
	JPanel norte;
	JButton btn1,btn2,btn3, btnGuide, btnSound;
	JButton[] listBotones;
	KeyListener keyListener;
	JDialog dialogFinal;
	private JDialog dialogGuide;
	
	int pI = 0;
	int filaColumna = 0;
	
	//boolean flag;
	private boolean dispose = false;
	static final int MAX = 9;
	int[] activado;
	Tablero tab = Tablero.obtTablero();	
	VentanaFinSudoku vnt;

	/**
	 * Create the frame.
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 */
	public VentanaTablero() {
		tab = Tablero.obtTablero();
		tab.addObserver(this);
		setSize(500,500);
		setLocationRelativeTo(null);
		norte = new JPanel();
		tiempo = new JLabel();
		intentos = new JLabel();
		pistas = new JLabel();
		crearTiempo();
		getPistas();
		getIntentos();
		
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
		norte.add(pistas);
		norte.add(intentos);
		norte.add(tiempo);
		norte.add(btnGuide);	
		try{
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					ocultar();
					tab.pausado(true);
					new DialogoPausa();
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		setVisible(true);
		if(!tab.obtPausado() && !tab.obtTiempoAjustado()){
			tab.reiniciar();
		}else if(tab.obtPausado()){
			ocultar();
			new DialogoPausa();
		}else{
			
		}
		
	}
	
	private void hiloCelebracion(){
		new Thread(new Runnable()
		{
			public void run()
			{
				while(true){
					celebracion(pI,filaColumna);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pI++;
					if(pI == 10){pI=0;}
					if(filaColumna == 0 && pI == 0) filaColumna = 1;
					else if (filaColumna == 1 && pI == 0) filaColumna = 2;
					else if (filaColumna == 2 && pI == 0) filaColumna = 3;
					else if (filaColumna == 3 && pI == 0) filaColumna = 4;
					else if (filaColumna == 4 && pI == 0) filaColumna = 5;
					else if (filaColumna == 5 && pI == 0) filaColumna = 6;
					else if (filaColumna == 6 && pI == 0) filaColumna = 0;
					if(dispose){
						break;
					}
				}
			}
		}).start();
		
		
	}

	private void celebracion(int pI,int pFilaColumna){
		
		Color azul = Color.blue;
		Color verde = Color.green;
		Color rojo = Color.red;
		int i = pI;
		
		
		switch(pFilaColumna){
		
		case 0:
			switch(i){
			case 0: cambiarColorFila(azul,0);break;
			case 1: cambiarColorFila(verde,1);break;
			case 2: cambiarColorFila(rojo,2);break;
			case 3: cambiarColorFila(azul,3);break;
			case 4: cambiarColorFila(verde,4);break;
			case 5: cambiarColorFila(rojo,5);break;
			case 6: cambiarColorFila(azul,6);break;
			case 7: cambiarColorFila(verde,7);break;
			case 8: cambiarColorFila(rojo,8);break;
			case 9: desactivar();break;	
			}break;
		case 1:	
			
			switch(i){
			case 0: cambiarColorColumna(azul,0);break;
			case 1: cambiarColorColumna(verde,1);break;
			case 2: cambiarColorColumna(rojo,2);break;
			case 3: cambiarColorColumna(azul,3);break;
			case 4: cambiarColorColumna(verde,4);break;
			case 5: cambiarColorColumna(rojo,5);break;
			case 6: cambiarColorColumna(azul,6);break;
			case 7: cambiarColorColumna(verde,7);break;
			case 8: cambiarColorColumna(rojo,8);break;
			case 9: desactivar();break;	
			}break;
		
		
		case 2:	
		
			switch(i){
			case 8: cambiarColorColumnaInverso(azul,0);break;
			case 7: cambiarColorColumnaInverso(verde,1);break;
			case 6: cambiarColorColumnaInverso(rojo,2);break;
			case 5: cambiarColorColumnaInverso(azul,3);break;
			case 4: cambiarColorColumnaInverso(verde,4);break;
			case 3: cambiarColorColumnaInverso(rojo,5);break;
			case 2: cambiarColorColumnaInverso(azul,6);break;
			case 1: cambiarColorColumnaInverso(verde,7);break;
			case 0: cambiarColorColumnaInverso(rojo,8);break;
			case 9: desactivar();break;	
			}break;
		
		case 3:	
			
			switch(i){
			case 8: cambiarColorFilaInverso(azul,0);break;
			case 7: cambiarColorFilaInverso(verde,1);break;
			case 6: cambiarColorFilaInverso(rojo,2);break;
			case 5: cambiarColorFilaInverso(azul,3);break;
			case 4: cambiarColorFilaInverso(verde,4);break;
			case 3: cambiarColorFilaInverso(rojo,5);break;
			case 2: cambiarColorFilaInverso(azul,6);break;
			case 1: cambiarColorFilaInverso(verde,7);break;
			case 0: cambiarColorFilaInverso(rojo,8);break;
			case 9: desactivar();break;	
			}break;
			
		case 4:
			
			switch(i){
			case 8: cambiarColorSeccion(azul,0,0);break;
			case 7: cambiarColorSeccion(verde,0,3);break;
			case 6: cambiarColorSeccion(rojo,0,6);break;
			case 5: cambiarColorSeccion(rojo,3,0);break;
			case 4: cambiarColorSeccion(azul,3,3);break;
			case 3: cambiarColorSeccion(verde,3,6);break;
			case 2: cambiarColorSeccion(verde,6,0);break;
			case 1: cambiarColorSeccion(rojo,6,3);break;
			case 0: cambiarColorSeccion(azul,6,6);break;
			case 9: desactivar();break;	
			}break;
			
		case 5:
			switch(i){
			case 1: cambiarColorParaDentro(azul,0);break;
			case 3: cambiarColorParaDentro(verde,1);break;
			case 5: cambiarColorParaDentro(rojo,2);break;
			case 7: cambiarColorParaDentro(azul,3);break;
			case 9: cambiarColorParaDentro(verde,4);break;
			}break;
		
		case 6:
			
			switch(i){
			case 0: cambiarColorParaFuera(4);break;
			case 2: cambiarColorParaFuera(3);break;
			case 4: cambiarColorParaFuera(2);break;
			case 6: cambiarColorParaFuera(1);break;
			case 8: cambiarColorParaFuera(0);break;
			case 9: desactivar();break;	
			}break;
	}
		
				
		
		
	}
	
	private void cambiarColorFila(Color pColor, int pFila){
		
		for(int j = 0; j < MAX; j++){
			cajas[pFila][j].setBackground(pColor);
			cajas[pFila][j].setOpaque(true);
			cajas[pFila][j].updateUI();
			
			if(pFila > 0){
				cajas[pFila-1][j].setOpaque(false);
				cajas[pFila-1][j].updateUI();
			}
			
		}	
	}
	
	private void cambiarColorColumna(Color pColor, int pColumna){
		
		for(int i = 0; i < MAX; i++){
			cajas[i][pColumna].setBackground(pColor);
			cajas[i][pColumna].setOpaque(true);
			cajas[i][pColumna].updateUI();
			
			if(pColumna > 0){
				cajas[i][pColumna-1].setOpaque(false);
				cajas[i][pColumna-1].updateUI();
			}
			
		}	
	}
	private void cambiarColorColumnaInverso(Color pColor, int pColumna){
		
		for(int i = 0; i < MAX; i++){
			cajas[i][pColumna].setBackground(pColor);
			cajas[i][pColumna].setOpaque(true);
			cajas[i][pColumna].updateUI();
		}	
	}
	
	private void cambiarColorFilaInverso(Color pColor, int pFila){
		
		for(int j = 0; j < MAX; j++){
			cajas[pFila][j].setBackground(pColor);
			cajas[pFila][j].setOpaque(true);
			cajas[pFila][j].updateUI();
		}	
	}
	private void cambiarColorSeccion(Color pColor, int pFila, int pColumna){
		
		for(int i = pFila; i<pFila+3; i++){
			for(int j = 0; j < pColumna+3; j++){
				cajas[i][j].setBackground(pColor);
				cajas[i][j].setOpaque(true);
				cajas[i][j].updateUI();
			}
		}	
			
	}
	private void cambiarColorParaDentro(Color pColor, int pNivel){
		
		for(int i = pNivel; i<MAX-pNivel; i++){
			cajas[i][pNivel].setBackground(pColor);
			cajas[i][pNivel].setOpaque(true);
			cajas[i][pNivel].updateUI();
		}
		for(int i = pNivel; i < MAX-pNivel; i++){
			cajas[i][MAX-pNivel-1].setBackground(pColor);
			cajas[i][MAX-pNivel-1].setOpaque(true);
			cajas[i][MAX-pNivel-1].updateUI();			
		}
		for(int j = pNivel; j < MAX-pNivel; j++){
			cajas[pNivel][j].setBackground(pColor);
			cajas[pNivel][j].setOpaque(true);
			cajas[pNivel][j].updateUI();			
		}
		for(int j = pNivel; j < MAX-pNivel; j++){
			cajas[MAX-pNivel-1][j].setBackground(pColor);
			cajas[MAX-pNivel-1][j].setOpaque(true);
			cajas[MAX-pNivel-1][j].updateUI();			
		}
	}
	private void cambiarColorParaFuera(int pNivel){
		
		for(int i = pNivel; i<MAX-pNivel; i++){
			for(int j = pNivel; j < MAX-pNivel; j++){
				cajas[i][j].setOpaque(false);
				cajas[i][j].updateUI();			
			}
		}			
	}
	
	private void crearTiempo(){
		tiempo.setFont(new Font("Monospaced", Font.BOLD, 20));
		tiempo.setText(formaTiempo());
	}
	
	private String formaTiempo(){
		int num = tab.obtTiempo();
		int minutos = num/60;
		int segundos = num-minutos*60;
		String min;
		String seg;
		if(minutos < 10){
			min = '0'+String.valueOf(minutos);
		}else{
			min = String.valueOf(minutos);
		}
		if(segundos < 10){
			seg = '0'+String.valueOf(segundos);
		}else{
			seg = String.valueOf(segundos);
		}
		return min + ':' + seg;
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
		String num;
		for(int i = 0; i < MAX; i++){
			for (int j = 0; j < MAX; j++){
				if(tab.obtValorCasilla(i, j) == 0){
					num = " ";
				}else{
					num = String.valueOf(tab.obtValorCasilla(i, j));
				}
				cajas[i][j] = crearJLabel(i, j, num);
							
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
					todosLosNumeros(e.getKeyChar());
					if(activado[0]>=0){
						tab.asgValor(activado[0], activado[1],(int)(num-'0'));
					}
				}
				
			}
			
		};
	}
	
	private void todosLosNumeros(int k){
		boolean[][] casillaNum = tab.todosLosNumeros(k);
		if(k != 0){
			for(int i=0; i<MAX; i++){
				for(int j=0; j<MAX; j++){
					if(activado[0]!=i || activado[1]!=j){
						if(casillaNum[i][j]){
							cajas[i][j].setBackground(Color.yellow);
							cajas[i][j].setOpaque(true);
							cajas[i][j].updateUI();
						}else{
							if(cajas[i][j].getBackground() == Color.yellow){
								cajas[i][j].setOpaque(false);
								cajas[i][j].updateUI();
							}
						}
					}
				}
			}
		}
				
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
		if(tab.esValorInicial(pI, pJ)){
			j.setForeground(Color.red);
		}else{

			j.addKeyListener(keyListener);
		}
		MouseListener mouseListener1 = new MouseListener(){

			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!tab.esValorInicial(pI, pJ)){
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
				}else{
					activado[0] = -1;
					activado[1] = -1;
					desactivar();
				}
				todosLosNumeros(tab.obtValorCasilla(pI, pJ));
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
				cajas[i][j].setOpaque(false);
				cajas[i][j].updateUI();
			}
		}
	}	
	
	private void modificar(int pI, int pJ){
		String texto="";

		if(tab.obtListaNotas(pI, pJ).length != 0){
			texto = "<html>";
			for(int k=0; k<MAX; k++){
				if(Character.isDigit(tab.obtListaNotas(pI, pJ)[k])){
					texto += tab.obtListaNotas(pI, pJ)[k];
				}else{
					texto += "&nbsp;";
				}
				if(k!=2 && k!= 5 && k!= 8){
					texto += "&nbsp;"; 
				}else if(k!=8){
					texto += "<br/>";
				}else{
					texto += "</html>";
				}
			}
			cajas[pI][pJ].setText(texto);
			cajas[pI][pJ].setFont(new Font("Arial",Font.ITALIC,8));
			cajas[pI][pJ].setVerticalAlignment(HEIGHT/2);
		}else{
			if(tab.obtValorCasilla(pI, pJ) == 0){
				texto = " ";
			}else{
				texto = String.valueOf(tab.obtValorCasilla(pI, pJ)) ;				
			}
			cajas[pI][pJ].setText(texto);
			cajas[pI][pJ].setFont(new Font("Monospaced",Font.BOLD,20));

			cajas[pI][pJ].setVerticalAlignment(HEIGHT/4);
		}
	}
	
	private void getBotones(){
		getBtnGuide();
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
						todosLosNumeros(numero.charAt(0));
						if(activado[0]>=0){
							tab.asgValor(activado[0], activado[1],(int)(numero.charAt(0)-'0'));
						}
					}
					
				});
			}
		}
	}
	
	private JButton getBtn1() {
		if(btn1 == null){
			btn1 = new JButton("Correcto");
			sur.add(btn1);
			btn1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					desactivar();
					boolean[][] casillasFallo =	tab.comprobarCorrecto();
					if(casillasFallo.length == 0){
						hiloCelebracion();
						vnt = new VentanaFinSudoku();

						setEnabled(false);
						
						//tab.terminar();
					}else{
						mostrarErrores(casillasFallo);
						getIntentos();
						activado[0]=-1;
						activado[1]=-1;
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
			btn2 = new JButton("C");
			sur.add(btn2);
		}
		btn2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < MAX; i++){
					for(int j = 0; j<MAX;j++){
						if(activado[0]>=0 && cajas[i][j].getBackground()!=Color.blue){
							tab.borrarNumero(activado[0], activado[1]);
							
						}
					}
				}
			}
		});
		return btn2;
		
	}
	private JButton getBtn3() {
		if(btn3 == null){
			btn3 = new JButton("Elimin Values");
			sur.add(btn3);
		}
		btn3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(Sesion.obtSesion().obtPistas() > 0){
					tab.eliminateValues();	
				}
			}
		});
		return btn3;
		
	}
	
	private JLabel getIntentos(){
		intentos.setText(" Nº de Intentos: " + tab.obtIntentos());
		return intentos;	
	}
	
	private JLabel getPistas(){
		pistas.setText(" Nº de Pistas: " + Sesion.obtSesion().obtPistas());
		return pistas;	
	}
	
	private JButton getBtnGuide(){
		if(btnGuide == null){
			btnGuide = new JButton("?");
		}
		btnGuide.setBorder(null);
		btnGuide.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getDialog();
			}
		});
		return btnGuide;	
	}
	
	private void getDialog(){
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 5;
		
		JLabel correcto = new JLabel("Casillas correctas");
		correcto.setForeground(new Color(0x0095FF));
		JLabel error = new JLabel("Casillas erróneas");
		error.setForeground(Color.red);
		JLabel seleccion = new JLabel("Casilla seleccionada");
		seleccion.setForeground(Color.green);
		JLabel amarillo = new JLabel("Casillas con número seleccionado");
		amarillo.setForeground(Color.yellow);
		JButton boton = new JButton("Cerrar");
		
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialogGuide.dispose();
			}
			
		});
		
		dialogGuide = new JDialog();
		dialogGuide.setSize(300,200);
		dialogGuide.setModal(false);
		dialogGuide.setVisible(true);
		dialogGuide.setLocationRelativeTo(this);
		dialogGuide.setTitle("GUIDE");
		
		dialogGuide.setLayout(new GridBagLayout());
		dialogGuide.getContentPane().setBackground(new Color(0x585858));
	
		dialogGuide.add(seleccion,csTexto);
		csTexto.gridy = 1;
		dialogGuide.add(amarillo,csTexto);
		csTexto.gridy = 2;
		dialogGuide.add(correcto,csTexto);
		csTexto.gridy = 3;
		dialogGuide.add(error,csTexto);
		csTexto.gridy = 4;
		dialogGuide.add(boton,csBoton);
	}
	
	private void ocultar(){
		for(int i = 0; i<MAX; i++){
			for(int j = 0; j<MAX; j++){
				cajas[i][j].setText("");
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		for(int j = 0; j<MAX; j++){
			for(int i=0; i<MAX; i++){
				modificar(i,j);
			}
		}
		tiempo.setText(formaTiempo());
		getPistas();
		if(vnt!= null && !vnt.isDisplayable()){
			dispose = true;
			dispose();
		}
		if(vnt == null){
			if((tab.obtIntentos()==0) || (tab.obtTiempo()==0 && tab.obtTiempoAjustado())){
				setEnabled(false);
				tab.reiniciar();
				vnt = new VentanaFinSudoku();
			}
		}
	}
	
	public static void main(String arg[]) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		Tablero tb = Tablero.obtTablero();
		Sudoku sud;
		ResultSet res = GestorBD.getGestorBD().Select("SELECT Sudoku FROM Sudokus WHERE Identificador = 1400");
		try {
			res.next();
			byte[] b = res.getBytes("Sudoku");
			ByteArrayInputStream byteArray = new ByteArrayInputStream(b);
			ObjectInputStream oos = new ObjectInputStream(byteArray);
			sud = (Sudoku) oos.readObject();
			tb.inicializar(sud, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new VentanaTablero();
	}

}