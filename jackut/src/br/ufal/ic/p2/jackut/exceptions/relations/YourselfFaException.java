package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exce��o lan�ada quando o usu�rio tenta adicionar a si mesmo como idolo.
 */
public class YourselfFaException extends AbstractException {
  public YourselfFaException() {
    super("Usu�rio n�o pode ser f� de si mesmo.");
  }
}
