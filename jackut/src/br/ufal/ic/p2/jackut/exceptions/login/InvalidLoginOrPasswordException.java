package br.ufal.ic.p2.jackut.exceptions.login;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando a tentativa de login falha.
 */
public class InvalidLoginOrPasswordException extends AbstractException {
    public InvalidLoginOrPasswordException() {
        super("Login ou senha inválidos.");
    }
}
