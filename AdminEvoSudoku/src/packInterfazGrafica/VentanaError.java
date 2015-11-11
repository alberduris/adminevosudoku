package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class VentanaError extends JDialog {

		  private JPanel panel = new JPanel();
		  private BorderLayout borderLayout1 = new BorderLayout();
		  private JPanel panelOk = new JPanel();
		  private JButton botonOk = new JButton();
		  private Border border1;
		  private Border border2;
		  private TitledBorder titledBorder1;
		  private JTextArea textMensaje = new JTextArea();
		  private Border border3;
		  private Controlador controlador;
		  
		  private static VentanaError miVentana;

		  public static VentanaError obtVentanaError()
		  {
		      if (miVentana == null)
		      {
		          miVentana = new VentanaError();
		      }
		      return miVentana;
		  }

		  private VentanaError(Frame frame, String title, boolean modal) {
		    super(frame, title, modal);
		    try {
		      jbInit();
		      pack();
		    }
		    catch(Exception ex) {
		      ex.printStackTrace();
		    }
		  }

		  private VentanaError() {
		    this(null, "¡¡ERROR!!", true);
		  }
		  void jbInit() throws Exception {
		    controlador = new Controlador(); 
		    border1 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(156, 156, 158)),BorderFactory.createEmptyBorder(10,10,10,10));
		    border2 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white,1),BorderFactory.createEmptyBorder(10,10,10,10));
		    titledBorder1 = new TitledBorder("");
		    border3 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(156, 156, 158)),BorderFactory.createEmptyBorder(10,10,10,10));
		    panel.setLayout(borderLayout1);
		    botonOk.setBorder(BorderFactory.createRaisedBevelBorder());
		    botonOk.setPreferredSize(new Dimension(51, 25));
		    botonOk.setText("OK");
		    botonOk.addActionListener((ActionListener) controlador);
		    textMensaje.setEditable(false);
		    textMensaje.setPreferredSize(new Dimension(200, 80));
		    textMensaje.setBackground(SystemColor.text);
		    textMensaje.setFont(new java.awt.Font("Dialog", 0, 12));
		    textMensaje.setBorder(border3);
		    textMensaje.setWrapStyleWord(true);
		    textMensaje.setLineWrap(true);
		    getContentPane().add(panel);
		    panel.add(panelOk,  BorderLayout.SOUTH);
		    panelOk.add(botonOk, null);
		    panel.add(textMensaje,  BorderLayout.CENTER);
		  }




		  public void mostrar(String m) {
		    textMensaje.setText(m);
		    this.setModal(true);
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
		  }
		  
		  private class Controlador implements ActionListener
		  {

		    /* (non-Javadoc)
		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		     */
		    @Override
		    public void actionPerformed(ActionEvent pE)
		    {
			setVisible(false);
			
		    }
		      
		  }

}