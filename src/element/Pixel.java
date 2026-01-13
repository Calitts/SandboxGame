package element;

import java.awt.Color;

import core.Position;

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
        return this.type.peso;
    }

    public Elemento getType() {
        return this.type;
    }

    public void setType(Elemento e) {
        this.type = e;
    }
}
