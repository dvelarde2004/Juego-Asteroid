package Controlador;

import Modelo.Nave;
import Vista.NaveVista;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NaveController implements Runnable {
    private Nave nave;
    private NaveVista vista;
    private boolean wPresionado, aPresionado, sPresionado, dPresionado;
    private boolean running;

    public NaveController(Nave nave, NaveVista vista) {
        this.nave = nave;
        this.vista = vista;
        this.running = true;
        setupKeyListeners();
    }

    @Override
    public void run() {
        while (running) {
            // ✅ ACTUALIZAR MOVIMIENTO CONTINUO
            actualizarMovimiento();

            // ✅ ACTUALIZAR VISTA (para rotación suave)
            vista.actualizarNave();

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupKeyListeners() {
        vista.setFocusable(true);
        vista.requestFocusInWindow();

        vista.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> wPresionado = true;
                    case KeyEvent.VK_A -> aPresionado = true;
                    case KeyEvent.VK_S -> sPresionado = true;
                    case KeyEvent.VK_D -> dPresionado = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> wPresionado = false;
                    case KeyEvent.VK_A -> aPresionado = false;
                    case KeyEvent.VK_S -> sPresionado = false;
                    case KeyEvent.VK_D -> dPresionado = false;
                }
            }
        });
    }

    private void actualizarMovimiento() {
        if (wPresionado) nave.moverArriba();
        if (sPresionado) nave.moverAbajo();
        if (aPresionado) nave.moverIzquierda();
        if (dPresionado) nave.moverDerecha();
    }

    public void stop() {
        running = false;
    }
}