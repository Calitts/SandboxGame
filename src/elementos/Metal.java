package elementos;

import core.Elemento;
import core.IReagivel;
import elementos.liquido.Acido;
import elementos.liquido.Lava;
import elementos.liquido.MetalDerretido;

// Metal sólido pesado
public class Metal extends Elemento implements IReagivel {
    public Metal() {
        super("Metal", 0x808080, 10, true, false, false, false);
    }

    @Override
    public boolean podeReagir(Elemento elemento) {
        return elemento instanceof Lava || elemento instanceof Acido || elemento instanceof MetalDerretido;
    }

    @Override
    public Elemento[] pegarResultado(Elemento elemento) {
        return elemento instanceof Acido ? new Elemento[]{new Sal(), new MetalDerretido()} : new Elemento[]{new MetalDerretido(), new Fumaca()};
    }
}