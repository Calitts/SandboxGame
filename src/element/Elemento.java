package element;
import java.util.ArrayList;
import java.util.List;


  // ELEMENTO BASE
   
abstract class Elemento {

    public String nome;
    public int cor;
    public int peso;          // 0 = sem gravidade | 10 = muito pesado
    public boolean liquido;
    public boolean gasoso;
    public boolean inflamavel;

    public Elemento(String nome, int cor, int peso,
                    boolean liquido, boolean gasoso,
                    boolean inflamavel) {

        this.nome = nome;
        this.cor = cor;
        this.peso = peso;
        this.liquido = liquido;
        this.gasoso = gasoso;
        this.inflamavel = inflamavel;
    }

    public boolean sofreGravidade() {
        return peso > 0 && !gasoso;
    }

    public int getCor() {
        return this.cor;
    }
}


   //ELEMENTOS
   

// Queima elementos inflamáveis
class Fogo extends Elemento {
    public Fogo() {
        super("Fogo", 0xFF4500, 0, false, false, false);
    }
}

// Gás sem cor
class Ar extends Elemento {
    public Ar() {
        super("Ar", 0x000000, 0, false, true, false);
    }
}

// Gás leve 
class Vapor extends Elemento {
    public Vapor() {
        super("Vapor", 0xCCCCCC, 1, false, true, false);
    }
}

//  sólido e inflamável
class Madeira extends Elemento {
    public Madeira() {
        super("Madeira", 0x8B4513, 7, false, false, true);
    }
}

// Resultado da combustão
class Fumaca extends Elemento {
    public Fumaca() {
        super("Fumaça", 0x777777, 1, false, true, false);
    }
}

// Mistura de água e areia
class Lama extends Elemento {
    public Lama() {
        super("Lama", 0x6B4423, 6, true, false, false);
    }
}

// Sólido pesado e resistente
class Pedra extends Elemento {
    public Pedra() {
        super("Pedra", 0x555555, 9, false, false, false);
    }
}

// Rocha derretida
class Lava extends Elemento {
    public Lava() {
        super("Lava", 0xFF3300, 8, true, false, false);
    }
}

// Material formado da areia super-aquecida
class Vidro extends Elemento {
    public Vidro() {
        super("Vidro", 0x99FFFF, 8, false, false, false);
    }
}

// Resultado sólido da queima
class Cinzas extends Elemento {
    public Cinzas() {
        super("Cinzas", 0xAAAAAA, 2, false, false, false);
    }
}


   //LOGICA DE REAÇÕES
   

class Reacao {

    public Class<? extends Elemento> a; //pega determinada classe que participe da reação
    public Class<? extends Elemento> b;
    public Elemento resultadoA;
    public Elemento resultadoB;

    public Reacao(Class<? extends Elemento> a, 
                  Class<? extends Elemento> b,
                  Elemento resultadoA,        //elemento resultante da reação
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

/* =========================
   GERENCIADOR DE REAÇÕES
   ========================= */

class GerenciadorReacoes {

    private static final List<Reacao> reacoes = new ArrayList<>();

    static {
        // Fogo + Madeira → Fogo + Fumaça
        reacoes.add(new Reacao(
                Fogo.class, Madeira.class,
                new Fogo(), new Fumaca()
        ));

        // Fogo + Água → Vapor + Ar
        reacoes.add(new Reacao(
                Fogo.class, Agua.class,
                new Vapor(), new Ar()
        ));

        // Água + Areia → Lama
        reacoes.add(new Reacao(
                Agua.class, Areia.class,
                new Lama(), new Lama()
        ));

        // Lava + Água → Pedra + Vapor
        reacoes.add(new Reacao(
                Lava.class, Agua.class,
                new Pedra(), new Vapor()
        ));

        // Lava + Areia → Vidro
        reacoes.add(new Reacao(
                Lava.class, Areia.class,
                new Vidro(), new Vidro()
        ));
    }

    // Método que o JOGO vai chamar
    public static Elemento[] processar(Elemento e1, Elemento e2) {

        for (Reacao r : reacoes) {
            if (r.combina(e1, e2)) {
                return new Elemento[]{
                        r.resultadoA,
                        r.resultadoB
                };
            }
        }

        return new Elemento[]{ e1, e2 };
    }
}

