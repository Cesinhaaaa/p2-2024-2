package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

public class UserAlredyIsIdolException extends AbstractException {
    public UserAlredyIsIdolException() {
        super("Usu�rio j� est� adicionado como �dolo.");
    }
}
