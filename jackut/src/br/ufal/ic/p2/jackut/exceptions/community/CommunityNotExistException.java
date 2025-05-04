package br.ufal.ic.p2.jackut.exceptions.community;

/**
 * Exceção lançada quando se tenta acessar um atributo de uma comunidade que não existe
 */
public class CommunityNotExistException extends RuntimeException {
    public CommunityNotExistException() {
        super("Comunidade não existe.");
    }
}
