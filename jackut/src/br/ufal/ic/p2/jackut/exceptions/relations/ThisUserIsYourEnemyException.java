package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando voc� tenta interagir com um usu�rio que o declarou como inimigo.
 */
public class ThisUserIsYourEnemyException extends AbstractException {
    public ThisUserIsYourEnemyException(String userName) {
        super("Fun��o inv�lida: " + userName + " � seu inimigo.");
    }
}
