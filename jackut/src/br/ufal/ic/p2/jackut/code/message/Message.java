package br.ufal.ic.p2.jackut.code.message;

import br.ufal.ic.p2.jackut.code.user.User;

import java.io.Serializable;

/**
 * Classe abstrata que representa uma mensagem enviada por um usuário.
 * Serve como base para diferentes tipos de mensagens no sistema.
 */
public abstract class Message implements Serializable {
    private User sender;
    private String message;

    /**
     * Construtor da classe Message.
     *
     * @param sender o usuário que enviou a mensagem
     * @param message o conteúdo da mensagem
     */
    public Message(User sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    /**
     * Retorna o conteúdo da mensagem.
     *
     * @return a mensagem como String
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Retorna o remetente da mensagem.
     *
     * @return o objeto User que enviou a mensagem
     */
    public User getSender() {
        return this.sender;
    }

    /**
     * Método abstrato que define como a mensagem será enviada.
     * Deve ser implementado pelas subclasses.
     */
    public abstract void send();
}

