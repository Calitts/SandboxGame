package element;

import java.util.ArrayList;
import java.util.List;

/*
 * =========================
 * GERENCIADOR DE REAÇÕES
 * =========================
 */

class GerenciadorReacoes {

    private static final List<Reacao> reacoes = new ArrayList<>();

    static {
        // Fogo + Madeira → Fogo + Fumaça
        reacoes.add(new Reacao(
                Fogo.class, Madeira.class,
                new Fogo(), new Fumaca()));

        // Fogo + Água → Vapor + Ar
        reacoes.add(new Reacao(
                Fogo.class, Agua.class,
                new Vapor(), new Ar()));

        // Água + Areia → Lama
        reacoes.add(new Reacao(
                Agua.class, Areia.class,
                new Lama(), new Lama()));

        // Lava + Água → Pedra + Vapor
        reacoes.add(new Reacao(
                Lava.class, Agua.class,
                new Pedra(), new Vapor()));

        // Lava + Areia → Vidro
        reacoes.add(new Reacao(
                Lava.class, Areia.class,
                new Vidro(), new Vidro()));

        // Fogo + Fumaça → Cinzas
        reacoes.add(new Reacao(
                Fogo.class, Fumaca.class,
                new Cinzas(), new Ar()));
        // Fogo + Oleo → Propagacao
        reacoes.add(new Reacao(
                Fogo.class, Oleo.class,
                new Fogo(), new Fogo()));

        // Eletricidade + Agua → Propagacao
        reacoes.add(new Reacao(
                Eletricidade.class, Agua.class,
                new Eletricidade(), new Agua()));

        // Eletricidade + Oleo → curto circuito
        reacoes.add(new Reacao(
                Eletricidade.class, Oleo.class,
                new Fogo(), new Oleo()));

        // Neve + Lava → Agua + Vapor
        reacoes.add(new Reacao(
                Neve.class, Lava.class,
                new Agua(), new Vapor()));

        // Fogo + Neve → Agua
        reacoes.add(new Reacao(
                Fogo.class, Neve.class,
                new Agua(), new Vapor()));

        // Fogo + Metal → MetalDerretido + Fumaca
        reacoes.add(new Reacao(
                Fogo.class, Metal.class,
                new MetalDerretido(), new Fumaca()));

        // Ácido + Metal → MetalDerretido + sal
        reacoes.add(new Reacao(
                Acido.class, Metal.class,
                new MetalDerretido(), new Sal()));

        // Fogo + Álcool → Propagacao
        reacoes.add(new Reacao(
                Fogo.class, Alcool.class,
                new Fogo(), new Fogo()));
        // Agua + Sal → Agua salgada
        reacoes.add(new Reacao(
                Agua.class, Sal.class,
                new AguaSalgada(), new AguaSalgada()));
        // Neve + Acido → Agua + Ar
        reacoes.add(new Reacao(
                Neve.class, Acido.class,
                new Agua(), new Ar()));
    }

    // Método que o JOGO vai chamar
    public static Elemento[] processar(Elemento e1, Elemento e2) {

        for (Reacao r : reacoes) {
            if (r.combina(e1, e2)) {
                return new Elemento[] {
                        r.resultadoA,
                        r.resultadoB
                };
            }
        }

        return new Elemento[] { e1, e2 };
    }
}
