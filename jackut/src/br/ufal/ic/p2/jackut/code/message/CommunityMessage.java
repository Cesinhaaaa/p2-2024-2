package br.ufal.ic.p2.jackut.code.message;

import br.ufal.ic.p2.jackut.code.community.Community;
import br.ufal.ic.p2.jackut.code.user.User;

/**
 * Representa uma mensagem enviada para uma comunidade.
 * A mensagem � enviada a todos os membros da comunidade.
 */
public class CommunityMessage implements Message {
    private User sender;
    private Community community;
    private String message;

    /**
     * Construtor da classe CommunityMessage.
     *
     * @param sender o usu�rio que est� enviando a mensagem
     * @param community a comunidade para a qual a mensagem ser� enviada
     * @param message o conte�do da mensagem
     */
    public CommunityMessage(User sender, Community community, String message) {
        this.sender = sender;
        this.community = community;
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
        for (User user : community.getMemberList()) {
            user.receiveCommunityMessage(this);
        }
    }
}
