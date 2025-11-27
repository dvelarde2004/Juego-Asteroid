import Vista.BallVista;
import Vista.NaveVista;
import Vista.ControlPanel;
import Controlador.BallController;
import Controlador.NaveController;
import Modelo.Ball;
import Modelo.Nave;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class Main {
    public static void main(String[] args) {
        // Tamaños de la ventana
        int anchoJuego = 800;
        int altoJuego = 600;
        int altoPanel = 120;

        System.out.println("Iniciando juego...");

        // Creo las tres bolas iniciales
        Ball bola1 = new Ball(RED, 50, 50, 2, 3, anchoJuego, altoJuego);
        Ball bola2 = new Ball(BLUE, 100, 100, 3, 2, anchoJuego, altoJuego);
        Ball bola3 = new Ball(GREEN, 150, 150, 4, 4, anchoJuego, altoJuego);

        // Creo la nave
        Nave nave = new Nave(anchoJuego, altoJuego);
        nave.setX(375); // Centro
        nave.setY(500); // Abajo
        nave.setTamaño(50);

        // Lista con todas las bolas
        List<Ball> bolas = new ArrayList<>(List.of(bola1, bola2, bola3));

        // Vista principal donde se dibuja todo
        BallVista vistaPrincipal = new BallVista();
        vistaPrincipal.setBalls(bolas);

        // Vista de la nave que se superpone
        NaveVista vistaNave = new NaveVista(nave);

        // Configuro como se ven las cosas
        vistaPrincipal.setLayout(null);
        vistaPrincipal.add(vistaNave);
        vistaNave.setBounds(nave.getX(), nave.getY(), nave.getTamaño(), nave.getTamaño());

        // Tamaño y color del fondo
        vistaPrincipal.setSize(anchoJuego, altoJuego);
        vistaPrincipal.setBackground(Color.WHITE);
        vistaPrincipal.setOpaque(true);

        // Controladores para mover todo
        BallController controladorBola1 = new BallController(bola1, vistaPrincipal, bolas, nave);
        BallController controladorBola2 = new BallController(bola2, vistaPrincipal, bolas, nave);
        BallController controladorBola3 = new BallController(bola3, vistaPrincipal, bolas, nave);
        NaveController controladorNave = new NaveController(nave, vistaNave);

        // Panel de control con sliders
        ControlPanel panelControl = new ControlPanel();

        // Configuro el boton de añadir bola
        panelControl.setAddBallListener(e -> {
            // Bola nueva con color aleatorio
            Ball nuevaBola = new Ball(
                    new Color(
                            (int)(Math.random() * 255),
                            (int)(Math.random() * 255),
                            (int)(Math.random() * 255)
                    ),
                    (int)(Math.random() * (anchoJuego - 50)),
                    (int)(Math.random() * (altoJuego - 50)),
                    (int)(Math.random() * 5) + 1,
                    (int)(Math.random() * 5) + 1,
                    anchoJuego, altoJuego
            );
            controladorBola1.añadirBola(nuevaBola);
        });

        // Slider de velocidad
        panelControl.velocidadSlider.addChangeListener(e -> {
            int velocidad = panelControl.velocidadSlider.getValue();
            controladorBola1.ajustarVelocidadGlobal(velocidad - 5);
        });

        // Slider de tamaño
        panelControl.tamañoSlider.addChangeListener(e -> {
            int tamaño = panelControl.tamañoSlider.getValue();
            controladorBola1.ajustarTamañoGlobal(tamaño - 30);
        });

        // Creo la ventana principal
        JFrame ventana = new JFrame("Space Invaders con Panel de Control");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(anchoJuego, altoJuego + altoPanel);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);

        // Pongo el juego arriba y el panel abajo
        JPanel contenedorPrincipal = new JPanel(new BorderLayout());
        contenedorPrincipal.add(vistaPrincipal, BorderLayout.CENTER);
        contenedorPrincipal.add(panelControl, BorderLayout.SOUTH);

        ventana.add(contenedorPrincipal);
        ventana.setVisible(true);

        // Doy foco para los controles
        vistaNave.requestFocusInWindow();

        // Arranco todos los hilos
        new Thread(controladorBola1).start();
        new Thread(controladorBola2).start();
        new Thread(controladorBola3).start();
        new Thread(controladorNave).start();

        System.out.println("Juego iniciado!");
    }
}