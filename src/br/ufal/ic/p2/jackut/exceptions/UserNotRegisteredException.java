package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando se busca por um usu�rio no qual n�o existe no sistema.
 */
public class UserNotRegisteredException extends AbstractException {
    public UserNotRegisteredException() {
        super("Usu�rio n�o cadastrado.");
    }
}
