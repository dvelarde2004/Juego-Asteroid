import Controlador.BallController;
import Controlador.NaveController;
import Modelo.Ball;
import Modelo.Nave;
import Vista.BallVista;
import Vista.NaveVista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class Main {
    public static void main(String[] args) {
        // Dimensiones del área de movimiento
        int width = 800;
        int height = 600;

        System.out.println("🚀 INICIANDO JUEGO...");

        // Crear las pelotas iniciales
        Ball ball1 = new Ball(RED, 50, 50, 2, 3, width, height);
        Ball ball2 = new Ball(BLUE, 100, 100, 3, 2, width, height);
        Ball ball3 = new Ball(GREEN, 150, 150, 4, 4, width, height);

        // ✅ CREAR LA NAVE
        Nave nave = new Nave(width, height);
        System.out.println("📍 Nave creada - Posición: " + nave.getX() + ", " + nave.getY());

        // Crear la vista principal de bolas
        List<Ball> balls = new ArrayList<>(List.of(ball1, ball2, ball3));
        BallVista vistaPrincipal = new BallVista();

        // ✅ CREAR LA VISTA DE LA NAVE
        NaveVista vistaNave = new NaveVista(nave);
        System.out.println("🖼️ VistaNave creada - Tamaño: " + vistaNave.getWidth() + "x" + vistaNave.getHeight());

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

        // Configurar listeners
        vistaPrincipal.setupControllerListeners(controller1, balls, width, height);

        // ✅ FORZAR VISIBILIDAD
        vistaNave.setVisible(true);
        vistaNave.repaint();
        vistaPrincipal.revalidate();
        vistaPrincipal.repaint();

        // ✅ DAR FOCUS A LA VISTA DE LA NAVE
        vistaNave.requestFocusInWindow();
        System.out.println("🎯 Focus solicitado para vistaNave");

        // ✅ INICIAR HILOS
        new Thread(controller1).start();
        new Thread(controller2).start();
        new Thread(controller3).start();
        new Thread(naveController).start();

        System.out.println("✅ TODOS LOS HILOS INICIADOS");
        System.out.println("🎮 Controles: W, A, S, D");

        // ✅ VERIFICACIÓN FINAL DESPUÉS DE 1 SEGUNDO
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("🔍 VERIFICACIÓN FINAL:");
                System.out.println("   Nave visible: " + vistaNave.isVisible());
                System.out.println("   Nave showing: " + vistaNave.isShowing());
                System.out.println("   Nave bounds: " + vistaNave.getBounds());
                System.out.println("   Nave posición: " + nave.getX() + ", " + nave.getY());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}