package core;

import elementos.Ar;

import java.awt.Color;

// Classe onde os pixels terão seus comportamentos classificados

public class Pixel {
    private Elemento type;

    public Pixel() {
        this.type = new Ar();
    }

    public Pixel(Elemento elemento) {
        type = elemento;
    }

    public boolean suffersGravity() {
        return type.sofreGravidade();
    }

    public boolean isSolid() {
        return type.solido;
    }

    public boolean isGas() {
        return type.gasoso;
    }

    public int getFlow() {
        if (type.liquido) {
            return 50;
        }
        return 1;
    }

    public String getName() {
        return type.getName();
    }

    public Color getColor(int offset) {
        return new Color(this.type.getCor() + offset);
    }

    public int getWeight() {
        return type.peso;
    }

    public Elemento getType() {
        return type;
    }

    public void setType(Elemento elemento) {
        type = elemento;
    }
}
