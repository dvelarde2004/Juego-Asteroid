package Modelo;

public class Nave {
    private int x, y;
    private int tamaño;
    private int direccion; // 0=arriba, 1=derecha, 2=abajo, 3=izquierda
    private int anchoMax, altoMax;

    // Creo la nave en la parte de abajo del todo
    public Nave(int anchoMax, int altoMax) {
        this.anchoMax = anchoMax;
        this.altoMax = altoMax;
        this.tamaño = 50;
        this.x = anchoMax / 2 - tamaño / 2; // En el centro
        this.y = altoMax - tamaño - 20; // Arriba del todo
        this.direccion = 0; // Mirando hacia arriba
    }

    // Mueve la nave pero sin salirse de la pantalla
    public void mover(int dx, int dy) {
        this.x += dx;
        this.y += dy;

        // Que no se salga de los bordes
        this.x = Math.max(0, Math.min(x, anchoMax - tamaño));
        this.y = Math.max(0, Math.min(y, altoMax - tamaño));
    }

    // SOLO UN HILO PUEDE EJECUTAR ESTO A LA VEZ
    public synchronized boolean colisionaCon(Ball ball) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int ballTamaño = ball.getTamaño();

        return x < ballX + ballTamaño &&
                x + tamaño > ballX &&
                y < ballY + ballTamaño &&
                y + tamaño > ballY;
    }

    // Getters y setters normales
    public int getX() { return x; }
    public int getY() { return y; }
    public int getTamaño() { return tamaño; }
    public int getDireccion() { return direccion; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setTamaño(int tamaño) { this.tamaño = tamaño; }
    public void setDireccion(int direccion) { this.direccion = direccion; }
}