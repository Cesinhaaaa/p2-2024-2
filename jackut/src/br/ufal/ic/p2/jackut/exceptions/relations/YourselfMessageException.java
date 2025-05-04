package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando se tenta enviar um recado para si mesmo.
 */
public class YourselfMessageException extends AbstractException {
    public YourselfMessageException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}
