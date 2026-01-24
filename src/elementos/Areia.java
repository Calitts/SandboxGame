package elementos;

import core.Elemento;
import core.IReagivel;
import elementos.liquido.Agua;
import elementos.liquido.Lava;

// Elemento granuloso que cai
public class Areia extends Elemento implements IReagivel {
    public Areia() {
        super("Areia", 0xFFFF00, 1, true, false, false, false);
    }

    @Override
    public boolean podeReagir(Elemento elemento) {
        return elemento instanceof Lava || elemento instanceof Agua;
    }

    @Override
    public Elemento[] pegarResultado(Elemento elemento) {
        if (elemento instanceof Lava)
            return new Elemento[]{new Vidro(), new Vidro()};
        return new Elemento[]{new Lama(), new Lama()};
    }
}
