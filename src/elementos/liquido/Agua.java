package elementos.liquido;

import core.Elemento;
import core.IReagivel;
import elementos.*;

// Líquido  que apaga fogo
public class Agua extends Elemento implements IReagivel {
    public Agua() {
        super("Água", 0x0000FF, 5, false, true, false, false);
    }

    @Override
    public boolean podeReagir(Elemento elemento) {
        return elemento instanceof Lava || elemento instanceof Fogo || elemento instanceof Eletricidade || elemento instanceof Sal;
    }

    @Override
    public Elemento[] pegarResultado(Elemento elemento) {
        if (elemento instanceof Fogo)
            return new Elemento[]{new Vapor(), new Ar()};
        if (elemento instanceof Eletricidade)
            return new Elemento[]{new Eletricidade(), new Agua()};
        if (elemento instanceof Sal)
            return new Elemento[]{new AguaSalgada(), new Ar()};
        return new Elemento[]{new Pedra(), new Vapor()};
    }
}
