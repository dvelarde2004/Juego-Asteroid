package Controlador;

import Modelo.Ball;
import Vista.BallVista;

import java.util.List;

public class BallController implements Runnable {
    private Ball ball;
    private BallVista view;
    private List<Ball> balls;
    private boolean running;
    private int ajusteVelocidadGlobal = 0; // ✅ NUEVO: Ajuste global actual
    private int ajusteTamañoGlobal = 0;    // ✅ NUEVO: Ajuste tamaño global actual

    public BallController(Ball ball, BallVista view, List<Ball> balls) {
        this.ball = ball;
        this.view = view;
        this.balls = balls;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            ball.movimiento();

            // Hacer que choquen las bolas entre sí
            for (Ball otra : balls) {
                if (otra != ball && ball.colisionaCon(otra) && ball.hashCode() < otra.hashCode()) {
                    ball.intercambianVelocidad(otra);
                }
            }

            view.draw(balls);

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // ✅ CORREGIDO: Ajustar velocidad SOBRE la base de cada bola
    public void ajustarVelocidadGlobal(int ajuste) {
        this.ajusteVelocidadGlobal = ajuste;
        for (Ball b : balls) {
            b.ajustarVelocidadDesdeBase(ajuste);
        }
        view.refrescar(balls);
    }

    // ✅ CORREGIDO: Ajustar tamaño SOBRE la base de cada bola
    public void ajustarTamañoGlobal(int ajuste) {
        this.ajusteTamañoGlobal = ajuste;
        for (Ball b : balls) {
            b.ajustarTamañoDesdeBase(b.getTamañoBase() + ajuste);
        }
        view.refrescar(balls);
    }

    // ✅ CORREGIDO: Al añadir nueva bola, aplicar ajustes globales actuales
    public void añadirBola(Ball nuevaBola) {
        // Aplicar ajustes globales a la nueva bola
        nuevaBola.ajustarVelocidadDesdeBase(ajusteVelocidadGlobal);
        nuevaBola.ajustarTamañoDesdeBase(nuevaBola.getTamañoBase() + ajusteTamañoGlobal);

        balls.add(nuevaBola);
        BallController nuevoController = new BallController(nuevaBola, view, balls);
        new Thread(nuevoController).start();
        view.refrescar(balls);
    }

    public void stop() {
        running = false;
    }
}