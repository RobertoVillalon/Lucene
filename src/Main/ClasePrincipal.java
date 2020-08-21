package Main;

import Vista.Vista;

public class ClasePrincipal {
    public static void main(String [] args){
        Hilo hilo = new Hilo();
        javax.swing.SwingUtilities.invokeLater(hilo);
    }
}

class Hilo implements Runnable{
    Vista vista;
    
    @Override
    public void run() {
        vista = new Vista();
    }
    
}
