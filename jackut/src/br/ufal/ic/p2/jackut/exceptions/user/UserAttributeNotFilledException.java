package br.ufal.ic.p2.jackut.exceptions.user;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando se acessa o atributo de um usuário no qual não foi preenchido.
 */
public class UserAttributeNotFilledException extends AbstractException {
    public UserAttributeNotFilledException() {
        super("Atributo não preenchido.");
    }
}
