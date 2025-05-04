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
 * A classe User representa um usu�rio com login, senha, atributos de perfil,
 * rela��es (amizade, fa, paquera, inimigo) representado pelo gerenciador de rela��es
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
     * Inicializa os atributos de perfil, lista de amigos, solicita��es de amizade
     * e gerenciador de mensagens. Tamb�m define o atributo de perfil "nome" com o nome fornecido.
     *
     * @param login o nome de login do usu�rio. N�o pode ser nulo ou vazio.
     * @param password a senha do usu�rio. N�o pode ser nula ou vazia.
     * @param userName o nome do usu�rio, que ser� armazenado no atributo de perfil "nome".
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
     * Retorna o login do usu�rio.
     *
     * @return o login do usu�rio como uma string.
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
     * Retorna a lista de comunidades do usu�rio formatada como String.
     *
     * @return a lista de comunidades do usu�rio formatada como String.
     */
    public String getCommunitysAsString() {
        return "{" + String.join(",", this.communitys) + "}";
    }

    /**
     * Retorna o gerenciador de rela��es do usu�rio.
     *
     * @return o gerenciador de rela��es do usu�rio.
     */
    public RelationsManager getRelationManager() {
        return relations;
    }

    /**
     * Verifica se a senha fornecida corresponde � senha do usu�rio.
     *
     * @param password A senha a ser validada em rela��o � senha armazenada do usu�rio.
     * @return true se a senha fornecida for correta.
     * @throws InvalidLoginOrPasswordException Se a senha fornecida n�o corresponder � senha do usu�rio.
     */
    public boolean isCorrectPassword(String password) throws InvalidLoginOrPasswordException {
        if (password.equals(this.password)) {
            return true;
        } else {
            throw new InvalidLoginOrPasswordException();
        }
    }

    /**
     * Retorna o valor de um atributo espec�fico do perfil do usu�rio.
     *
     * @param atribute o nome do atributo a ser recuperado.
     * @return o valor do atributo especificado.
     * @throws UserAttributeNotFilledException se o atributo n�o estiver presente ou n�o tiver sido preenchido.
     */
    public String getAttribute(String atribute) throws UserAttributeNotFilledException {
        String atributeGetted = this.profileAttributes.get(atribute);
        if (atributeGetted == null) {
            throw new UserAttributeNotFilledException();
        }
        return atributeGetted;
    }

    /**
     * Atualiza o valor de um atributo espec�fico do perfil do usu�rio.
     *
     * @param atribute o nome do atributo de perfil a ser atualizado.
     * @param value o novo valor a ser definido para o atributo especificado.
     */
    public void updateProfileAttribute(String atribute, String value) {
        this.profileAttributes.put(atribute, value);
    }

    /**
     * Recebe uma mensagem de um usu�rio para outro.
     *
     * @param message o conte�do da mensagem recebida.
     */
    public void receivePrivateMessage(Message message) {
        this.privateMessages.add(message);
    }

    /**
     * Recebe uma mensagem de um usu�rio para a comunidade.
     *
     * @param message o conte�do da mensagem recebida.
     */
    public void receiveCommunityMessage(Message message) {
        this.communityMessages.add(message);
    }

    /**
     * L� a primeira mensagem privada dispon�vel para o usu�rio.
     * A mensagem recuperada � removida da lista de mensagens privadas.
     *
     * @return a primeira mensagem privada dispon�vel para o usu�rio.
     * @throws NoPrivateMessageException se n�o houver mensagens dispon�veis para leitura.
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
     * L� a primeira mensagem de uma comunidade dispon�vel para o usu�rio.
     * A mensagem recuperada � removida da lista de mensagens de comunidades.
     *
     * @return a primeira mensagem de uma comunidade dispon�vel para o usu�rio.
     * @throws NoCommunityMessageException se n�o houver mensagens dispon�veis para leitura.
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
     * Adiciona o nome de uma nova comunidade � lista de comunidades do usu�rio.
     *
     * @param newCommunity o nome da nova comunidade a ser adicionada
     */
    public void addCommunity(String newCommunity) {
        this.communitys.add(newCommunity);
    }

    /**
     * Remove o nome de uma comunidade da lista de comunidades do usu�rio.
     *
     * @param communiyToRemove o nome da comunidade a ser removida
     */
    public void removeComunity(String communiyToRemove) {
        this.communitys.remove(communiyToRemove);
    }

    /**
     * Remove todas as mensagens (privadas e de comunidades) enviadas por um determinado usu�rio.
     *
     * @param user o usu�rio cujas mensagens ser�o removidas
     */
    public void removeAllMessagesFromUser(User user) {
        this.privateMessages.removeIf(message -> message.getSender() == user);
        this.communityMessages.removeIf(message -> message.getSender() == user);
    }
}
