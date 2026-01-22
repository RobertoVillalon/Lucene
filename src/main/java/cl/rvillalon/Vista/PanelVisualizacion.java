package cl.rvillalon.Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalBorders;

public class PanelVisualizacion extends JPanel {
    public static JTabbedPane tablaPaneles = new JTabbedPane();
    public static ArrayList<JTextPane> areas = new ArrayList<JTextPane>();
    
    public PanelVisualizacion() {
        IniciarPanel();
        IniciarJTabbedPane();
    } 

    private void IniciarPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(new TitledBorder(new MetalBorders.PaletteBorder(), "Visualizacion",2,0));
        setPreferredSize(new Dimension(299,0));
    }

    private void IniciarJTabbedPane(){
        tablaPaneles.setBackground(Color.WHITE);
        add(tablaPaneles,BorderLayout.CENTER);
    }
    
    public static JScrollPane CrearJScroll(JScrollPane scroll){
        scroll = new JScrollPane();
        
        return scroll;
    }
    
    public static JPanel CrearJPanel(JPanel panel) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        return panel;
    }
    
    public static JTextPane CrearJTextPane(JTextPane area) {
        area = new JTextPane();
        
        return area;
    }
}
