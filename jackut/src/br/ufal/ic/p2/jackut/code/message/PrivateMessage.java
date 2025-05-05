package br.ufal.ic.p2.jackut.code.message;

import br.ufal.ic.p2.jackut.code.user.User;

/**
 * Representa uma mensagem privada enviada de um usu�rio para outro.
 */
public class PrivateMessage implements Message {
    private User sender;
    private User receiver;
    private String message;

    /**
     * Construtor da classe PrivateMessage.
     *
     * @param sender o usu�rio que envia a mensagem
     * @param receiver o usu�rio que recebe a mensagem
     * @param message o conte�do da mensagem
     */
    public PrivateMessage(User sender, User receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public User getSender() {
        return this.sender;
    }

    @Override
    public void send() {
        receiver.receivePrivateMessage(this);
    }
}
