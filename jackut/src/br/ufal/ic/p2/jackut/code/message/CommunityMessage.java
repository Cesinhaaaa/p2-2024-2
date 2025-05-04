package br.ufal.ic.p2.jackut.code.message;

import br.ufal.ic.p2.jackut.code.community.Community;
import br.ufal.ic.p2.jackut.code.user.User;

/**
 * Representa uma mensagem enviada para uma comunidade.
 * A mensagem é enviada a todos os membros da comunidade.
 */
public class CommunityMessage extends Message {
    private Community community;

    /**
     * Construtor da classe CommunityMessage.
     *
     * @param sender o usuário que está enviando a mensagem
     * @param community a comunidade para a qual a mensagem será enviada
     * @param message o conteúdo da mensagem
     */
    public CommunityMessage(User sender, Community community, String message) {
        super(sender, message);
        this.community = community;
    }

    /**
     * Envia a mensagem para todos os membros da comunidade.
     * Cada membro recebe a mensagem através do método receiveCommunityMessage.
     */
    @Override
    public void send() {
        for (User user : community.getMemberList()) {
            user.receiveCommunityMessage(this);
        }
    }
}
