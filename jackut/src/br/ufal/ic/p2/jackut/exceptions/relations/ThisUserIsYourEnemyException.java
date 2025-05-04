package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando você tenta interagir com um usuário que o declarou como inimigo.
 */
public class ThisUserIsYourEnemyException extends AbstractException {
    public ThisUserIsYourEnemyException(String userName) {
        super("Função inválida: " + userName + " é seu inimigo.");
    }
}
