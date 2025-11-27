package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    public JSlider velocidadSlider;
    public JSlider tamañoSlider;
    public JButton añadirBallBoton;

    // Panel de control con sliders y botones
    public ControlPanel() {
        // Layout horizontal para que quede bonito
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        setPreferredSize(new Dimension(800, 100)); // Tamaño fijo
        setBackground(new Color(240, 240, 240)); // Fondo gris claro
        setBorder(BorderFactory.createTitledBorder("Panel de Control")); // Borde con titulo

        // Slider para la velocidad
        add(new JLabel("Velocidad:"));
        velocidadSlider = new JSlider(1, 10, 5);
        velocidadSlider.setPreferredSize(new Dimension(100, 40));
        add(velocidadSlider);

        // Slider para el tamaño
        add(new JLabel("Tamaño:"));
        tamañoSlider = new JSlider(10, 100, 30);
        tamañoSlider.setPreferredSize(new Dimension(100, 40));
        add(tamañoSlider);

        // Boton para añadir bolas
        añadirBallBoton = new JButton("Añadir Pelota");
        añadirBallBoton.setPreferredSize(new Dimension(120, 35));
        add(añadirBallBoton);
    }

    // Le paso lo que tiene que hacer el boton
    public void setAddBallListener(ActionListener listener) {
        añadirBallBoton.addActionListener(listener);
    }
}