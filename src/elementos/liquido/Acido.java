package elementos.liquido;

import core.Elemento;

// Líquido que derrete
public class Acido extends Elemento {
    public Acido() {
        super("Ácido", 0x00FF00, 4, false, true, false, false);
    }
}