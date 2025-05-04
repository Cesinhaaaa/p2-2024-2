package br.ufal.ic.p2.jackut.exceptions.message;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando o usuário não tem mensagens recebidas de uma comunidade.
 */
public class NoCommunityMessageException extends AbstractException {
    public NoCommunityMessageException() {
        super("Não há mensagens.");
    }
}
