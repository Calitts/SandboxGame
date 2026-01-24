package elementos;

import core.Elemento;
import core.IReagivel;
import elementos.liquido.Lava;

// Líquido inflamável
public class Oleo extends Elemento implements IReagivel {
    public Oleo() {
        super("Óleo", 0xFFD700, 1, false, true, false, true);
    }

    @Override
    public boolean podeReagir(Elemento elemento) {
        return elemento instanceof Fogo || elemento instanceof Eletricidade || elemento instanceof Lava;
    }

    @Override
    public Elemento[] pegarResultado(Elemento elemento) {
        if (elemento instanceof Eletricidade) return new Elemento[]{new Oleo(), new Fogo()};
        if (elemento instanceof Lava) return new Elemento[]{new Lava(), new Fogo()};
        return new Elemento[]{new Fogo(), new Fogo()};
    }
}