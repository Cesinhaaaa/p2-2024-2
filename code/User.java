package br.ufal.ic.p2.jackut.code;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A classe User representa um usu�rio com login, senha, atributos de perfil,
 * amigos, solicita��es de amizade e um sistema de mensagens. Ela implementa a interface
 * Serializable, permitindo que o objeto User seja serializado.
 */
public class User implements Serializable {
    private String login, password;
    private Map<String, String> profileAttributes;
    private List<String> friends;
    private List<User> requests;
    private MessageManager messageManager;

    /**
     * Constr�i um novo objeto User com o login, senha e nome fornecidos.
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
        this.friends = new ArrayList<String>();
        this.requests = new ArrayList<User>();
        this.messageManager = new MessageManager();

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
     * Retorna uma representa��o formatada da lista de amigos do usu�rio.
     * O formato da string retornada � delimitado por chaves, com os nomes
     * dos amigos separados por v�rgulas.
     *
     * @return uma string contendo a lista de amigos do usu�rio no formato "{amigo1,amigo2,...}".
     */
    public String getFriends() {
        return "{" + String.join(",", friends) + "}";
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
     * @throws AttributeNotFilledException se o atributo n�o estiver presente ou n�o tiver sido preenchido.
     */
    public String getAttribute(String atribute) throws AttributeNotFilledException {
        String atributeGetted = this.profileAttributes.get(atribute);
        if (atributeGetted == null) {
            throw new AttributeNotFilledException();
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
     * Determina se o usu�rio especificado � amigo do usu�rio atual.
     *
     * @param receiver o login ou identificador do usu�rio a ser verificado.
     * @return true se o usu�rio especificado for amigo, false caso contr�rio.
     */
    public boolean isFriend(String receiver) {
        return this.friends.contains(receiver);
    }

    /**
     * Verifica se o usu�rio especificado j� enviou uma solicita��o de amizade ao usu�rio atual.
     *
     * @param user o usu�rio cuja solicita��o ser� verificada.
     * @return true se o usu�rio j� enviou uma solicita��o, false caso contr�rio.
     */
    public boolean alredyReceiveRequest(User user) {
        return this.requests.contains(user);
    }

    /**
     * Adiciona um amigo � lista de amigos do usu�rio. Esse m�todo lida com a l�gica de adicionar um amigo,
     * verificando se o usu�rio j� � amigo, se uma solicita��o de amizade j� foi enviada ou recebida,
     * e se o usu�rio est� tentando adicionar a si mesmo como amigo.
     *
     * @param user O objeto do usu�rio a ser adicionado como amigo.
     * @throws UserAlredyAddedException Se o usu�rio especificado j� estiver na lista de amigos.
     * @throws FriendRequestAlredySentException Se uma solicita��o de amizade j� foi enviada.
     * @throws YourselfFriendRequestException Se o usu�rio tentar adicionar a si mesmo como amigo.
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
     * Envia uma mensagem para um destinat�rio especificado.
     *
     * @param receiver o destinat�rio da mensagem.
     * @param message o conte�do da mensagem a ser enviada.
     * @throws YourselfMessageException se o usu�rio tentar enviar uma mensagem para si mesmo.
     */
    public void sentMessage(User receiver, String message) throws YourselfMessageException {
        if (receiver.getLogin().equals(this.getLogin())) {
            throw new YourselfMessageException();
        } else {
            receiver.messageManager.receive(message);
        }
    }

    /**
     * L� a primeira mensagem dispon�vel para o usu�rio.
     * A mensagem recuperada � removida da lista interna de mensagens.
     *
     * @return a primeira mensagem dispon�vel para o usu�rio.
     * @throws NoMessageException se n�o houver mensagens dispon�veis para leitura.
     */
    public String readMessage() throws NoMessageException {
        try {
            return this.messageManager.read();
        } catch (NoMessageException e) {
            throw e;
        }
    }
}
