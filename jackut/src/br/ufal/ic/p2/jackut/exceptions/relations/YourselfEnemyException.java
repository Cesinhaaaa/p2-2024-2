package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando um usuário tenta adicionar a si mesmo como inimigo.
 */
public class YourselfEnemyException extends AbstractException {
    public YourselfEnemyException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }
}
