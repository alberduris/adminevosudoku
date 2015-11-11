package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class VentanaSiNo extends JDialog {

		  private boolean respuesta;
		  private JPanel panel = new JPanel();
		  private BorderLayout borderLayout1 = new BorderLayout();
		  private JPanel panelPregunta = new JPanel();
		  private JPanel panelOk = new JPanel();
		  private JButton botonNo = new JButton();
		  private Border border1;
		  private JLabel lblPregunta = new JLabel();
		  private JButton botonSi = new JButton();
		  private Border border2;
		  private Controlador controlador = new Controlador();

		  private static VentanaSiNo miVentana;

		  public static VentanaSiNo obtVentanaSiNo()
		  {
		      if (miVentana==null)
		      {
		          miVentana = new VentanaSiNo();
		      }
		      return miVentana;
		  }


		  private VentanaSiNo(Frame frame, String title, boolean modal) {
		    super(frame, title, modal);
		    try {
		      jbInit();
		      pack();
		    }
		    catch(Exception ex) {
		      ex.printStackTrace();
		    }
		  }

		  private VentanaSiNo() {
		    this(null, "Pregunta", true);
		  }
		  void jbInit() throws Exception {
		    border1 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.white,Color.white,new Color(156, 156, 158),new Color(109, 109, 110)),BorderFactory.createEmptyBorder(2,15,2,15));
		    border2 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(156, 156, 158)),BorderFactory.createEmptyBorder(10,10,10,10));
		    panel.setLayout(borderLayout1);
		    botonNo.setBorder(border1);
		    botonNo.setPreferredSize(new Dimension(51, 25));
		    botonNo.setText("No");
		    botonNo.addActionListener((ActionListener) controlador);
		    botonNo.setActionCommand("botonNo");
		    botonSi.setText("Si");
		    botonSi.addActionListener(controlador);
		    botonSi.setActionCommand("botonSi");
		    botonSi.setPreferredSize(new Dimension(51, 25));
		    botonSi.setBorder(border1);
		    panelPregunta.setBorder(border2);
		    panelOk.add(botonSi, null);
		    getContentPane().add(panel);
		    panel.add(panelPregunta,  BorderLayout.CENTER);
		    panel.add(panelOk,  BorderLayout.SOUTH);
		    panelOk.add(botonNo, null);
		    lblPregunta.setFont(new java.awt.Font("Dialog", 1, 12));
		    lblPregunta.setText("¿Desea seguir jugando?");
		    panelPregunta.add(lblPregunta, null);
		  }

		  public boolean preguntar(String p) {

		    lblPregunta.setText(p);
		    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		    Dimension frameSize = this.getSize();
		    if (frameSize.height > screenSize.height) {
		      frameSize.height = screenSize.height;
		    }
		    if (frameSize.width > screenSize.width) {
		      frameSize.width = screenSize.width;
		    }
		    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		    this.setVisible(true);
		    return respuesta;
		  }

		  private class Controlador implements ActionListener
		  {

			public void actionPerformed(ActionEvent pEvent) {
				if (pEvent.getActionCommand().equalsIgnoreCase("botonNo"))
				{
					respuesta = false;
				    setVisible(false);
				}
				else
				{
					respuesta = true;
				    setVisible(false);	
				}
				
			}
			  
		  }
		  
}
