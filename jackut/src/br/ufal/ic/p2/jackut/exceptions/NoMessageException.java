package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando se tenta ler os recados recebidos e o mesmo se encontra sem recados.
 */
public class NoMessageException extends AbstractException {
    public NoMessageException() {
        super("N�o h� recados.");
    }
}
