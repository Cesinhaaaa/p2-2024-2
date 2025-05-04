package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando um usuário tenta adicionar uma paquera já adicionada.
 */
public class PaqueraAlredyAddedException extends AbstractException {
    public PaqueraAlredyAddedException() {
        super("Usuário já está adicionado como paquera.");
    }
}
