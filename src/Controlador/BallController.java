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

    // Constructor normal, le paso la bola, la vista, las demas bolas y la nave
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

            // Miramos si choca con otras bolas
            for (Ball otra : balls) {
                if (otra != ball && ball.colisionaCon(otra) && ball.hashCode() < otra.hashCode()) {
                    ball.intercambianVelocidad(otra); // Cambian de direccion
                }
            }

            view.draw(balls); // Actualizo la pantalla

            try {
                Thread.sleep(8); // Un poquito mas rapido para que vaya mejor
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Compruebo si la bola choca con la nave usando los centros
    private void rebotarContraNave() {
        if (nave != null) {
            // Calculo el centro de la bola y de la nave
            int ballCenterX = ball.getX() + ball.getTamaño()/2;
            int ballCenterY = ball.getY() + ball.getTamaño()/2;
            int naveCenterX = nave.getX() + nave.getTamaño()/2;
            int naveCenterY = nave.getY() + nave.getTamaño()/2;

            // Calculo la distancia entre los dos centros
            int distanciaX = ballCenterX - naveCenterX;
            int distanciaY = ballCenterY - naveCenterY;
            double distancia = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);

            // Distancia minima para que choquen (radio bola + radio nave)
            int minDistancia = (ball.getTamaño()/2) + (nave.getTamaño()/2);

            // Si estan mas cerca de lo que deberian, chocan
            if (distancia < minDistancia) {
                // La bola rebota en direccion contraria
                ball.setVx(-ball.getVx());
                ball.setVy(-ball.getVy());

                // La muevo un poco mas para que no se quede pegada
                ball.movimiento();
            }
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

    // Añado una bola nueva al juego
    public void añadirBola(Ball nuevaBola) {
        // Le pongo la velocidad y tamaño actuales
        nuevaBola.ajustarVelocidadDesdeBase(ajusteVelocidadGlobal);
        nuevaBola.ajustarTamañoDesdeBase(ajusteTamañoGlobal);

        // La añado a la lista y creo su controlador
        balls.add(nuevaBola);
        BallController nuevoController = new BallController(nuevaBola, view, balls, nave);
        new Thread(nuevoController).start(); // La pongo en movimiento
        view.refrescar(balls); // Actualizo la pantalla
    }

    // Para parar esta bola si hace falta
    public void stop() {
        running = false;
    }
}