import Vista.*;
import Controlador.*;
import Modelo.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class Main {
    public static void main(String[] args) {
        // Dimensiones del área de movimiento
        int gameWidth = 800;   // Ancho del área de juego
        int gameHeight = 600;  // Alto del área de juego
        int panelHeight = 120; // Alto del panel de control

        System.out.println("🚀 INICIANDO JUEGO...");

        // Crear las pelotas iniciales
        Ball ball1 = new Ball(RED, 50, 50, 2, 3, gameWidth, gameHeight);
        Ball ball2 = new Ball(BLUE, 100, 100, 3, 2, gameWidth, gameHeight);
        Ball ball3 = new Ball(GREEN, 150, 150, 4, 4, gameWidth, gameHeight);

        // ✅ CREAR LA NAVE
        Nave nave = new Nave(gameWidth, gameHeight);
        nave.setX(375);
        nave.setY(500);
        nave.setTamaño(50);

        System.out.println("📍 Nave creada - Posición: " + nave.getX() + ", " + nave.getY());

        // Crear la vista principal de bolas
        List<Ball> balls = new ArrayList<>(List.of(ball1, ball2, ball3));
        BallVista vistaPrincipal = new BallVista();
        vistaPrincipal.setBalls(balls);

        // ✅ CREAR LA VISTA DE LA NAVE
        NaveVista vistaNave = new NaveVista(nave);
        System.out.println("🖼️ VistaNave creada");

        // ✅ CONFIGURAR SUPERPOSICIÓN
        vistaPrincipal.setLayout(null);
        vistaPrincipal.add(vistaNave);

        // ✅ POSICIONAR LA VISTA DE LA NAVE
        vistaNave.setBounds(nave.getX(), nave.getY(), nave.getTamaño(), nave.getTamaño());
        System.out.println("📐 VistaNave bounds: " + vistaNave.getBounds());

        // ✅ CREAR CONTROLADORES
        BallController controller1 = new BallController(ball1, vistaPrincipal, balls, nave);
        BallController controller2 = new BallController(ball2, vistaPrincipal, balls, nave);
        BallController controller3 = new BallController(ball3, vistaPrincipal, balls, nave);
        NaveController naveController = new NaveController(nave, vistaNave);

        // Configurar listeners (simplificado)
        vistaPrincipal.setFocusable(true);

        // ✅ CONFIGURAR BallVista CORRECTAMENTE
        vistaPrincipal.setSize(gameWidth, gameHeight);
        vistaPrincipal.setBackground(Color.WHITE);
        vistaPrincipal.setOpaque(true);

        // ✅ CREAR PANEL DE CONTROL
        ControlPanel controlPanel = new ControlPanel();

        // ✅ CONFIGURAR LISTENERS DEL PANEL DE CONTROL
        controlPanel.setAddBallListener(e -> {
            Ball nuevaBola = new Ball(
                    new Color(
                            (int)(Math.random() * 255),
                            (int)(Math.random() * 255),
                            (int)(Math.random() * 255)
                    ),
                    (int)(Math.random() * (gameWidth - 50)),
                    (int)(Math.random() * (gameHeight - 50)),
                    (int)(Math.random() * 5) + 1,
                    (int)(Math.random() * 5) + 1,
                    gameWidth, gameHeight
            );
            controller1.añadirBola(nuevaBola);
            System.out.println("⚪ Nueva bola añadida");
        });

        // ✅ CONFIGURAR SLIDERS
        controlPanel.velocidadSlider.addChangeListener(e -> {
            int velocidad = controlPanel.velocidadSlider.getValue();
            controller1.ajustarVelocidadGlobal(velocidad - 5); // Ajuste relativo
            System.out.println("🎚️ Velocidad ajustada: " + velocidad);
        });

        controlPanel.tamañoSlider.addChangeListener(e -> {
            int tamaño = controlPanel.tamañoSlider.getValue();
            controller1.ajustarTamañoGlobal(tamaño - 30); // Ajuste relativo
            System.out.println("📏 Tamaño ajustado: " + tamaño);
        });

        // ✅ CONFIGURAR Y MOSTRAR VENTANA PRINCIPAL
        JFrame ventana = new JFrame("Space Invaders con Panel de Control");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(gameWidth, gameHeight + panelHeight); // ✅ Alto total aumentado
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);

        // ✅ CREAR CONTENEDOR PRINCIPAL CON PANEL ABAJO
        JPanel contenedorPrincipal = new JPanel(new BorderLayout());
        contenedorPrincipal.add(vistaPrincipal, BorderLayout.CENTER);
        contenedorPrincipal.add(controlPanel, BorderLayout.SOUTH); // ✅ PANEL ABAJO

        ventana.add(contenedorPrincipal);
        ventana.setVisible(true);
        System.out.println("🖼️ VENTANA PRINCIPAL HECHA VISIBLE");
        System.out.println("📏 Tamaño ventana: " + ventana.getWidth() + "x" + ventana.getHeight());

        // ✅ DAR FOCO PARA LOS CONTROLES DE NAVE
        vistaNave.requestFocusInWindow();
        System.out.println("🎯 Focus dado a vistaNave");

        // ✅ INICIAR HILOS
        new Thread(controller1).start();
        new Thread(controller2).start();
        new Thread(controller3).start();
        new Thread(naveController).start();

        System.out.println("✅ TODOS LOS HILOS INICIADOS");
        System.out.println("🎮 Controles: W, A, S, D para mover la nave");

        // ✅ VERIFICACIÓN FINAL
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("🔍 VERIFICACIÓN FINAL:");
                System.out.println("   Ventana visible: " + ventana.isVisible());
                System.out.println("   Ventana tamaño: " + ventana.getWidth() + "x" + ventana.getHeight());
                System.out.println("   BallVista tamaño: " + vistaPrincipal.getWidth() + "x" + vistaPrincipal.getHeight());
                System.out.println("   Nave visible: " + vistaNave.isVisible());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}