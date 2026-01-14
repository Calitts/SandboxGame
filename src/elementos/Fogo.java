package elementos;

import core.Elemento;
import core.IReagivel;

// Queima elementos inflamáveis
public class Fogo extends Elemento implements IReagivel {
    public Fogo() {
        super("Fogo", 0xFF4500, 1, false, false, true, false);
    }

    @Override
    public boolean podeReagir(Elemento elemento) {
        return elemento instanceof Fumaca;
    }

    @Override
    public Elemento[] pegarResultado(Elemento elemento) {
        return new Elemento[]{new Cinzas(), new Cinzas()};
    }
}