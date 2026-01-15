package elementos;

import core.Elemento;
import core.IReagivel;
import elementos.liquido.Acido;
import elementos.liquido.Agua;
import elementos.liquido.Lava;

// Elemento congelado
public class Neve extends Elemento implements IReagivel {
    public Neve() {
        super("Neve", 0xFFFFFF, 2, true, false, false, false);
    }

    @Override
    public boolean podeReagir(Elemento elemento) {
        return elemento instanceof Fogo || elemento instanceof Lava || elemento instanceof Acido;
    }

    @Override
    public Elemento[] pegarResultado(Elemento elemento) {
        return elemento instanceof Acido ? new Elemento[]{new Agua(), new Agua()} : new Elemento[]{new Agua(), new Vapor()};
    }
}