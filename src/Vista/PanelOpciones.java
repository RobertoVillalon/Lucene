package Vista;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalBorders;

public class PanelOpciones extends JPanel{
    Controlador ctrl = new Controlador();
    public static JPanel panelSeleccion = new JPanel(),panelDiferenciacion = new JPanel();
    public static JFileChooser selector;
    public static FileFilter filtro = new FileNameExtensionFilter("Selecciona una carpeta",".");
    public static JLabel labelSeleccion = new JLabel("    Click para Seleccionar Directorio    ");
    public static char Cpdf = 'W',Cword = 'W',Cppt = 'W',Ctxt = 'W';
    public static JButton imgWord = new JButton(),imgPpt = new JButton(),imgPdf = new JButton(),imgTxt = new JButton();
    public static URL direccionPdf = Vista.class.getResource("res/pdf.png"),direccionDoc = Vista.class.getResource("res/doc.png"),direccionPpt = Vista.class.getResource("res/powerpoint.png"),direccionTxt = Vista.class.getResource("res/txt.png");
    public static URL direccionPdfNegro = Vista.class.getResource("res/pdfnegro.png"),direccionDocNegro = Vista.class.getResource("res/docnegro.png"),direccionPptNegro = Vista.class.getResource("res/powerpointnegro.png"),direccionTxtNegro = Vista.class.getResource("res/txtnegro.png");
    public static ImageIcon pdf = new ImageIcon(direccionPdf),ppt = new ImageIcon(direccionPpt),doc = new ImageIcon(direccionDoc),txt = new ImageIcon(direccionTxt);
    public static ImageIcon pdfNegro = new ImageIcon(direccionPdfNegro),pptNegro = new ImageIcon(direccionPptNegro),docNegro = new ImageIcon(direccionDocNegro),txtNegro = new ImageIcon(direccionTxtNegro);
    
    public PanelOpciones(){
        IniciarPanel();
        IniciarPanelSeleccion();
        IniciarPanelDiferenciacion();
        ConectaControlador();
    }

    private void IniciarPanel(){
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(0, 50));
        setBackground(Color.WHITE);
    }

    private void IniciarPanelSeleccion() {
        panelSeleccion.setBackground(Color.WHITE);
        panelSeleccion.setLayout(new BorderLayout());
        panelSeleccion.setBorder(new MetalBorders.PaletteBorder());
        labelSeleccion.setHorizontalAlignment(JLabel.CENTER);
        panelSeleccion.add(labelSeleccion,BorderLayout.CENTER);
        add(panelSeleccion,BorderLayout.WEST);
    }

    private void IniciarPanelDiferenciacion() {
        panelDiferenciacion.setBackground(Color.WHITE);
        panelDiferenciacion.setBorder(new MetalBorders.PaletteBorder());
        panelDiferenciacion.setLayout(new GridLayout(1, 0));
        IniciarLabelsPanelDiferenciacion();
        add(panelDiferenciacion,BorderLayout.CENTER);
    }
    
    private void IniciarLabelsPanelDiferenciacion() {
        imgPdf.setIcon(new ImageIcon(pdf.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
        imgPdf.setBackground(Color.WHITE);
        imgPdf.setText("Documento PDF");
        imgPdf.setActionCommand("imgPdf");
        
        imgPpt.setIcon(new ImageIcon(ppt.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
        imgPpt.setBackground(Color.WHITE);
        imgPpt.setText("Documento PPT");
        imgPpt.setActionCommand("imgPpt");
        
        imgWord.setIcon(new ImageIcon(doc.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
        imgWord.setBackground(Color.WHITE);
        imgWord.setText("Documento Word");
        imgWord.setActionCommand("imgWord");
        
        imgTxt.setIcon(new ImageIcon(txt.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
        imgTxt.setBackground(Color.WHITE);
        imgTxt.setText("Texto Plano");
        imgTxt.setActionCommand("imgTxt");
        
        panelDiferenciacion.add(imgTxt);
        panelDiferenciacion.add(imgPdf);
        panelDiferenciacion.add(imgPpt);
        panelDiferenciacion.add(imgWord);
    }
    
    private void ConectaControlador() {
        panelSeleccion.addMouseListener(ctrl);
        imgPdf.addActionListener(ctrl);
        imgPpt.addActionListener(ctrl);
        imgTxt.addActionListener(ctrl);
        imgWord.addActionListener(ctrl);
    }
}
