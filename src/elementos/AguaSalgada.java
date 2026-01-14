package elementos;

import core.Elemento;
import core.IReagivel;

// Líquido salgado
public class AguaSalgada extends Elemento implements IReagivel {
    public AguaSalgada() {
        super("ÁguaSalgada", 0x00FFFF, 5, false, true, false, false);
    }

    @Override
    public boolean podeReagir(Elemento elemento) {
        return elemento instanceof Fogo || elemento instanceof Lava;
    }

    @Override
    public Elemento[] pegarResultado(Elemento elemento) {
        return new Elemento[]{new Vapor(), new Sal()};
    }
}