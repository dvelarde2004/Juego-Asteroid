package Modelo;

public class Nave {
    private int x, y; // Posición de la nave
    private int tamaño; // Tamaño visualmente atractivo pero modificable
    private int velocidad; // Velocidad atractiva pero modificable
    private int direccion; // 0=arriba (0°), 1=derecha (90°), 2=abajo (180°), 3=izquierda (270°)
    private final int width, height; // Límites de la pantalla

    public Nave(int width, int height) {
        this.width = width;
        this.height = height;

        // ✅ VALORES POR DEFECTO (visualmente atractivos pero modificables)
        this.tamaño = 50; // 50px - buen tamaño visual
        this.velocidad = 6; // 6px/frame - velocidad balanceada
        this.direccion = 0; // Mirando hacia arriba por defecto

        // ✅ POSICIÓN INICIAL CENTRADA
        this.x = width / 2 - tamaño / 2;
        this.y = height / 2 - tamaño / 2;
    }

    // ✅ MOVIMIENTOS DIRECTOS CON TECLAS
    public void moverArriba() {
        if (y - velocidad >= 0) { // ✅ NO SALIRSE DE PANTALLA
            y -= velocidad;
        }
        direccion = 0; // 0° - Arriba
    }

    public void moverDerecha() {
        if (x + tamaño + velocidad <= width) { // ✅ NO SALIRSE DE PANTALLA
            x += velocidad;
        }
        direccion = 1; // 90° - Derecha
    }

    public void moverAbajo() {
        if (y + tamaño + velocidad <= height) { // ✅ NO SALIRSE DE PANTALLA
            y += velocidad;
        }
        direccion = 2; // 180° - Abajo
    }

    public void moverIzquierda() {
        if (x - velocidad >= 0) { // ✅ NO SALIRSE DE PANTALLA
            x -= velocidad;
        }
        direccion = 3; // 270° - Izquierda
    }

    // ✅ DETECCIÓN DE COLISIÓN (MISMA QUE BOLAS)
    public boolean colisionaCon(Ball bola) {
        // Calcular centros
        int centroNaveX = x + tamaño / 2;
        int centroNaveY = y + tamaño / 2;
        int centroBolaX = bola.getX() + bola.getTamaño() / 2;
        int centroBolaY = bola.getY() + bola.getTamaño() / 2;

        // Calcular distancia entre centros
        int dx = centroNaveX - centroBolaX;
        int dy = centroNaveY - centroBolaY;
        int distancia = (int) Math.sqrt(dx * dx + dy * dy);

        // Suma de radios (misma física que bolas)
        int sumaRadios = (tamaño / 2) + (bola.getTamaño() / 2);

        return distancia < sumaRadios;
    }

    // ✅ GETTERS Y SETTERS (PARA MODIFICAR EN FUTURO)
    public int getX() { return x; }
    public int getY() { return y; }
    public int getTamaño() { return tamaño; }
    public int getVelocidad() { return velocidad; }
    public int getDireccion() { return direccion; }

    public void setTamaño(int tamaño) {
        this.tamaño = Math.max(20, Math.min(100, tamaño)); // Límites razonables
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = Math.max(1, Math.min(15, velocidad)); // Límites razonables
    }
}