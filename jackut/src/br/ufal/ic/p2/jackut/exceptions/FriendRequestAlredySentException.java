package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando a solicitação de amizade já foi enviada.
 */
public class FriendRequestAlredySentException extends AbstractException {
    public FriendRequestAlredySentException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
