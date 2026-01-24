package elementos.liquido;

import core.Elemento;
import core.IReagivel;
import elementos.Metal;
import elementos.Vapor;

// Metal liquido
public class MetalDerretido extends Elemento implements IReagivel {
    public MetalDerretido() {
        super("MetalDerretido", 0x606060, 10, false, true, false, false);
    }

    @Override
    public boolean podeReagir(Elemento elemento) {
        return elemento instanceof Agua;
    }

    @Override
    public Elemento[] pegarResultado(Elemento elemento) {
        return new Elemento[]{ new Metal(), new Vapor()};
    }
}