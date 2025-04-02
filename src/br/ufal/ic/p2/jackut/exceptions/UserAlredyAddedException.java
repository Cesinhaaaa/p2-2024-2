package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando se tenta enviar uma solicitação de amizade para um Usuário que já está adicionado como amigo.
 */
public class UserAlredyAddedException extends AbstractException {
    public UserAlredyAddedException() {
        super("Usuário já está adicionado como amigo.");
    }
}
