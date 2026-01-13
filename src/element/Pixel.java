package element;

import java.awt.Color;
import java.util.Random;

// Classe onde os pixels terão seus comportamentos classificados

public class Pixel {
    private Elemento type;

    public Pixel() {
        this.type = new Ar();
    }

    public Pixel(Elemento e) {
        this.type = e;
        int offset = 0;
        // if (type.nome != "Ar") {
        //     Random gen = new Random();
        //     offset = gen.nextInt(60) - 30;
        // }
        this.type.cor += offset;
    }

    public boolean suffersGravity() {
        return this.type.sofreGravidade();
    }

    public boolean isSolid() {
        return this.type.solido;
    }

    public String getName() {
        return this.type.nome;
    }

    public Color getColor(int offset) {
        return new Color(this.type.getCor() + offset);
    }

    public int getGravity() {
        int res = this.type.peso;
        // try {
        // res = 10 / this.type.peso;
        // } catch (ArithmeticException e) {
        // res = 0;
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
