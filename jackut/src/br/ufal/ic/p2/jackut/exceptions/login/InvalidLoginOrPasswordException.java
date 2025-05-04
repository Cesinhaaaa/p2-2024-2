package br.ufal.ic.p2.jackut.exceptions.login;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando a tentativa de login falha.
 */
public class InvalidLoginOrPasswordException extends AbstractException {
    public InvalidLoginOrPasswordException() {
        super("Login ou senha inv�lidos.");
    }
}
