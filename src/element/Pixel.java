package element;

import java.awt.Color;

// Classe onde os pixels terão seus comportamentos classificados

public class Pixel {
    private Elemento type;

    public Pixel() {
        this.type = new Ar();
    }

    public Pixel(Elemento e) {
        this.type = e;
    }

    public String getName() {
        return this.type.nome;
    }

    public Color getColor() {
        return new Color(this.type.cor);
    }

    public int getGravity() {
        int res = this.type.peso;
        // try {
        //     res = 10 / this.type.peso;
        // } catch (ArithmeticException e) {
        //     res = 0;
        // }
        return res;
    }

    public Elemento getType() {
        return this.type;
    }

    public void setType(Elemento e) {
        this.type = e;
    }
}
