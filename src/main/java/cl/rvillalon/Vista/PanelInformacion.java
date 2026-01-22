package cl.rvillalon.Vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalBorders;

public class PanelInformacion extends JPanel{
    public static JLabel labelDatosCargados = new JLabel("Directorio Cargado: "),labelRespuestaDatosCargados = new JLabel("No");
    public static JLabel labelArchivosIndexados = new JLabel(""),labelRespuestaArchivosIndexados = new JLabel("");
    public static JLabel labelTiempoIndexado = new JLabel(""),labelRespuestaTiempoIndexado = new JLabel("");
    public static JLabel labelVecesEncontrada = new JLabel(""),labelRespuestaVecesEncontrada = new JLabel("");
    public static JLabel labelTiempoBusqueda = new JLabel(""),labelRespuestaTiempoBusqueda = new JLabel("");
    
    public PanelInformacion() {
        IniciarPanel();
        IniciarJLabels();
    } 

    private void IniciarPanel() {
        setBackground(Color.WHITE);
        setLayout(new GridLayout(0, 2));
        setBorder(new TitledBorder(new MetalBorders.PaletteBorder(), "Informacion",2,0));
        setPreferredSize(new Dimension(299,0));
    }

    private void IniciarJLabels(){
        labelDatosCargados.setHorizontalAlignment(JLabel.CENTER);
        labelRespuestaDatosCargados.setForeground(Color.RED);
        labelRespuestaDatosCargados.setHorizontalAlignment(JLabel.CENTER);
        labelArchivosIndexados.setHorizontalAlignment(JLabel.CENTER);
        labelRespuestaArchivosIndexados.setHorizontalAlignment(JLabel.CENTER);
        labelTiempoIndexado.setHorizontalAlignment(JLabel.CENTER);
        labelRespuestaTiempoIndexado.setHorizontalAlignment(JLabel.CENTER);
        labelVecesEncontrada.setHorizontalAlignment(JLabel.CENTER);
        labelRespuestaVecesEncontrada.setHorizontalAlignment(JLabel.CENTER);
        labelTiempoBusqueda.setHorizontalAlignment(JLabel.CENTER);
        labelRespuestaTiempoBusqueda.setHorizontalAlignment(JLabel.CENTER);
        add(labelDatosCargados);
        add(labelRespuestaDatosCargados);
        add(labelArchivosIndexados);
        add(labelRespuestaArchivosIndexados);
        add(labelTiempoIndexado);
        add(labelRespuestaTiempoIndexado);
        add(labelVecesEncontrada);
        add(labelRespuestaVecesEncontrada);
        add(labelTiempoBusqueda);
        add(labelRespuestaTiempoBusqueda);
    }
    
}
