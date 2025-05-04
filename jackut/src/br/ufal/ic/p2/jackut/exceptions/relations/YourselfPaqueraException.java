package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando o usu�rio tenta adicionar a si mesmo commo paquera.
 */
public class YourselfPaqueraException extends AbstractException {
    public YourselfPaqueraException() {
        super("Usu�rio n�o pode ser paquera de si mesmo.");
    }
}
