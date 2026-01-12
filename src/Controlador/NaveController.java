package Controlador;

import Modelo.Nave;
import Vista.NaveVista;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class NaveController implements Runnable {
    private Nave nave;
    private NaveVista vistaNave;

    // Constructor simplificado - ya no configura controles
    public NaveController(Nave nave, NaveVista vistaNave) {
        this.nave = nave;
        this.vistaNave = vistaNave;
        // Se eliminó configurarControlesGlobales() - ahora está en la vista
    }

    @Override
    public void run() {
        // Solo mantengo el hilo vivo, el movimiento es con teclado desde la vista
        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}