package br.ufal.ic.p2.jackut.exceptions.message;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando o usu�rio n�o tem mensagens recebidas de uma comunidade.
 */
public class NoCommunityMessageException extends AbstractException {
    public NoCommunityMessageException() {
        super("N�o h� mensagens.");
    }
}
