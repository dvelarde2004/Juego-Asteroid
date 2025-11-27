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

    // Creo la vista de la nave con su tamaño y posicion
    public NaveVista(Nave nave) {
        this.nave = nave;
        setOpaque(false); // Transparente para ver las bolas detras
        setSize(nave.getTamaño(), nave.getTamaño());
        setBounds(nave.getX(), nave.getY(), nave.getTamaño(), nave.getTamaño());
        cargarImagen(); // Intento cargar la imagen del cohete
    }

    // Intento cargar la imagen, si no existe hago una temporal
    private void cargarImagen() {
        try {
            java.io.File file = new java.io.File("C:\\trabajos clase\\Segundo año\\programacion\\java\\clase jumi\\Animacion\\src\\img\\nave.png");

            // Pruebo varias rutas por si no esta en la primera
            if (!file.exists()) {
                file = new java.io.File("img/nave.png");
            }
            if (!file.exists()) {
                file = new java.io.File("./src/img/nave.png");
            }

            if (file.exists()) {
                ImageIcon icono = new ImageIcon(file.getAbsolutePath());
                // Escalo la imagen al tamaño de la nave
                imagenBase = icono.getImage().getScaledInstance(
                        nave.getTamaño(), nave.getTamaño(), Image.SCALE_SMOOTH
                );
            } else {
                // Si no encuentro la imagen, hago una azul
                imagenBase = crearImagenTemporal();
            }
        } catch (Exception e) {
            imagenBase = crearImagenTemporal();
        }
    }

    // Hago un cuadrado azul si no hay imagen
    private Image crearImagenTemporal() {
        BufferedImage img = new BufferedImage(
                nave.getTamaño(), nave.getTamaño(), BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cuadrado azul con borde rojo
        g2d.setColor(new Color(0, 100, 255));
        g2d.fillRect(0, 0, nave.getTamaño(), nave.getTamaño());

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(2, 2, nave.getTamaño()-4, nave.getTamaño()-4);

        g2d.dispose();
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujo la imagen rotada segun la direccion
        if (imagenBase != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            actualizarRotacionSuave(); // Actualizo el angulo poco a poco

            // Roto la imagen
            AffineTransform transform = new AffineTransform();
            transform.translate(getWidth() / 2, getHeight() / 2);
            transform.rotate(anguloActual);
            transform.translate(-getWidth() / 2, -getHeight() / 2);

            g2d.drawImage(imagenBase, transform, this);
            g2d.dispose();
        }
    }

    // Cambio el angulo poco a poco para que se vea suave
    private void actualizarRotacionSuave() {
        // Segun la direccion pongo un angulo diferente
        switch (nave.getDireccion()) {
            case 0: anguloObjetivo = 0; break;        // Arriba
            case 1: anguloObjetivo = Math.PI / 2; break;   // Derecha
            case 2: anguloObjetivo = Math.PI; break;       // Abajo
            case 3: anguloObjetivo = 3 * Math.PI / 2; break; // Izquierda
        }

        // Calculo la diferencia y voy rotando poco a poco
        double diferencia = anguloObjetivo - anguloActual;
        while (diferencia > Math.PI) diferencia -= 2 * Math.PI;
        while (diferencia < -Math.PI) diferencia += 2 * Math.PI;

        anguloActual += diferencia * VELOCIDAD_ROTACION;
    }

    // Actualizo la posicion de la nave en pantalla
    public void actualizarNave() {
        setBounds(nave.getX(), nave.getY(), nave.getTamaño(), nave.getTamaño());
        repaint(); // Vuelvo a dibujar
    }
}