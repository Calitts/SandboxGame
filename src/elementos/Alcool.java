package elementos;

import core.Elemento;
import core.IReagivel;

// Líquido instiga fogo
public class Alcool extends Elemento implements IReagivel {
    public Alcool() {
        super("Álcool", 0xC0C0C0, 3, false, true, false, true);
    }

    @Override
    public boolean podeReagir(Elemento elemento) {
        return elemento instanceof Fogo;
    }

    @Override
    public Elemento[] pegarResultado(Elemento elemento) {
        return new Elemento[]{new Fogo(), new Fogo()};
    }
}