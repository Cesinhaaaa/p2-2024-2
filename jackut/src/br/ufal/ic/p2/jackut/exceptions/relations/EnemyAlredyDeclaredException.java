package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando se tenta declarar um inimigo que j� foi declarado.
 */
public class EnemyAlredyDeclaredException extends AbstractException {
    public EnemyAlredyDeclaredException() {
        super("Usu�rio j� est� adicionado como inimigo.");
    }
}
