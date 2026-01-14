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
        // this.type.nome = e.nome;
        // this.type.cor = e.getCor();
        // this.type.peso = e.peso;
        // this.type.liquido = e.liquido;
        // this.type.solido = e.solido;
        // this.type.gasoso = e.gasoso;
        // this.type.inflamavel = e.inflamavel;
        int offset = 0;
        
        this.type.cor += offset;
    }

    public boolean suffersGravity() {
        return this.type.sofreGravidade();
    }

    public boolean isSolid() {
        return this.type.solido;
    }

    public boolean isGas() {
        return this.type.gasoso;
    }

    public int getFlow() {
        if (this.type.liquido) {
            return 50;
        }
        return 1;
    }

    public String getName() {
        return this.type.nome;
    }

    public Color getColor(int offset) {
        return new Color(this.type.getCor() + offset);
    }

    public int getWeight() {
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
