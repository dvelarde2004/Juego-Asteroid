package Vista;

import Controlador.BallController;
import Modelo.Ball;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.Random;

public class BallVista extends JFrame {
    private Canvas canvas;
    private ControlPanel controlPanel;
    private Random random;

    public BallVista() {
        this.random = new Random();
        setTitle("Pelotas Rebotando - UI Funcional");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        // Canvas donde se dibuja la pelota
        canvas = new Canvas();
        canvas.setBackground(Color.WHITE);
        panel.add(canvas, BorderLayout.CENTER);

        // Agrega el panel de control abajo
        controlPanel = new ControlPanel();
        panel.add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
        canvas.createBufferStrategy(2);
    }

    // Configurar los listeners del controlador
    public void setupControllerListeners(BallController controller, List<Ball> balls, int width, int height) {
        // Listener para el slider de velocidad
        controlPanel.velocidadSlider.addChangeListener(e -> {
            int valorSlider = controlPanel.velocidadSlider.getValue();
            // Convertir valor del slider (1-10) a ajuste de velocidad (-4 a +5)
            int ajusteVelocidad = valorSlider - 5; // Centro en 5 = ajuste 0
            controller.ajustarVelocidadGlobal(ajusteVelocidad);
        });

        // Listener para el slider de tamaño
        controlPanel.tamañoSlider.addChangeListener(e -> {
            int valorSlider = controlPanel.tamañoSlider.getValue();
            // Convertir valor del slider (10-100) a ajuste de tamaño
            int ajusteTamaño = valorSlider - 30; // Centro en 30 = ajuste 0
            controller.ajustarTamañoGlobal(ajusteTamaño);
        });

        // En el método setupControllerListeners, modificar la creación:
        controlPanel.setAddBallListener(e -> {
            Color colorAleatorio = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            int xAleatorio = random.nextInt(width - 50);
            int yAleatorio = random.nextInt(height - 50);
            int vxAleatorio = random.nextInt(7) - 3;
            int vyAleatorio = random.nextInt(7) - 3;
            int tamañoBaseAleatorio = 20 + random.nextInt(41); // 20 a 60

            // ✅ CORREGIDO: Usar tamaño base en el constructor
            Ball nuevaBall = new Ball(colorAleatorio, xAleatorio, yAleatorio,
                    vxAleatorio, vyAleatorio, width, height);
            nuevaBall.ajustarTamañoDesdeBase(tamañoBaseAleatorio); // Establecer tamaño base

            controller.añadirBola(nuevaBall);
        });
    }

    public void draw(List<Ball> balls) {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            bs = canvas.getBufferStrategy();
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Ball ball : balls) {
            g.setColor(ball.getColor());
            g.fillOval(ball.getX(), ball.getY(), ball.getTamaño(), ball.getTamaño());
        }

        g.dispose();
        bs.show();
    }

    public void refrescar(List<Ball> balls) {
        draw(balls);
    }
}