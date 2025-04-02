package br.ufal.ic.p2.jackut.code;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A classe User representa um usuário com login, senha, atributos de perfil,
 * amigos, solicitações de amizade e um sistema de mensagens. Ela implementa a interface
 * Serializable, permitindo que o objeto User seja serializado.
 */
public class User implements Serializable {
    private String login, password;
    private Map<String, String> profileAttributes;
    private List<String> friends;
    private List<User> requests;
    private MessageManager messageManager;

    /**
     * Constrói um novo objeto User com o login, senha e nome fornecidos.
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
        this.friends = new ArrayList<String>();
        this.requests = new ArrayList<User>();
        this.messageManager = new MessageManager();

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
     * Retorna uma representação formatada da lista de amigos do usuário.
     * O formato da string retornada é delimitado por chaves, com os nomes
     * dos amigos separados por vírgulas.
     *
     * @return uma string contendo a lista de amigos do usuário no formato "{amigo1,amigo2,...}".
     */
    public String getFriends() {
        return "{" + String.join(",", friends) + "}";
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
     * @throws AttributeNotFilledException se o atributo não estiver presente ou não tiver sido preenchido.
     */
    public String getAttribute(String atribute) throws AttributeNotFilledException {
        String atributeGetted = this.profileAttributes.get(atribute);
        if (atributeGetted == null) {
            throw new AttributeNotFilledException();
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
     * Determina se o usuário especificado é amigo do usuário atual.
     *
     * @param receiver o login ou identificador do usuário a ser verificado.
     * @return true se o usuário especificado for amigo, false caso contrário.
     */
    public boolean isFriend(String receiver) {
        return this.friends.contains(receiver);
    }

    /**
     * Verifica se o usuário especificado já enviou uma solicitação de amizade ao usuário atual.
     *
     * @param user o usuário cuja solicitação será verificada.
     * @return true se o usuário já enviou uma solicitação, false caso contrário.
     */
    public boolean alredyReceiveRequest(User user) {
        return this.requests.contains(user);
    }

    /**
     * Adiciona um amigo à lista de amigos do usuário. Esse método lida com a lógica de adicionar um amigo,
     * verificando se o usuário já é amigo, se uma solicitação de amizade já foi enviada ou recebida,
     * e se o usuário está tentando adicionar a si mesmo como amigo.
     *
     * @param user O objeto do usuário a ser adicionado como amigo.
     * @throws UserAlredyAddedException Se o usuário especificado já estiver na lista de amigos.
     * @throws FriendRequestAlredySentException Se uma solicitação de amizade já foi enviada.
     * @throws YourselfFriendRequestException Se o usuário tentar adicionar a si mesmo como amigo.
     */
    public void addFriend(User user) throws UserAlredyAddedException, FriendRequestAlredySentException, YourselfFriendRequestException {
        if (this.isFriend(user.getLogin())) {
            throw new UserAlredyAddedException();
        } else if (this.alredyReceiveRequest(user)) {
            this.friends.add(user.getLogin());
            user.friends.add(this.getLogin());

            this.requests.remove(user);
            user.requests.remove(this);
        } else if (user.alredyReceiveRequest(this)) {
            throw new FriendRequestAlredySentException();
        } else if (this.getLogin().equals(user.getLogin())) {
            throw new YourselfFriendRequestException();
        } else {
            user.requests.add(this);
        }
    }

    /**
     * Envia uma mensagem para um destinatário especificado.
     *
     * @param receiver o destinatário da mensagem.
     * @param message o conteúdo da mensagem a ser enviada.
     * @throws YourselfMessageException se o usuário tentar enviar uma mensagem para si mesmo.
     */
    public void sentMessage(User receiver, String message) throws YourselfMessageException {
        if (receiver.getLogin().equals(this.getLogin())) {
            throw new YourselfMessageException();
        } else {
            receiver.messageManager.receive(message);
        }
    }

    /**
     * Lê a primeira mensagem disponível para o usuário.
     * A mensagem recuperada é removida da lista interna de mensagens.
     *
     * @return a primeira mensagem disponível para o usuário.
     * @throws NoMessageException se não houver mensagens disponíveis para leitura.
     */
    public String readMessage() throws NoMessageException {
        try {
            return this.messageManager.read();
        } catch (NoMessageException e) {
            throw e;
        }
    }
}
