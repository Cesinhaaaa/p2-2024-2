package br.ufal.ic.p2.jackut.exceptions.login;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando se busca por um usuário no qual não existe no sistema.
 */
public class UserNotRegisteredException extends AbstractException {
    public UserNotRegisteredException() {
        super("Usuário não cadastrado.");
    }
}
