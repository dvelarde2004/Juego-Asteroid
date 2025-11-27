package Vista;

import Modelo.Nave;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class NaveVista extends JPanel {
    private Nave nave;
    private Image imagenBase;
    private double anguloActual = 0;
    private double anguloObjetivo = 0;
    private final double VELOCIDAD_ROTACION = 0.2;

    public NaveVista(Nave nave) {
        this.nave = nave;
        setOpaque(false);
        setSize(nave.getTamaño(), nave.getTamaño());
        setBounds(nave.getX(), nave.getY(), nave.getTamaño(), nave.getTamaño());
        cargarImagen(); // ✅ Sin mensajes de debug
    }

    private void cargarImagen() {
        try {
            java.io.File file = new java.io.File("C:\\trabajos clase\\Segundo año\\programacion\\java\\clase jumi\\Animacion\\src\\img\\nave.png");

            if (!file.exists()) {
                file = new java.io.File("img/nave.png");
            }
            if (!file.exists()) {
                file = new java.io.File("./src/img/nave.png");
            }

            if (file.exists()) {
                ImageIcon icono = new ImageIcon(file.getAbsolutePath());
                imagenBase = icono.getImage().getScaledInstance(
                        nave.getTamaño(), nave.getTamaño(), Image.SCALE_SMOOTH
                );
            } else {
                // ✅ Crear imagen temporal SILENCIOSAMENTE
                imagenBase = crearImagenTemporal();
            }
        } catch (Exception e) {
            imagenBase = crearImagenTemporal();
        }
    }

    private Image crearImagenTemporal() {
        BufferedImage img = new BufferedImage(
                nave.getTamaño(), nave.getTamaño(), BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Imagen temporal azul sin texto
        g2d.setColor(new Color(0, 100, 255));
        g2d.fillRect(0, 0, nave.getTamaño(), nave.getTamaño());

        g2d.dispose();
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ✅ SIN BORDE ROJO, SIN FONDO NARANJA - SOLO LA IMAGEN
        if (imagenBase != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            actualizarRotacionSuave();

            AffineTransform transform = new AffineTransform();
            transform.translate(getWidth() / 2, getHeight() / 2);
            transform.rotate(anguloActual);
            transform.translate(-getWidth() / 2, -getHeight() / 2);

            g2d.drawImage(imagenBase, transform, this);
            g2d.dispose();
        }
    }

    private void actualizarRotacionSuave() {
        switch (nave.getDireccion()) {
            case 0: anguloObjetivo = 0; break;
            case 1: anguloObjetivo = Math.PI / 2; break;
            case 2: anguloObjetivo = Math.PI; break;
            case 3: anguloObjetivo = 3 * Math.PI / 2; break;
        }

        double diferencia = anguloObjetivo - anguloActual;
        while (diferencia > Math.PI) diferencia -= 2 * Math.PI;
        while (diferencia < -Math.PI) diferencia += 2 * Math.PI;

        anguloActual += diferencia * VELOCIDAD_ROTACION;
    }

    public void actualizarNave() {
        setBounds(nave.getX(), nave.getY(), nave.getTamaño(), nave.getTamaño());
        repaint(); // ✅ Sin mensajes de debug
    }
}