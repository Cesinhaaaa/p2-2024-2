package br.ufal.ic.p2.jackut.exceptions.relations;

import br.ufal.ic.p2.jackut.exceptions.AbstractException;

/**
 * Exceção lançada quando o usuário tenta adicionar a si mesmo como idolo.
 */
public class YourselfFaException extends AbstractException {
  public YourselfFaException() {
    super("Usuário não pode ser fã de si mesmo.");
  }
}
