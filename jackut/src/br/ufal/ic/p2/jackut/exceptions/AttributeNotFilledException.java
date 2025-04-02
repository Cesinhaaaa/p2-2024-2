package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando se acessa o atributo de um usuário no qual não foi preenchido.
 */
public class AttributeNotFilledException extends AbstractException {
    public AttributeNotFilledException() {
        super("Atributo não preenchido.");
    }
}
