package br.ufal.ic.p2.jackut.code.message;

import br.ufal.ic.p2.jackut.code.user.User;

/**
 * Representa uma mensagem privada enviada de um usu�rio para outro.
 */
public class PrivateMessage extends Message {
    private User receiver;

    /**
     * Construtor da classe PrivateMessage.
     *
     * @param sender o usu�rio que envia a mensagem
     * @param receiver o usu�rio que recebe a mensagem
     * @param message o conte�do da mensagem
     */
    public PrivateMessage(User sender, User receiver, String message) {
        super(sender, message);
        this.receiver = receiver;
    }

    /**
     * Envia a mensagem privada para o destinat�rio.
     * A entrega � feita atrav�s do m�todo receivePrivateMessage do usu�rio receptor.
     */
    @Override
    public void send() {
        receiver.receivePrivateMessage(this);
    }
}
