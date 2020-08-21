package Vista;

import Controlador.Controlador;
import java.awt.*;
import javax.swing.*;

public class Vista extends JFrame{
    Controlador ctrl = new Controlador();
    PanelOpciones panelOpciones;
    PanelIndexacion panelIndexacion;
    PanelInformacion panelInformacion;
    PanelVisualizacion panelVisualizacion;
    JPanel panelPrincipal = new JPanel();
    
    public Vista() {
        IniciarVentana();
        IniciarPanelPrincipal();;
        IniciarPaneles();
        ConectaControlador(ctrl);
    }

    private void IniciarVentana() {
        setTitle("Proyecto Lucene 1");
        setSize(600, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void IniciarPanelPrincipal(){
        panelPrincipal.setLayout(new BorderLayout());
        getContentPane().add(panelPrincipal);
    }
    
    private void IniciarPaneles() {
        panelIndexacion = new PanelIndexacion();
        panelInformacion = new PanelInformacion();
        panelVisualizacion = new PanelVisualizacion();
        panelOpciones = new PanelOpciones();
        
        panelPrincipal.add(panelOpciones,BorderLayout.NORTH);
        panelPrincipal.add(panelIndexacion,BorderLayout.SOUTH);
        panelPrincipal.add(panelInformacion,BorderLayout.WEST);
        panelPrincipal.add(panelVisualizacion,BorderLayout.CENTER);
    }
    
    private void ConectaControlador(Controlador ctrl){
        addWindowListener(ctrl);
    }
}
