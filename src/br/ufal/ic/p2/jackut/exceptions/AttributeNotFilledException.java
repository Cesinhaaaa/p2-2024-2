package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando se acessa o atributo de um usu�rio no qual n�o foi preenchido.
 */
public class AttributeNotFilledException extends AbstractException {
    public AttributeNotFilledException() {
        super("Atributo n�o preenchido.");
    }
}
