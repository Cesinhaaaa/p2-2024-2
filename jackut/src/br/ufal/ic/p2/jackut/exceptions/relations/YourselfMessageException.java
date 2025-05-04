package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando se tenta enviar um recado para si mesmo.
 */
public class YourselfMessageException extends AbstractException {
    public YourselfMessageException() {
        super("Usu�rio n�o pode enviar recado para si mesmo.");
    }
}
