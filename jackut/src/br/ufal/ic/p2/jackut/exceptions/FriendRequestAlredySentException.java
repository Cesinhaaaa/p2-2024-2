package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando a solicita��o de amizade j� foi enviada.
 */
public class FriendRequestAlredySentException extends AbstractException {
    public FriendRequestAlredySentException() {
        super("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
    }
}
