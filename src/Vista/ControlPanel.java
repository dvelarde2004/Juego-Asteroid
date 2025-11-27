package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    public JSlider velocidadSlider;
    public JSlider tamañoSlider;
    public JButton añadirBallBoton;

    public ControlPanel() {
        // ✅ LAYOUT HORIZONTAL para panel inferior
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        setPreferredSize(new Dimension(800, 100));

        // ✅ MEJORAR ESTILO
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createTitledBorder("Panel de Control"));

        // ✅ ETIQUETA Y SLIDER VELOCIDAD
        add(new JLabel("Velocidad:"));
        velocidadSlider = new JSlider(1, 10, 5);
        velocidadSlider.setPreferredSize(new Dimension(100, 40));
        add(velocidadSlider);

        // ✅ ETIQUETA Y SLIDER TAMAÑO
        add(new JLabel("Tamaño:"));
        tamañoSlider = new JSlider(10, 100, 30);
        tamañoSlider.setPreferredSize(new Dimension(100, 40));
        add(tamañoSlider);

        // ✅ BOTÓN AÑADIR PELOTA
        añadirBallBoton = new JButton("Añadir Pelota");
        añadirBallBoton.setPreferredSize(new Dimension(120, 35));
        add(añadirBallBoton);
    }

    public void setAddBallListener(ActionListener listener) {
        añadirBallBoton.addActionListener(listener);
    }
}