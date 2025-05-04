package br.ufal.ic.p2.jackut.code.user;

import br.ufal.ic.p2.jackut.code.message.Message;
import br.ufal.ic.p2.jackut.exceptions.login.InvalidLoginException;
import br.ufal.ic.p2.jackut.exceptions.login.InvalidLoginOrPasswordException;
import br.ufal.ic.p2.jackut.exceptions.login.InvalidPasswordException;
import br.ufal.ic.p2.jackut.exceptions.message.NoCommunityMessageException;
import br.ufal.ic.p2.jackut.exceptions.message.NoPrivateMessageException;
import br.ufal.ic.p2.jackut.exceptions.user.UserAttributeNotFilledException;
import br.ufal.ic.p2.jackut.code.relations.RelationsManager;

import java.io.Serializable;
import java.util.*;

/**
 * A classe User representa um usuário com login, senha, atributos de perfil,
 * relações (amizade, fa, paquera, inimigo) representado pelo gerenciador de relações
 * e 2 sistemas de mensagens (privado e comunidade). Ela implementa a interface
 * Serializable, permitindo que o objeto User seja serializado.
 */
public class User implements Serializable {
    private String login, password;
    private Map<String, String> profileAttributes;
    private List<Message> privateMessages;
    private List<Message> communityMessages;
    private List<String> communitys;
    private RelationsManager relations;

    /**
     * Constroi um novo objeto User com o login, senha e nome fornecidos.
     * Inicializa os atributos de perfil, lista de amigos, solicitações de amizade
     * e gerenciador de mensagens. Também define o atributo de perfil "nome" com o nome fornecido.
     *
     * @param login o nome de login do usuário. Não pode ser nulo ou vazio.
     * @param password a senha do usuário. Não pode ser nula ou vazia.
     * @param userName o nome do usuário, que será armazenado no atributo de perfil "nome".
     * @throws InvalidLoginException se o login for nulo ou vazio.
     * @throws InvalidPasswordException se a senha for nula ou vazia.
     */
    public User(String login, String password, String userName) throws InvalidLoginException, InvalidPasswordException {
        if (login == null || login.isEmpty()) throw new InvalidLoginException();
        if (password == null || password.isEmpty()) throw new InvalidPasswordException();

        this.login = login;
        this.password = password;
        this.profileAttributes = new TreeMap<String, String>();
        this.privateMessages = new ArrayList<>();
        this.communityMessages = new ArrayList<>();
        this.communitys = new ArrayList<>();
        this.relations = new RelationsManager();

        this.updateProfileAttribute("nome", userName);
    }

    /**
     * Retorna o login do usuário.
     *
     * @return o login do usuário como uma string.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Retorna a lista de comunidades.
     *
     * @return a lista de comunidades.
     */
    public List<String> getCommunitys() {
        return this.communitys;
    }

    /**
     * Retorna a lista de comunidades do usuário formatada como String.
     *
     * @return a lista de comunidades do usuário formatada como String.
     */
    public String getCommunitysAsString() {
        return "{" + String.join(",", this.communitys) + "}";
    }

    /**
     * Retorna o gerenciador de relações do usuário.
     *
     * @return o gerenciador de relações do usuário.
     */
    public RelationsManager getRelationManager() {
        return relations;
    }

    /**
     * Verifica se a senha fornecida corresponde à senha do usuário.
     *
     * @param password A senha a ser validada em relação à senha armazenada do usuário.
     * @return true se a senha fornecida for correta.
     * @throws InvalidLoginOrPasswordException Se a senha fornecida não corresponder à senha do usuário.
     */
    public boolean isCorrectPassword(String password) throws InvalidLoginOrPasswordException {
        if (password.equals(this.password)) {
            return true;
        } else {
            throw new InvalidLoginOrPasswordException();
        }
    }

    /**
     * Retorna o valor de um atributo específico do perfil do usuário.
     *
     * @param atribute o nome do atributo a ser recuperado.
     * @return o valor do atributo especificado.
     * @throws UserAttributeNotFilledException se o atributo não estiver presente ou não tiver sido preenchido.
     */
    public String getAttribute(String atribute) throws UserAttributeNotFilledException {
        String atributeGetted = this.profileAttributes.get(atribute);
        if (atributeGetted == null) {
            throw new UserAttributeNotFilledException();
        }
        return atributeGetted;
    }

    /**
     * Atualiza o valor de um atributo específico do perfil do usuário.
     *
     * @param atribute o nome do atributo de perfil a ser atualizado.
     * @param value o novo valor a ser definido para o atributo especificado.
     */
    public void updateProfileAttribute(String atribute, String value) {
        this.profileAttributes.put(atribute, value);
    }

    /**
     * Recebe uma mensagem de um usuário para outro.
     *
     * @param message o conteúdo da mensagem recebida.
     */
    public void receivePrivateMessage(Message message) {
        this.privateMessages.add(message);
    }

    /**
     * Recebe uma mensagem de um usuário para a comunidade.
     *
     * @param message o conteúdo da mensagem recebida.
     */
    public void receiveCommunityMessage(Message message) {
        this.communityMessages.add(message);
    }

    /**
     * Lê a primeira mensagem privada disponível para o usuário.
     * A mensagem recuperada é removida da lista de mensagens privadas.
     *
     * @return a primeira mensagem privada disponível para o usuário.
     * @throws NoPrivateMessageException se não houver mensagens disponíveis para leitura.
     */
    public String readPrivateMessage() throws NoPrivateMessageException {
        if (this.privateMessages.isEmpty()) {
            throw new NoPrivateMessageException();
        } else {
            Message message = this.privateMessages.get(0);
            this.privateMessages.remove(0);
            return message.getMessage();
        }
    }

    /**
     * Lê a primeira mensagem de uma comunidade disponível para o usuário.
     * A mensagem recuperada é removida da lista de mensagens de comunidades.
     *
     * @return a primeira mensagem de uma comunidade disponível para o usuário.
     * @throws NoCommunityMessageException se não houver mensagens disponíveis para leitura.
     */
    public String readCommunityMessage() throws NoCommunityMessageException {
        if (this.communityMessages.isEmpty()) {
            throw new NoCommunityMessageException();
        } else {
            Message message = this.communityMessages.get(0);
            this.communityMessages.remove(0);
            return message.getMessage();
        }
    }

    /**
     * Adiciona o nome de uma nova comunidade à lista de comunidades do usuário.
     *
     * @param newCommunity o nome da nova comunidade a ser adicionada
     */
    public void addCommunity(String newCommunity) {
        this.communitys.add(newCommunity);
    }

    /**
     * Remove o nome de uma comunidade da lista de comunidades do usuário.
     *
     * @param communiyToRemove o nome da comunidade a ser removida
     */
    public void removeComunity(String communiyToRemove) {
        this.communitys.remove(communiyToRemove);
    }

    /**
     * Remove todas as mensagens (privadas e de comunidades) enviadas por um determinado usuário.
     *
     * @param user o usuário cujas mensagens serão removidas
     */
    public void removeAllMessagesFromUser(User user) {
        this.privateMessages.removeIf(message -> message.getSender() == user);
        this.communityMessages.removeIf(message -> message.getSender() == user);
    }
}
