package core;

import elementos.*;

import java.util.ArrayList;
import java.util.List;

/*
 * =========================
 * GERENCIADOR DE REAÇÕES
 * =========================
 */

public class GerenciadorReacoes {


        /**
         * Pega dois elementos e retorna a reação entre eles caso tenha uma.
         *
         * @param elemento1 primeiro elemento
         * @param elemento2 segundo elemento
         * @return Resultado em um array com dois valores, array virá vazio caso não tenha consigo causar uma reação.
         */
        public Elemento[] tryReact(Elemento elemento1, Elemento elemento2) {
                if (elemento1 instanceof IReagivel reagivel && reagivel.podeReagir(elemento2)) {
                        return reagivel.pegarResultado(elemento2);
                } else if (elemento2 instanceof IReagivel reagivel && reagivel.podeReagir(elemento1)) {
                        return reagivel.pegarResultado(elemento1);
                }
                return new Elemento[2];
        }
}
