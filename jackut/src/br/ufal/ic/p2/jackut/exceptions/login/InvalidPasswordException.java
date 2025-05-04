package br.ufal.ic.p2.jackut.exceptions.login;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando se tenta criar um usuário com uma senha inválida.
 */
public class InvalidPasswordException extends AbstractException {
    public InvalidPasswordException() {
        super("Senha inválida.");
    }
}
