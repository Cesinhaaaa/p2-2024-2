package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando se tenta declarar um inimigo que já foi declarado.
 */
public class EnemyAlredyDeclaredException extends AbstractException {
    public EnemyAlredyDeclaredException() {
        super("Usuário já está adicionado como inimigo.");
    }
}
