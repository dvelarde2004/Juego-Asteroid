package Vista;

import Modelo.Ball;
import Controlador.BallController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BallVista extends JPanel {
    private List<Ball> balls;

    public BallVista() {
        // ✅ CAMBIAR FONDO A BLANCO
        setBackground(Color.WHITE);
        setOpaque(true);

        setLayout(null);
        setPreferredSize(new Dimension(800, 600));
    }

    public void setBalls(List<Ball> balls) {
        this.balls = balls;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ✅ FONDO BLANCO (sin debug)
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // ✅ DIBUJAR LAS PELOTAS (sin textos de debug)
        if (balls != null) {
            for (Ball ball : balls) {
                g.setColor(ball.getColor());
                g.fillOval(ball.getX(), ball.getY(), ball.getTamaño(), ball.getTamaño());
            }
        }
    }

    public void draw(List<Ball> balls) {
        this.balls = balls;
        repaint();
    }

    public void refrescar(List<Ball> balls) {
        this.balls = balls;
        repaint();
    }
}