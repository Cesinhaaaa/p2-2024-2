package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando se tenta adicionar a si mesmo como amigo.
 */
public class YourselfFriendRequestException extends AbstractException {
    public YourselfFriendRequestException() {
        super("Usu�rio n�o pode adicionar a si mesmo como amigo.");
    }
}
