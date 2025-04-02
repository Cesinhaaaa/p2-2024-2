package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando se tenta criar um usu�rio com uma senha inv�lida.
 */
public class InvalidPasswordException extends AbstractException {
    public InvalidPasswordException() {
        super("Senha inv�lida.");
    }
}
