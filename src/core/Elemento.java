package core;


// ELEMENTO BASE

public abstract class Elemento {

    private final String nome;
    private final int cor;
    public final int peso; // 0 = sem gravidade | 10 = muito pesado
    public int densidade;
    public final boolean solido;
    public final boolean liquido;
    public final boolean gasoso;
    public final boolean inflamavel;

    public Elemento(String nome, int cor, int peso, boolean solido,
            boolean liquido, boolean gasoso,
            boolean inflamavel) {

        this.nome = nome;
        this.cor = cor;
        this.peso = peso;
        this.solido = solido;
        this.liquido = liquido;
        this.gasoso = gasoso;
        this.inflamavel = inflamavel;
    }

    public boolean sofreGravidade() {
        return !gasoso && peso > 0;
    }

    public int getCor() {
        return cor;
    }

    public String getName() {
        return nome;
    }
}