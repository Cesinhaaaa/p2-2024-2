package br.ufal.ic.p2.jackut.exceptions.message;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando se tenta ler os recados recebidos e o mesmo se encontra sem recados.
 */
public class NoPrivateMessageException extends AbstractException {
    public NoPrivateMessageException() {
        super("N�o h� recados.");
    }
}
