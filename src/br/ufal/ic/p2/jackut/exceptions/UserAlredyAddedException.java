package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando se tenta enviar uma solicita��o de amizade para um Usu�rio que j� est� adicionado como amigo.
 */
public class UserAlredyAddedException extends AbstractException {
    public UserAlredyAddedException() {
        super("Usu�rio j� est� adicionado como amigo.");
    }
}
