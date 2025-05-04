package br.ufal.ic.p2.jackut.exceptions.login;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando se tenta criar um usu�rio com um login j� existente.
 */
public class LoginAlredyUsedException extends AbstractException {
    public LoginAlredyUsedException() {
        super("Conta com esse nome j� existe.");
    }
}
