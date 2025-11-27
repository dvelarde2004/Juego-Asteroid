package Controlador;

import Modelo.Ball;
import Modelo.Nave;
import Vista.BallVista;

import java.util.List;

public class BallController implements Runnable {
    private Ball ball;
    private BallVista view;
    private List<Ball> balls;
    private Nave nave; // Para que rebote con la nave
    private boolean running;
    private int ajusteVelocidadGlobal = 0;
    private int ajusteTamañoGlobal = 0;

    // Constructor normal, le paso todo lo que necesita
    public BallController(Ball ball, BallVista view, List<Ball> balls, Nave nave) {
        this.ball = ball;
        this.view = view;
        this.balls = balls;
        this.nave = nave;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            ball.movimiento(); // Muevo la bola

            rebotarContraNave(); // Compruebo si choca con la nave

            // Miramos colisiones con otras bolas
            for (Ball otra : balls) {
                if (otra != ball && ball.colisionaCon(otra) && ball.hashCode() < otra.hashCode()) {
                    ball.intercambianVelocidad(otra); // Cambian de direccion
                }
            }

            view.draw(balls); // Actualizo la pantalla

            try {
                Thread.sleep(16); // Para que vaya fluido
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Hago que la bola rebote si toca la nave
    private void rebotarContraNave() {
        if (nave != null && nave.colisionaCon(ball)) {
            ball.setVx(-ball.getVx()); // Cambio direccion X
            ball.setVy(-ball.getVy()); // Cambio direccion Y
            ball.movimiento(); // Un empujon para que no se quede pegada
        }
    }

    // Cambio la velocidad de todas las bolas
    public void ajustarVelocidadGlobal(int ajuste) {
        this.ajusteVelocidadGlobal = ajuste;
        for (Ball b : balls) {
            b.ajustarVelocidadDesdeBase(ajuste);
        }
        view.refrescar(balls);
    }

    // Cambio el tamaño de todas las bolas
    public void ajustarTamañoGlobal(int ajuste) {
        this.ajusteTamañoGlobal = ajuste;
        for (Ball b : balls) {
            b.ajustarTamañoDesdeBase(ajuste);
        }
        view.refrescar(balls);
    }

    // Añado una bola nueva
    public void añadirBola(Ball nuevaBola) {
        // Le aplico los ajustes actuales
        nuevaBola.ajustarVelocidadDesdeBase(ajusteVelocidadGlobal);
        nuevaBola.ajustarTamañoDesdeBase(ajusteTamañoGlobal);

        balls.add(nuevaBola);
        // Creo controlador nuevo para la bola nueva
        BallController nuevoController = new BallController(nuevaBola, view, balls, nave);
        new Thread(nuevoController).start();
        view.refrescar(balls);
    }

    // Para parar el hilo
    public void stop() {
        running = false;
    }
}