package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando um usu�rio tenta adicionar a si mesmo como inimigo.
 */
public class YourselfEnemyException extends AbstractException {
    public YourselfEnemyException() {
        super("Usu�rio n�o pode ser inimigo de si mesmo.");
    }
}
