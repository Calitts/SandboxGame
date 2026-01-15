package elementos.liquido;

import core.Elemento;
import core.IReagivel;
import elementos.Fogo;
import elementos.Sal;
import elementos.Vapor;

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