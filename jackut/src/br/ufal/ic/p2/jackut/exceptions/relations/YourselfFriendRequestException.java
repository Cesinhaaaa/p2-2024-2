package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando se tenta adicionar a si mesmo como amigo.
 */
public class YourselfFriendRequestException extends AbstractException {
    public YourselfFriendRequestException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
