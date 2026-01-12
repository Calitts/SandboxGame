package src;

import java.util.ArrayList;
import java.util.List;


  // ELEMENTO BASE
   
abstract class Elemento {

    public String nome;
    public int cor;
    public int peso;   // 0 = sem gravidade | 10 = muito pesado
    public boolean solido;
    public boolean liquido;
    public boolean gasoso;
    public boolean inflamavel;

    public Elemento(String nome, int cor, int peso,boolean solido,
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
}



//ELEMENTOS
   
// Líquido  que apaga fogo
class Agua extends Elemento {
    public Agua() {
        super("Água", 0x0000FF, 5, false, true, false, false);
    }
}

// Elemento granuloso que cai
class Areia extends Elemento {
    public Areia() {
        super("Areia", 0xFFFF00, 6, true, false, false, false);
    }
}

// Queima elementos inflamáveis
class Fogo extends Elemento {
    public Fogo() {
        super("Fogo", 0xFF4500, 0, false, false, false, false);
    }
}

// Gás sem cor
class Ar extends Elemento {
    public Ar() {
        super("Ar", 0x000000, 0, false, false, true, false);
    }
}

// Gás leve 
class Vapor extends Elemento {
    public Vapor() {
        super("Vapor", 0xCCCCCC, 1, false, false, true, false);
    }
}

//  sólido e inflamável
class Madeira extends Elemento {
    public Madeira() {
        super("Madeira", 0x8B4513, 7,true,  false, false, true);
    }
}

// Resultado da combustão
class Fumaca extends Elemento {
    public Fumaca() {
        super("Fumaça", 0x777777, 1, false, false, true, false);
    }
}

// Mistura de água e areia
class Lama extends Elemento {
    public Lama() {
        super("Lama", 0x6B4423, 6, true, true, false, false);
    }
}

// Sólido pesado e resistente
class Pedra extends Elemento {
    public Pedra() {
        super("Pedra", 0x555555, 9, true, false, false, false);
    }
}

// Rocha derretida
class Lava extends Elemento {
    public Lava() {
        super("Lava", 0xFF3300, 8, true, true, false, false);
    }
}

// Material formado da areia super-aquecida
class Vidro extends Elemento {
    public Vidro() {
        super("Vidro", 0x99FFFF, 8, true, false, false, false);
    }
}

// Resultado sólido da queima
class Cinzas extends Elemento {
    public Cinzas() {
        super("Cinzas", 0xAAAAAA, 2, true, false, false, false);
    }
}
  // Líquido inflamável
class Oleo extends Elemento {
    public Oleo() { super("Óleo", 0xFFD700, 5, false,  true, false, true); }
}


// Elemento elétrico
class Eletricidade extends Elemento {
    public Eletricidade() { super("Eletricidade", 0xFFFFAA, 0, false, false, true, false); }
}


// Elemento congelado
class Neve extends Elemento {
    public Neve() { super("Neve", 0xFFFFFF, 2, true, false, false, false); }
}


// Metal sólido pesado
class Metal extends Elemento {
    public Metal() { super("Metal", 0x808080, 10, true, false, false, false); }
}

// Líquido  que derrete
class Acido extends Elemento {
    public Acido() {
        super("Ácido", 0x00FF00, 4, false, true, false, false);
    }
}

// Líquido instiga fogo
class Alcool extends Elemento {
    public Alcool() {
        super("Álcool", 0xC0C0C0, 3, false, true, false, true);
    }
}
// solido
class Sal extends Elemento {
    public Sal() {
        super("Sal", 0xFF9999, 6, true, false, false, false);
    }
}
// Líquido salgado
class AguaSalgada extends Elemento {
    public AguaSalgada() {
        super("ÁguaSalgada", 0x00FFFF, 5, false, true, false, false);
    }
}
// Metal liquido
class MetalDerretido extends Elemento {
    public MetalDerretido() { super("MetalDerretido", 0x606060, 10, false, true, false, false); }
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

       // Fogo + Fumaça → Cinzas
    reacoes.add(new Reacao(
            Fogo.class, Fumaca.class,
            new Cinzas(), new Ar()
    ));
      // Fogo + Oleo → Propagacao
    reacoes.add(new Reacao(
            Fogo.class, Oleo.class,
            new Fogo(), new Fogo()
    ));

      // Eletricidade + Agua → Propagacao
    reacoes.add(new Reacao(
            Eletricidade.class, Agua.class,
            new Eletricidade(), new Agua()
    ));

      // Eletricidade + Oleo → curto circuito
    reacoes.add(new Reacao(
            Eletricidade.class, Oleo.class,
            new Fogo(), new Oleo()
    ));

      // Neve + Lava → Agua + Vapor
    reacoes.add(new Reacao(
            Neve.class, Lava.class,
            new Agua(), new Vapor()
    ));

      // Fogo + Neve → Agua
    reacoes.add(new Reacao(
            Fogo.class, Neve.class,
            new Agua(), new Vapor()
    ));

      // Fogo + Metal → MetalDerretido + Fumaca
        reacoes.add(new Reacao(
                Fogo.class, Metal.class,
                new MetalDerretido(), new Fumaca()
        ));
      
       // Ácido + Metal → MetalDerretido + sal
        reacoes.add(new Reacao(
                Acido.class, Metal.class,
                new MetalDerretido(), new Sal()
      ));

        // Fogo + Álcool → Propagacao
    reacoes.add(new Reacao(
            Fogo.class, Alcool.class,
            new Fogo(), new Fogo()
    ));
        // Agua + Sal → Agua salgada
    reacoes.add(new Reacao(
            Agua.class, Sal.class,
            new AguaSalgada(), new AguaSalgada()
    ));
        // Neve + Acido → Agua + Ar
    reacoes.add(new Reacao(
            Neve.class, Acido.class,
            new Agua(), new Ar()
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

