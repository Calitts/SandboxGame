package element;


// ELEMENTO BASE

abstract class Elemento {

    public String nome;
    public int cor;
    public int peso; // 0 = sem gravidade | 10 = muito pesado
    public boolean solido;
    public boolean liquido;
    public boolean gasoso;
    public boolean inflamavel;

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
        return (solido || liquido) && peso > 0;
    }

    public int getCor() {
        return this.cor;
    }
}