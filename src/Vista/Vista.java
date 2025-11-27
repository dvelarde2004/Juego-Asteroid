package Vista;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {
    private JPanel panelPrincipal;
    private NaveVista naveVista;

    public Ventana() {
        configurarVentana();
        inicializarComponentes();
        hacerVisible();
    }

    private void configurarVentana() {
        setTitle("Space Invaders");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null); // ✅ IMPORTANTE: Layout null para usar setBounds
        panelPrincipal.setBackground(Color.BLACK);

        // ✅ VERIFICACIONES
        System.out.println("=== INICIALIZANDO VENTANA ===");
        System.out.println("🖼️  Tamaño JFrame: " + getWidth() + "x" + getHeight());

        // ✅ BORDE VERDE para ver el área del panelPrincipal
        panelPrincipal.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));

        // Crear nave en posición CENTRAL
        Modelo.Nave nave = new Modelo.Nave();
        int centroX = 375;  // Centro horizontal
        int centroY = 275;  // Centro vertical
        nave.setX(centroX);
        nave.setY(centroY);
        nave.setTamaño(50);

        System.out.println("🎯 Nave en posición: (" + centroX + ", " + centroY + ")");

        // Crear y agregar la vista de la nave
        naveVista = new NaveVista(nave);
        panelPrincipal.add(naveVista);
        add(panelPrincipal);
    }

    private void hacerVisible() {
        setVisible(true);

        System.out.println("=== VENTANA VISIBLE ===");
        System.out.println("👀 JFrame size: " + getWidth() + "x" + getHeight());

        // Forzar actualización
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    public static void main(String[] args) {
        // ✅ EJECUTAR LA VENTANA
        SwingUtilities.invokeLater(() -> {
            new Ventana();
        });
    }
}