package elementos;

import core.Elemento;
import core.IReagivel;

// sólido e inflamável
public class Madeira extends Elemento implements IReagivel {
    public Madeira() {
        super("Madeira", 0x8B4513, 7, true, false, false, true);
    }

    @Override
    public boolean podeReagir(Elemento elemento) {
        return elemento instanceof Fogo || elemento instanceof Lava;
    }

    @Override
    public Elemento[] pegarResultado(Elemento elemento) {
        return new Elemento[]{new Fogo(), new Fumaca()};
    }
}