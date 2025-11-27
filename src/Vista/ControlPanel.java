package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    public JSlider velocidadSlider;
    public JSlider tamañoSlider;
    public JButton añadirBallBoton;

    public ControlPanel() {
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Velocidad:"));
        velocidadSlider = new JSlider(1, 10, 5);
        add(velocidadSlider);

        add(new JLabel("Tamaño:"));
        tamañoSlider = new JSlider(10, 100, 30);
        add(tamañoSlider);

        añadirBallBoton = new JButton("Añadir Pelota");
        add(añadirBallBoton);
    }

    public void setAddBallListener(ActionListener listener) {
        añadirBallBoton.addActionListener(listener);
    }
}