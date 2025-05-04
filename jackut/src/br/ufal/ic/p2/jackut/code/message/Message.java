package br.ufal.ic.p2.jackut.code.message;

import br.ufal.ic.p2.jackut.code.user.User;

import java.io.Serializable;

/**
 * Classe abstrata que representa uma mensagem enviada por um usu�rio.
 * Serve como base para diferentes tipos de mensagens no sistema.
 */
public abstract class Message implements Serializable {
    private User sender;
    private String message;

    /**
     * Construtor da classe Message.
     *
     * @param sender o usu�rio que enviou a mensagem
     * @param message o conte�do da mensagem
     */
    public Message(User sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    /**
     * Retorna o conte�do da mensagem.
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
     * M�todo abstrato que define como a mensagem ser� enviada.
     * Deve ser implementado pelas subclasses.
     */
    public abstract void send();
}

