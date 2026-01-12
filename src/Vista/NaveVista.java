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
        cargarImagen();
        configurarControlesNave(); // NUEVO: Configuro los controles aquí
    }

    // NUEVO MÉTODO: Configuro los controles WASD en la vista
    private void configurarControlesNave() {
        // Uso key bindings que funcionan aunque no tenga el foco
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "moverArriba");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "moverAbajo");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "moverIzquierda");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "moverDerecha");

        // Lo que hace cada tecla
        getActionMap().put("moverArriba", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setY(nave.getY() - 10);
                nave.setDireccion(0); // Arriba
                actualizarNave();
            }
        });

        getActionMap().put("moverAbajo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setY(nave.getY() + 10);
                nave.setDireccion(2); // Abajo
                actualizarNave();
            }
        });

        getActionMap().put("moverIzquierda", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setX(nave.getX() - 10);
                nave.setDireccion(3); // Izquierda
                actualizarNave();
            }
        });

        getActionMap().put("moverDerecha", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nave.setX(nave.getX() + 10);
                nave.setDireccion(1); // Derecha
                actualizarNave();
            }
        });
    }

    // El resto del código se mantiene igual...
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
        repaint();
    }
}