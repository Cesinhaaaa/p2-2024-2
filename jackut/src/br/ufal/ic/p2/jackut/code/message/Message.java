package br.ufal.ic.p2.jackut.code.message;

import br.ufal.ic.p2.jackut.code.user.User;
import java.io.Serializable;

/**
 * Interface que representa uma mensagem enviada por um usuário.
 * Define os comportamentos essenciais para diferentes tipos de mensagens.
 */
public interface Message extends Serializable {

    /**
     * Retorna o conteúdo da mensagem.
     *
     * @return a mensagem como String
     */
    String getMessage();

    /**
     * Retorna o remetente da mensagem.
     *
     * @return o objeto User que enviou a mensagem
     */
    User getSender();

    /**
     * Método para enviar a mensagem.
     * Implementado de forma diferente para cada tipo de mensagem.
     */
    void send();
}
