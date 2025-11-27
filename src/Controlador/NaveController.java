package Controlador;

import Modelo.Nave;
import Vista.NaveVista;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class NaveController implements Runnable {
    private Nave nave;
    private NaveVista vistaNave;
    private boolean wPressed = false, aPressed = false, sPressed = false, dPressed = false;

    public NaveController(Nave nave, NaveVista vistaNave) {
        this.nave = nave;
        this.vistaNave = vistaNave;
        configurarControlesGlobales();
    }

    private void configurarControlesGlobales() {
        // ✅ USAR KEY BINDINGS QUE FUNCIONAN SIN FOCUS
        JPanel panel = vistaNave;

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "moverArriba");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "moverAbajo");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "moverIzquierda");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "moverDerecha");

        panel.getActionMap().put("moverArriba", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setY(nave.getY() - 10);
                nave.setDireccion(0);
                vistaNave.actualizarNave();
                System.out.println("⬆️ Moviendo ARRIBA");
            }
        });

        panel.getActionMap().put("moverAbajo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setY(nave.getY() + 10);
                nave.setDireccion(2);
                vistaNave.actualizarNave();
                System.out.println("⬇️ Moviendo ABAJO");
            }
        });

        panel.getActionMap().put("moverIzquierda", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setX(nave.getX() - 10);
                nave.setDireccion(3);
                vistaNave.actualizarNave();
                System.out.println("⬅️ Moviendo IZQUIERDA");
            }
        });

        panel.getActionMap().put("moverDerecha", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setX(nave.getX() + 10);
                nave.setDireccion(1);
                vistaNave.actualizarNave();
                System.out.println("➡️ Moviendo DERECHA");
            }
        });

        System.out.println("✅ Controles globales configurados (W,A,S,D)");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}