package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando se tenta criar uma conta com um Login inválido.
 */
public class InvalidLoginException extends AbstractException {
    public InvalidLoginException() {
        super("Login inválido.");
    }
}
