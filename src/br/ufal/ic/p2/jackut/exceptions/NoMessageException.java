package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando se tenta ler os recados recebidos e o mesmo se encontra sem recados.
 */
public class NoMessageException extends AbstractException {
    public NoMessageException() {
        super("Não há recados.");
    }
}
