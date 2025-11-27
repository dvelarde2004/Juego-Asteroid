package Modelo;

import java.awt.*;

public class Ball {
    private int x, y;
    private int tamaño;
    private Color color;
    private int vx, vy;
    private int baseVx, baseVy;
    private int baseTamaño;
    private int anchoMax, altoMax;

    // Constructor de la bola con todo lo que necesita
    public Ball(Color color, int x, int y, int vx, int vy, int anchoMax, int altoMax) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.baseVx = vx;
        this.baseVy = vy;
        this.tamaño = 30;
        this.baseTamaño = 30;
        this.anchoMax = anchoMax;
        this.altoMax = altoMax;
    }

    // Mueve la bola y hace que rebote en los bordes
    public void movimiento() {
        x += vx;
        y += vy;

        // Rebotar en los bordes de la pantalla
        if (x <= 0 || x >= anchoMax - tamaño) {
            vx = -vx;
            x = Math.max(0, Math.min(x, anchoMax - tamaño));
        }
        if (y <= 0 || y >= altoMax - tamaño) {
            vy = -vy;
            y = Math.max(0, Math.min(y, altoMax - tamaño));
        }
    }

    // Comprueba si esta bola choca con otra
    public boolean colisionaCon(Ball otra) {
        int dx = x - otra.x;
        int dy = y - otra.y;
        int distancia = (int) Math.sqrt(dx * dx + dy * dy);
        return distancia < (tamaño / 2 + otra.tamaño / 2);
    }

    // Cambian la velocidad cuando chocan
    public void intercambianVelocidad(Ball otra) {
        int tempVx = this.vx;
        int tempVy = this.vy;
        this.vx = otra.vx;
        this.vy = otra.vy;
        otra.vx = tempVx;
        otra.vy = tempVy;
    }

    // Ajusta la velocidad desde la base
    public void ajustarVelocidadDesdeBase(int ajuste) {
        this.vx = baseVx + ajuste;
        this.vy = baseVy + ajuste;

        // Que no se quede parada
        if (vx == 0) vx = 1;
        if (vy == 0) vy = 1;
    }

    // Ajusta el tamaño desde la base
    public void ajustarTamañoDesdeBase(int ajuste) {
        this.tamaño = Math.max(10, Math.min(100, baseTamaño + ajuste));
    }

    // Getters normales
    public int getX() { return x; }
    public int getY() { return y; }
    public int getTamaño() { return tamaño; }
    public Color getColor() { return color; }
    public int getVx() { return vx; }
    public int getVy() { return vy; }

    // Setters para cambiar la velocidad
    public void setVx(int vx) { this.vx = vx; }
    public void setVy(int vy) { this.vy = vy; }
}