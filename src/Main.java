import Controlador.BallController;
import Modelo.Ball;
import Vista.BallVista;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.awt.Color.*;

public class Main {
    public static void main(String[] args) {
        // Dimensiones del área de movimiento de la pelota (ancho x alto)
        int width = 400;
        int height = 400;

        // Crear un par de pelotas
        Ball ball1 = new Ball(RED, 50, 50, 2, 3, width, height);
        Ball ball2 = new Ball(BLUE, 100, 100, 3, 2, width, height);
        Ball ball3 = new Ball(GREEN, 150, 150, 4, 4, width, height);

        // Crear la vista
        List<Ball> balls = new ArrayList<>(Arrays.asList(ball1, ball2, ball3));
        BallVista vista = new BallVista();

        // Crear los controladores
        BallController controller1 = new BallController(ball1, vista, balls);
        BallController controller2 = new BallController(ball2, vista, balls);
        BallController controller3 = new BallController(ball3, vista, balls);

        // Configurar los listeners de la vista
        vista.setupControllerListeners(controller1, balls, width, height);

        // Iniciar los hilos de los controladores
        new Thread(controller1).start();
        new Thread(controller2).start();
        new Thread(controller3).start();
    }
}