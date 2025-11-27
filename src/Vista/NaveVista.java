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

        // ✅ CORREGIDO: Usar setSize y setBounds directamente
        setSize(nave.getTamaño(), nave.getTamaño());
        setBounds(nave.getX(), nave.getY(), nave.getTamaño(), nave.getTamaño());

        System.out.println("🚀 NaveVista CREADA - Tamaño nave: " + nave.getTamaño());
        System.out.println("📍 Posición inicial: (" + nave.getX() + ", " + nave.getY() + ")");
        System.out.println("📏 Panel size: " + getWidth() + "x" + getHeight());
        System.out.println("📐 Panel bounds: " + getBounds());

        cargarImagen();
    }

    private void cargarImagen() {
        try {
            // ✅ USA LA RUTA CORRECTA PARA TU PROYECTO
            java.io.File file = new java.io.File("C:\\trabajos clase\\Segundo año\\programacion\\java\\clase.jumil\\Animation\\src\\img\\nave.png");

            if (file.exists()) {
                System.out.println("✅ Imagen ENCONTRADA: " + file.getAbsolutePath());

                ImageIcon icono = new ImageIcon(file.getAbsolutePath());
                System.out.println("📏 Tamaño original imagen: " + icono.getIconWidth() + "x" + icono.getIconHeight());

                imagenBase = icono.getImage().getScaledInstance(
                        nave.getTamaño(), nave.getTamaño(), Image.SCALE_SMOOTH
                );
            } else {
                System.out.println("❌ Imagen NO encontrada en: " + file.getAbsolutePath());

                // ✅ PRUEBA ESTAS RUTAS ALTERNATIVAS:
                String[] rutasAlternativas = {
                        "src/img/nave.png",
                        "img/nave.png",
                        "./src/img/nave.png",
                        "../img/nave.png"
                };

                for (String ruta : rutasAlternativas) {
                    file = new java.io.File(ruta);
                    if (file.exists()) {
                        System.out.println("✅ Imagen encontrada en ruta alternativa: " + ruta);
                        ImageIcon icono = new ImageIcon(file.getAbsolutePath());
                        imagenBase = icono.getImage().getScaledInstance(
                                nave.getTamaño(), nave.getTamaño(), Image.SCALE_SMOOTH
                        );
                        return;
                    }
                }

                imagenBase = crearImagenTemporal();
            }
        } catch (Exception e) {
            System.err.println("❌ Error cargando imagen: " + e.getMessage());
            imagenBase = crearImagenTemporal();
        }
    }

    private Image crearImagenTemporal() {
        System.out.println("🎨 Creando imagen temporal AZUL");
        BufferedImage img = new BufferedImage(
                nave.getTamaño(), nave.getTamaño(), BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // ✅ CUADRADO AZUL BRILLANTE (visible)
        g2d.setColor(new Color(0, 100, 255)); // Azul brillante
        g2d.fillRect(0, 0, nave.getTamaño(), nave.getTamaño());

        // ✅ BORDE ROJO
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(2, 2, nave.getTamaño()-4, nave.getTamaño()-4);

        // ✅ TEXTO BLANCO
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("NAVE", 10, nave.getTamaño()/2);

        g2d.dispose();
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ✅ DEBUG: Fondo CYAN brillante para VER EL ÁREA DEL PANEL
        g.setColor(new Color(0, 255, 255, 150)); // Cyan semitransparente
        g.fillRect(0, 0, getWidth(), getHeight());

        if (imagenBase != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            actualizarRotacionSuave();

            // ✅ CORREGIDO: Usar getWidth() y getHeight() en lugar de nave.getTamaño()
            AffineTransform transform = new AffineTransform();
            transform.translate(getWidth() / 2, getHeight() / 2);
            transform.rotate(anguloActual);
            transform.translate(-getWidth() / 2, -getHeight() / 2);

            g2d.drawImage(imagenBase, transform, this);
            g2d.dispose();

            System.out.println("🎨 paintComponent - Imagen dibujada - Panel: " + getWidth() + "x" + getHeight());
        } else {
            // ✅ DEBUG: Si no hay imagen, dibujar algo visible
            g.setColor(Color.GREEN);
            g.fillRect(10, 10, getWidth()-20, getHeight()-20);
            System.out.println("🎨 paintComponent - Imagen TEMPORAL dibujada");
        }

        // ✅ DEBUG: Dibujar borde rojo alrededor del panel
        g.setColor(Color.RED);
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
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

        // ✅ DEBUG: Verificar que los bounds se actualizan correctamente
        System.out.println("🔄 NaveVista ACTUALIZADA - Pos: (" + nave.getX() + "," + nave.getY() +
                ") Tamaño: " + nave.getTamaño() +
                " Panel real: " + getWidth() + "x" + getHeight() +
                " Bounds: " + getBounds());

        repaint();
    }
}