package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando se tenta criar um usuário com um login já existente.
 */
public class LoginAlredyUsedException extends AbstractException {
    public LoginAlredyUsedException() {
        super("Conta com esse nome já existe.");
    }
}
