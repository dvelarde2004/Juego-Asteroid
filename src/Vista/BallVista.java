package Vista;

import Modelo.Ball;
import Controlador.BallController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BallVista extends JPanel {
    private List<Ball> balls;

    // Constructor de la vista donde se ven las bolas
    public BallVista() {
        setBackground(Color.WHITE); // Fondo blanco
        setOpaque(true);
        setLayout(null); // Para poder poner la nave donde quiera
        setPreferredSize(new Dimension(800, 600)); // Tamaño fijo
    }

    // Le paso la lista de bolas para que las dibuje
    public void setBalls(List<Ball> balls) {
        this.balls = balls;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Pinto el fondo blanco
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Dibujo todas las bolas
        if (balls != null) {
            for (Ball ball : balls) {
                g.setColor(ball.getColor());
                g.fillOval(ball.getX(), ball.getY(), ball.getTamaño(), ball.getTamaño());
            }
        }
    }

    // Esto ya no se usa mucho pero lo dejo por si acaso
    public void setupControllerListeners(BallController controller, List<Ball> balls, int width, int height) {
        this.balls = balls;
        setFocusable(true);
    }

    // Para actualizar el dibujo de las bolas
    public void draw(List<Ball> balls) {
        this.balls = balls;
        repaint();
    }

    // Lo mismo que draw pero con otro nombre
    public void refrescar(List<Ball> balls) {
        this.balls = balls;
        repaint();
    }
}