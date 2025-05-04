package br.ufal.ic.p2.jackut.exceptions.community;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando se tenta criar uma comunidade com o nome de uma comunidade j� existente.
 */
public class CommunityAlredyExistException extends AbstractException {
    public CommunityAlredyExistException() {
        super("Comunidade com esse nome j� existe.");
    }
}
