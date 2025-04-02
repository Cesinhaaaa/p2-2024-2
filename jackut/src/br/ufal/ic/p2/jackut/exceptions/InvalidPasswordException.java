package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando se tenta criar um usuário com uma senha inválida.
 */
public class InvalidPasswordException extends AbstractException {
    public InvalidPasswordException() {
        super("Senha inválida.");
    }
}
