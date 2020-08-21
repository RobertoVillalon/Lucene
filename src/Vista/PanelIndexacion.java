package Vista;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalBorders;

public class PanelIndexacion extends JPanel{
    Controlador ctrl = new Controlador();
    JLabel labelBusqueda = new JLabel();
    public static JTextField fieldBusqueda = new JTextField();
    
    public PanelIndexacion() {
        IniciarPanel();
        IniciarLabels();
        IniciarCamposTexto();
        AgregarComponentes();
        ConectaControlador();
    } 

    private void IniciarPanel(){
        setBackground(Color.WHITE);
        setLayout(new GridLayout(1, 0));
        setBorder(new TitledBorder(new MetalBorders.PaletteBorder(),"Indexador de palabras"));
        setPreferredSize(new Dimension(0, 100));
    }
    
    private void IniciarLabels(){
        labelBusqueda.setText("Busca una palabra: ");
        labelBusqueda.setToolTipText("Presiona ENTER para indexar la palabra");
        labelBusqueda.setHorizontalAlignment(JLabel.CENTER);
    }
    
    private void IniciarCamposTexto(){
        fieldBusqueda.setHorizontalAlignment(JLabel.CENTER);
        fieldBusqueda.setActionCommand("campoBusqueda");
    }
    
    private void AgregarComponentes(){
        add(labelBusqueda);
        add(fieldBusqueda);
    }
    
    private void ConectaControlador(){
        fieldBusqueda.addKeyListener(ctrl);
    }
    
}
