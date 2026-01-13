
package element;

// LOGICA DE REAÇÕES

class Reacao {

    public Class<? extends Elemento> a; // pega determinada classe que participe da reação
    public Class<? extends Elemento> b;
    public Elemento resultadoA;
    public Elemento resultadoB;

    public Reacao(Class<? extends Elemento> a,
            Class<? extends Elemento> b,
            Elemento resultadoA, // elemento resultante da reação
            Elemento resultadoB) {

        this.a = a;
        this.b = b;
        this.resultadoA = resultadoA;
        this.resultadoB = resultadoB;
    }

    // Verifica os elementos desta reação
    public boolean combina(Elemento e1, Elemento e2) {
        return (a.isInstance(e1) && b.isInstance(e2)) ||
                (a.isInstance(e2) && b.isInstance(e1));
    }
}