package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando o usuário tenta adicionar a si mesmo commo paquera.
 */
public class YourselfPaqueraException extends AbstractException {
    public YourselfPaqueraException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
}
