package Controlador;

import Modelo.Nave;
import Vista.NaveVista;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class NaveController implements Runnable {
    private Nave nave;
    private NaveVista vistaNave;

    // Constructor simple
    public NaveController(Nave nave, NaveVista vistaNave) {
        this.nave = nave;
        this.vistaNave = vistaNave;
        configurarControlesGlobales(); // Configuro los controles
    }

    // Pongo los controles WASD para mover la nave
    private void configurarControlesGlobales() {
        JPanel panel = vistaNave;

        // Uso key bindings que funcionan aunque no tenga el foco
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "moverArriba");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "moverAbajo");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "moverIzquierda");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "moverDerecha");

        // Lo que hace cada tecla
        panel.getActionMap().put("moverArriba", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setY(nave.getY() - 10);
                nave.setDireccion(0); // Arriba
                vistaNave.actualizarNave();
            }
        });

        panel.getActionMap().put("moverAbajo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setY(nave.getY() + 10);
                nave.setDireccion(2); // Abajo
                vistaNave.actualizarNave();
            }
        });

        panel.getActionMap().put("moverIzquierda", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setX(nave.getX() - 10);
                nave.setDireccion(3); // Izquierda
                vistaNave.actualizarNave();
            }
        });

        panel.getActionMap().put("moverDerecha", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setX(nave.getX() + 10);
                nave.setDireccion(1); // Derecha
                vistaNave.actualizarNave();
            }
        });
    }

    @Override
    public void run() {
        // Solo mantengo el hilo vivo, el movimiento es con teclado
        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}