package br.ufal.ic.p2.jackut.exceptions.community;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando um usu�rio tenta entrar em uma comunidade da qual j� faz parte.
 */
public class UserAlredyJoinedCommunityException extends AbstractException {
    public UserAlredyJoinedCommunityException() {
        super("Usuario j� faz parte dessa comunidade.");
    }
}
