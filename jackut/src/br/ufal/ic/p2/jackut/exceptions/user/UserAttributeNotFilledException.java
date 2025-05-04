package br.ufal.ic.p2.jackut.exceptions.user;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando se acessa o atributo de um usu�rio no qual n�o foi preenchido.
 */
public class UserAttributeNotFilledException extends AbstractException {
    public UserAttributeNotFilledException() {
        super("Atributo n�o preenchido.");
    }
}
