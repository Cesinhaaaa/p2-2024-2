package br.ufal.ic.p2.jackut.exceptions.login;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando se tenta criar uma conta com um Login inv�lido.
 */
public class InvalidLoginException extends AbstractException {
    public InvalidLoginException() {
        super("Login inv�lido.");
    }
}
