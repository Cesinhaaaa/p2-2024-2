package br.ufal.ic.p2.jackut.code.message;

import br.ufal.ic.p2.jackut.code.user.User;

/**
 * Representa uma mensagem privada enviada de um usuário para outro.
 */
public class PrivateMessage extends Message {
    private User receiver;

    /**
     * Construtor da classe PrivateMessage.
     *
     * @param sender o usuário que envia a mensagem
     * @param receiver o usuário que recebe a mensagem
     * @param message o conteúdo da mensagem
     */
    public PrivateMessage(User sender, User receiver, String message) {
        super(sender, message);
        this.receiver = receiver;
    }

    /**
     * Envia a mensagem privada para o destinatário.
     * A entrega é feita através do método receivePrivateMessage do usuário receptor.
     */
    @Override
    public void send() {
        receiver.receivePrivateMessage(this);
    }
}
