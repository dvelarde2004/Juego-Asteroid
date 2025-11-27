package Modelo;

import java.awt.*;

public class Ball {
    private int x, y; // Posición de la bola
    private int vx, vy; // Velocidad de la bola
    private Color color; // Color de la bola
    private int tamaño = 30; // Tamaño inicial de la bola
    private int tamañoBase = 30; // ✅ NUEVO: Tamaño base original
    private int velocidadBase; // ✅ NUEVO: Velocidad base original
    private final int width, height; // Dimensiones del área de dibujo

    // Constructor
    public Ball(Color color, int x, int y, int vx, int vy, int width, int height) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.vx = vx;
        this.vy = vy;
        this.width = width;
        this.height = height;
        this.velocidadBase = getVelocidad(); // ✅ Guardar velocidad base
    }

    // ✅ NUEVO: Ajustar tamaño basado en el tamaño base
    public void ajustarTamañoDesdeBase(int nuevoTamañoBase) {
        this.tamañoBase = Math.max(10, Math.min(100, nuevoTamañoBase));
        this.tamaño = this.tamañoBase;
    }

    // ✅ NUEVO: Ajustar velocidad basada en la velocidad base
    public void ajustarVelocidadDesdeBase(int ajuste) {
        int nuevaVelocidad = Math.max(1, this.velocidadBase + ajuste);
        setVelocidad(nuevaVelocidad);
    }

    // Cambia la velocidad manteniendo la dirección
    public void setVelocidad(int nuevaVelocidad) {
        double angulo = Math.atan2(vy, vx);
        this.vx = (int) Math.round(nuevaVelocidad * Math.cos(angulo));
        this.vy = (int) Math.round(nuevaVelocidad * Math.sin(angulo));
    }

    // Devuelve la velocidad actual (módulo del vector velocidad)
    public int getVelocidad() {
        return (int) Math.sqrt(vx * vx + vy * vy);
    }

    // Movimiento de la bola y rebote en los bordes
    public void movimiento() {
        x += vx;
        y += vy;

        if (x < 0 || x + tamaño > width) {
            vx = -vx;
        }
        if (y < 0 || y + tamaño > height) {
            vy = -vy;
        }
    }

    // ✅ CORREGIDO: Hitbox precisa - usa radio en lugar de diámetro
    public boolean colisionaCon(Ball otra) {
        // Calcular centros de las bolas
        int centroX1 = this.x + this.tamaño / 2;
        int centroY1 = this.y + this.tamaño / 2;
        int centroX2 = otra.x + otra.tamaño / 2;
        int centroY2 = otra.y + otra.tamaño / 2;

        // Calcular distancia entre centros
        int dx = centroX1 - centroX2;
        int dy = centroY1 - centroY2;
        int distancia = (int) Math.sqrt(dx * dx + dy * dy);

        // ✅ CORRECCIÓN: Suma de radios (no diámetros)
        int sumaRadios = (this.tamaño / 2) + (otra.tamaño / 2);

        return distancia < sumaRadios;
    }

    // Intercambia velocidades con otra bola
    public void intercambianVelocidad(Ball otra) {
        int tempVx = this.vx;
        int tempVy = this.vy;
        this.vx = otra.vx;
        this.vy = otra.vy;
        otra.vx = tempVx;
        otra.vy = tempVy;
    }

    // ✅ AÑADIR ESTOS 4 MÉTODOS NUEVOS PARA REBOTE CON NAVE:
    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    // Getters existentes
    public int getX() { return x; }
    public int getY() { return y; }
    public int getTamaño() { return tamaño; }
    public Color getColor() { return color; }
    public int getTamañoBase() { return tamañoBase; }
    public int getVelocidadBase() { return velocidadBase; }
}