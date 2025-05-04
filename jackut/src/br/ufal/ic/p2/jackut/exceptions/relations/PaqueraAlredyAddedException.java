package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando um usu�rio tenta adicionar uma paquera j� adicionada.
 */
public class PaqueraAlredyAddedException extends AbstractException {
    public PaqueraAlredyAddedException() {
        super("Usu�rio j� est� adicionado como paquera.");
    }
}
