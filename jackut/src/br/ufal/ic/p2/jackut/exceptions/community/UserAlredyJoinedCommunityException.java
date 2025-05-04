package br.ufal.ic.p2.jackut.exceptions.community;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando um usuário tenta entrar em uma comunidade da qual já faz parte.
 */
public class UserAlredyJoinedCommunityException extends AbstractException {
    public UserAlredyJoinedCommunityException() {
        super("Usuario já faz parte dessa comunidade.");
    }
}
