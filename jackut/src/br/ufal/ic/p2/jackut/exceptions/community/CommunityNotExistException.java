package br.ufal.ic.p2.jackut.exceptions.community;

/**
 * Exce��o lan�ada quando se tenta acessar um atributo de uma comunidade que n�o existe
 */
public class CommunityNotExistException extends RuntimeException {
    public CommunityNotExistException() {
        super("Comunidade n�o existe.");
    }
}
