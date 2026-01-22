package cl.rvillalon.Controlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import cl.rvillalon.Modelo.Modelo;
import cl.rvillalon.Vista.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

public class Controlador implements ActionListener,KeyListener,WindowListener,MouseListener{
    Modelo modelo = new Modelo();
    String palabra = "";
    
    @Override
    public void actionPerformed(ActionEvent ae){
        String accion = ae.getActionCommand();
        
        switch(accion){
            case "guardarTexto":
                PanelOpciones.selector = new JFileChooser();
                PanelOpciones.selector.setFileFilter(PanelOpciones.filtro);
                PanelOpciones.selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                PanelOpciones.selector.showOpenDialog(PanelOpciones.selector);
                
                try{
                    String ruta2 = PanelOpciones.selector.getSelectedFile().getAbsolutePath();
                    Modelo.indexDir = ruta2;
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Ninguna carpeta ha sido selecionada","Advertencia",2);
                };
            break;
            case "imgPdf":
                if(PanelOpciones.Cpdf == 'W'){
                    PanelOpciones.imgPdf.setIcon(new ImageIcon(PanelOpciones.pdfNegro.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
                    PanelOpciones.Cpdf = 'B';
                }
                else{
                    PanelOpciones.imgPdf.setIcon(new ImageIcon(PanelOpciones.pdf.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
                    PanelOpciones.Cpdf = 'W';
                }
            break;
            case "imgPpt":
                if(PanelOpciones.Cppt== 'W'){
                    PanelOpciones.imgPpt.setIcon(new ImageIcon(PanelOpciones.pptNegro.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
                    PanelOpciones.Cppt = 'B';
                }
                else{
                    PanelOpciones.imgPpt.setIcon(new ImageIcon(PanelOpciones.ppt.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
                    PanelOpciones.Cppt = 'W';
                }
            break;
            case "imgWord":
                if(PanelOpciones.Cword == 'W'){
                    PanelOpciones.imgWord.setIcon(new ImageIcon(PanelOpciones.docNegro.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
                    PanelOpciones.Cword = 'B';
                }
                else{
                    PanelOpciones.imgWord.setIcon(new ImageIcon(PanelOpciones.doc.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
                    PanelOpciones.Cword = 'W';
                }
            break;
            case "imgTxt":
                if(PanelOpciones.Ctxt == 'W'){
                    PanelOpciones.imgTxt.setIcon(new ImageIcon(PanelOpciones.txtNegro.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
                    PanelOpciones.Ctxt = 'B';
                }
                else{
                    PanelOpciones.imgTxt.setIcon(new ImageIcon(PanelOpciones.txt.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH)));
                    PanelOpciones.Ctxt = 'W';
                }
            break;
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) throws RuntimeException{
        int key = (int)ke.getKeyChar();
        
        if(!Modelo.dataDir.equals("")){
            ReiniciarVariables();
        }
        
        if(key == 10){       
            if(PanelIndexacion.fieldBusqueda.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Mensaje no valido o campo de texto vacio","Error",2);
            }
            else{
                PanelIndexacion.fieldBusqueda.setBackground(Color.WHITE);
                palabra = PanelIndexacion.fieldBusqueda.getText();
                PanelVisualizacion.tablaPaneles.removeAll();
                
                try {
                    modelo.CrearIndice();
                    if(modelo.numeroPalabrasIndexadas == -1){
                    }
                    else{
                        PanelInformacion.labelArchivosIndexados.setText("Archivos Indexados: ");
                        PanelInformacion.labelRespuestaArchivosIndexados.setText(""+modelo.numeroPalabrasIndexadas);
                        PanelInformacion.labelTiempoIndexado.setText("Tiempo de Indexado: ");
                        PanelInformacion.labelRespuestaTiempoIndexado.setText(""+modelo.tiempoIndexado+" ms");
                        
                        modelo.Buscar(palabra);
                    
                        if(modelo.numeroPalabrasEncontradas == 0){
                            PanelInformacion.labelVecesEncontrada.setText("N° Repeticiones palabra: ");
                            PanelInformacion.labelRespuestaVecesEncontrada.setText("Palabra No Encontrada");
                            PanelInformacion.labelTiempoBusqueda.setText("Tiempo de Busqueda: ");
                            PanelInformacion.labelRespuestaTiempoBusqueda.setText("Error");
                            String sugerencia = modelo.buscador.Sugerencia(PanelIndexacion.fieldBusqueda.getText());
                            if(!sugerencia.equals("")){
                                PanelIndexacion.fieldBusqueda.setBackground(Color.GREEN);
                                PanelIndexacion.fieldBusqueda.setText(sugerencia);

                            }
                        }
                        else{
                            PanelInformacion.labelVecesEncontrada.setText("N° Archivos con palabra: ");
                            PanelInformacion.labelRespuestaVecesEncontrada.setText(""+modelo.numeroPalabrasEncontradas);
                            PanelInformacion.labelTiempoBusqueda.setText("Tiempo de Busqueda: ");
                            PanelInformacion.labelRespuestaTiempoBusqueda.setText(""+modelo.tiempoBusqueda+" ms");
                        }
                        
                        for(int i = 0; i < modelo.rutaDocumentos.length; i++){
                            JPanel panel = null;JTextPane area = null;JScrollPane scroll = null;
                            
                            panel = PanelVisualizacion.CrearJPanel(panel);
                            area = PanelVisualizacion.CrearJTextPane(area);
                            scroll = PanelVisualizacion.CrearJScroll(scroll);
                            area.setEditable(false);
                            scroll.setViewportView(area);
                            PanelVisualizacion.areas.add(i,area);
                            area.addMouseListener(this);
                            String titulo;
                            if(modelo.rutaDocumentos[i] != null){
                                File archivo = new File(modelo.rutaDocumentos[i]);
                                area.setText(""+modelo.textos[i]);
                                panel.add(scroll,BorderLayout.CENTER);
                                titulo = modelo.CortarRuta(modelo.rutaDocumentos[i]);
                                
                                if(titulo.endsWith(".pptx") || titulo.endsWith(".ppt")){
                                    if(PanelOpciones.Cppt == 'B')
                                        PanelVisualizacion.tablaPaneles.add(""+titulo,panel);
                                    //Archivos PPT
                                }
                                else{
                                    if(titulo.endsWith(".docx") || titulo.endsWith(".doc")){
                                        if(PanelOpciones.Cword == 'B')
                                        PanelVisualizacion.tablaPaneles.add(""+titulo,panel);
                                       //Archivos Word
                                    }
                                    else{
                                        if(titulo.endsWith(".pdf")){
                                            if(PanelOpciones.Cpdf == 'B')
                                            PanelVisualizacion.tablaPaneles.add(""+titulo,panel);
                                            //Archivos PDF
                                        }
                                        else{
                                            if(PanelOpciones.Ctxt == 'B')
                                            PanelVisualizacion.tablaPaneles.add(""+titulo,panel);
                                            //Archivos de Texto
                                        }
                                    }
                                } 
                            }
                        }
                    }
                    
                    }catch (IOException | ParseException | InvalidTokenOffsetsException ex) {
                        ex.getStackTrace();
                    }       
            }
        }
    }
    
    private void ReiniciarVariables() {
        LimpiarInformacion();
    }

    @Override
    public void keyPressed(KeyEvent ke){
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void windowOpened(WindowEvent we) {    }

    @Override
    public void windowClosing(WindowEvent we) {
        LimpiarInformacion();
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {    }

    @Override
    public void windowDeiconified(WindowEvent we) {    }

    @Override
    public void windowActivated(WindowEvent we) {    }

    @Override
    public void windowDeactivated(WindowEvent we) {}

    private void LimpiarInformacion(){
        File[] directorioArchivosBasura = new File(Modelo.indexDir).listFiles();
        File[] directorioArchivosRecuperacion = new File(Modelo.dataDir).listFiles();
        String[] extensiones = {".doc",".ppt","docx",".pptx",".pdf"};
     
        for(File ficheros : directorioArchivosBasura){
            ficheros.delete();
        }
        
        if(Modelo.dataDir.equals("")){
            return;
        }
       
        for(File archivos : directorioArchivosRecuperacion){
            for(String extension : extensiones){
                if(archivos.getName().toLowerCase().endsWith(extension+".txt")){
                    archivos.delete();
                }                
            }
        }
    }   

    @Override
    public void mouseClicked(MouseEvent me){
        
        if(me.getSource() == PanelOpciones.panelSeleccion && me.getButton() == 1){
            PanelOpciones.panelSeleccion.setBackground(Color.WHITE);
            PanelOpciones.selector = new JFileChooser();
            PanelOpciones.selector.setFileFilter(PanelOpciones.filtro);
            PanelOpciones.selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            PanelOpciones.selector.showOpenDialog(PanelOpciones.selector);

            try{
                String ruta = PanelOpciones.selector.getSelectedFile().getAbsolutePath();
                Modelo.dataDir = "";
                Modelo.dataDir = ruta;
                PanelInformacion.labelRespuestaDatosCargados.setText("Si");
                PanelInformacion.labelRespuestaDatosCargados.setForeground(Color.BLUE);
            }catch(NullPointerException e){
                JOptionPane.showMessageDialog(null, "Ninguna carpeta ha sido selecionada","Advertencia",2);
            }            
        }
        else{
            if(me.getButton() == 3){
                for(int i = 0; i < PanelVisualizacion.areas.size(); i++){
                    if(PanelVisualizacion.areas.get(i) == me.getSource()){
                        String textoContenido = PanelVisualizacion.areas.get(i).getText();
                        String palabraClave = palabra;
                        
                    }
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me){
        if(me.getSource() == PanelOpciones.panelSeleccion){
            if(Modelo.dataDir.equals("")){
                PanelOpciones.labelSeleccion.setText("    Directorio no Seleccionado    ");
                PanelOpciones.panelSeleccion.setBackground(Color.RED);
            }
            else{
               PanelOpciones.labelSeleccion.setText("    Directorio Seleccionado    ");
               PanelOpciones.panelSeleccion.setBackground(Color.GREEN); 
            }            
        }


    }

    @Override
    public void mouseExited(MouseEvent me) {
        if(me.getSource() == PanelOpciones.panelSeleccion){
            PanelOpciones.labelSeleccion.setText("    Click para Seleccionar Directorio    ");
            PanelOpciones.panelSeleccion.setBackground(Color.WHITE);    
        }

    }

}
