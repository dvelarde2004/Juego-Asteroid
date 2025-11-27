package Controlador;

import Modelo.Ball;
import Modelo.Nave;
import Vista.BallVista;

import java.util.List;

public class BallController implements Runnable {
    private Ball ball;
    private BallVista view;
    private List<Ball> balls;
    private Nave nave; // ✅ NUEVO: Referencia a la nave
    private boolean running;
    private int ajusteVelocidadGlobal = 0;
    private int ajusteTamañoGlobal = 0;

    // ✅ CONSTRUCTOR ACTUALIZADO (añadir parámetro nave)
    public BallController(Ball ball, BallVista view, List<Ball> balls, Nave nave) {
        this.ball = ball;
        this.view = view;
        this.balls = balls;
        this.nave = nave; // ✅ NUEVO
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            ball.movimiento();

            // ✅ NUEVO: Verificar colisión con nave
            rebotarContraNave();

            // CÓDIGO EXISTENTE (no tocar)
            for (Ball otra : balls) {
                if (otra != ball && ball.colisionaCon(otra) && ball.hashCode() < otra.hashCode()) {
                    ball.intercambianVelocidad(otra);
                }
            }

            view.draw(balls);

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // ✅ NUEVO MÉTODO: Rebote contra nave
    private void rebotarContraNave() {
        if (nave != null && nave.colisionaCon(ball)) {
            // Física simple de rebote - invertir dirección
            ball.setVx(-ball.getVx());
            ball.setVy(-ball.getVy());

            // Pequeño empuje para evitar stuck
            ball.movimiento();
        }
    }

    // MÉTODOS EXISTENTES (sin cambios)
    public void ajustarVelocidadGlobal(int ajuste) {
        this.ajusteVelocidadGlobal = ajuste;
        for (Ball b : balls) {
            b.ajustarVelocidadDesdeBase(ajuste);
        }
        view.refrescar(balls);
    }

    public void ajustarTamañoGlobal(int ajuste) {
        this.ajusteTamañoGlobal = ajuste;
        for (Ball b : balls) {
            b.ajustarTamañoDesdeBase(ajuste);
        }
        view.refrescar(balls);
    }

    public void añadirBola(Ball nuevaBola) {
        // Aplicar ajustes globales actuales a la nueva bola
        nuevaBola.ajustarVelocidadDesdeBase(ajusteVelocidadGlobal);
        nuevaBola.ajustarTamañoDesdeBase(ajusteTamañoGlobal);

        balls.add(nuevaBola);
        BallController nuevoController = new BallController(nuevaBola, view, balls, nave); // ✅ Actualizado
        new Thread(nuevoController).start();
        view.refrescar(balls);
    }

    public void stop() {
        running = false;
    }
}