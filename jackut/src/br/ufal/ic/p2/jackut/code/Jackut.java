package br.ufal.ic.p2.jackut.code;

import br.ufal.ic.p2.jackut.exceptions.*;

/**
 * A classe Jackut fornece a funcionalidade principal para um sistema de m�dia social,
 * incluindo gerenciamento de usu�rios, gerenciamento de sess�es e recursos para interagir
 * com outros usu�rios.
 */
public class Jackut {
    private final UserManager users;
    private final SessionManager sessions;

    /**
     * Constr�i uma nova inst�ncia da classe Jackut.
     *
     * Esse construtor inicializa os gerenciadores de usu�rio e de sess�o que lidam com os dados do usu�rio
     * e o gerenciamento de sess�es, respectivamente. O m�todo tamb�m aciona a leitura
     * dos dados armazenados para configurar o estado do sistema.
     */
    public Jackut() {
        this.users = new UserManager("data/users.ser");
        this.sessions = new SessionManager();

        this.readData();
    }

    /**
     * Faz chamadas para os respectivos m�todos de Limpeza de todos os dados na inst�ncia do sistema.
     */
    private void clearMemory() {
        this.users.clearUsers();
        this.sessions.clearSessions();
    }

    /**
     * Serializa todos os dados necess�rios para a persist�ncia da aplica��o.
     */
    private void saveData() {
        this.users.serialize();
    }

    /**
     * Desserializa todos os dados que foram previamente serializados para a persist�ncia da aplica��o.
     */
    private void readData() {
        this.users.deserialize();
    }

    /**
     * Apaga todos os dados serializados, perdendo a persist�ncia da aplica��o.
     */
    private void clearData() {
        this.users.clearSerialized();
    }

    /**
     * Apaga todos os dados da mem�ria do sistema e dos arquivos de persist�ncia.
     */
    public void clearSystem() {
        this.clearData();
        this.clearMemory();
    }

    /**
     * Encessa o sistema, salvando os dados de persist�ncia e limpando a mem�ria.
     */
    public void closeSystem() {
        this.saveData();
        this.clearMemory();
    }

    /**
     * Cria um novo usu�rio no sistema com o login, a senha e o nome de usu�rio especificados.
     *
     * @param login O identificador de login para o novo usu�rio. Deve ser exclusivo e n�o nulo ou vazio.
     * @param password A senha do novo usu�rio. N�o pode ser nula ou vazia.
     * @param userName O nome do usu�rio a ser associado ao seu perfil.
     *
     * @throws LoginAlredyUsedException Se um usu�rio com o login fornecido j� existir no sistema.
     * @throws InvalidLoginException Se o login fornecido for inv�lido (por exemplo, nulo ou vazio).
     * @throws InvalidPasswordException Se a senha fornecida for inv�lida (por exemplo, nula ou vazia).
     */
    public void createUser(String login, String password, String userName) throws LoginAlredyUsedException, InvalidLoginException, InvalidPasswordException {
        this.users.createUser(login, password, userName);
    }

    /**
     * Abre uma sess�o para um usu�rio com o login e a senha fornecidos.
     *
     * Esse m�todo valida o login e a senha do usu�rio, verifica se o usu�rio est� registrado,
     * e cria uma nova sess�o se as credenciais estiverem corretas.
     *
     * @param login o login do usu�rio que est� tentando abrir uma sess�o
     * @param password a senha associada ao login fornecido
     * @return um ID de sess�o exclusivo para o usu�rio autenticado
     * @throws UserNotRegisteredException se o login fornecido n�o corresponder a um usu�rio registrado
     * @throws InvalidLoginOrPasswordException se o login ou a senha fornecidos forem inv�lidos
     */
    public String openSession(String login, String password) throws UserNotRegisteredException, InvalidLoginOrPasswordException {
        try {
            this.users.loginCheck(login, password);
            User user = this.users.getUserByLogin(login);
            if (user.isCorrectPassword(password)) {
                return this.sessions.newSession(user);
            }
            throw new InvalidLoginOrPasswordException();
        } catch (UserNotRegisteredException e) {
            throw e;
        } catch (InvalidLoginOrPasswordException e) {
            throw e;
        }
    }

    /**
     * Recupera um atributo espec�fico de um usu�rio com base em seu identificador de login.
     *
     * Esse m�todo permite consultar o perfil de um usu�rio para obter um valor de atributo espec�fico
     * fornecendo o login do usu�rio e o nome do atributo desejado. Se o usu�rio
     * n�o existir ou o atributo n�o estiver preenchido, ser� lan�ada uma exce��o.
     *
     * @param login O login do usu�rio cujo atributo est� sendo recuperado. Deve ser um login v�lido e registrado.
     * @param atribute O nome do atributo a ser recuperado. Deve ser uma chave de atributo v�lida e n�o nula no perfil do usu�rio.
     * @return O valor do atributo especificado para o usu�rio.
     * @throws UserNotRegisteredException Se nenhum usu�rio for encontrado com o login fornecido.
     * @throws AttributeNotFilledException Se o usu�rio existir, mas o atributo especificado n�o estiver preenchido.
     */
    public String getUserAttribute(String login, String atribute) throws UserNotRegisteredException, AttributeNotFilledException {
        try {
            User user = this.users.getUserByLogin(login);
            return user.getAttribute(atribute);
        } catch (UserNotRegisteredException e) {
            throw e;
        } catch (AttributeNotFilledException e) {
            throw e;
        }
    }

    /**
     * Atualiza um atributo espec�fico do perfil de um usu�rio identificado pelo ID de sess�o fornecido.
     *
     * @param sessionId o identificador exclusivo da sess�o associada ao usu�rio
     * @param atribute o nome do atributo a ser atualizado
     * @param value o novo valor a ser definido para o atributo especificado
     * @throws UserNotRegisteredException se o ID da sess�o n�o corresponder a um usu�rio registrado
     */
    public void updateProfile(String sessionId, String atribute, String value) throws UserNotRegisteredException {
        try {
            User user = this.sessions.getUserBySessionId(sessionId);
            user.updateProfileAttribute(atribute, value);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Verifica se o usu�rio especificado � amigo de outro usu�rio.
     *
     * Esse m�todo recupera o usu�rio associado ao login fornecido e determina
     * se o usu�rio tem o amigo especificado em sua lista de amigos.
     *
     * @param login O login do usu�rio a ser verificado.
     * @param friendLogin O login do poss�vel amigo a ser verificado.
     * @return true se o usu�rio especificado for amigo do friendLogin fornecido, false caso contr�rio.
     * @throws UserNotRegisteredException se o usu�rio associado ao login n�o estiver registrado no sistema.
     */
    public boolean isFriend(String login, String friendLogin) throws UserNotRegisteredException {
        try {
            User user = this.users.getUserByLogin(login);
            return user.isFriend(friendLogin);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Envia uma solicita��o de amizade do usu�rio associado ao ID de sess�o fornecido para outro usu�rio.
     *
     * Esse m�todo recupera o remetente usando o `sessionId` e o destinat�rio usando o `receiverLogin`.
     * Em seguida, ele inicia uma solicita��o de amizade para o receptor. O tratamento de erros � realizado para gerenciar
     * v�rias condi��es, como usu�rios n�o registrados, status de j� amigos, solicita��es de amizade duplicadas,
     * ou tentativas de adicionar a si mesmo como amigo.
     *
     * @param sessionId O ID da sess�o do usu�rio que envia a solicita��o de amizade. Deve ser v�lido e corresponder a uma sess�o ativa.
     * @param receiverLogin O login do usu�rio para o qual a solicita��o de amizade est� sendo enviada. Deve ser um login registrado v�lido.
     *
     * @throws UserNotRegisteredException Se o remetente ou o destinat�rio n�o estiver registrado no sistema.
     * @throws UserAlredyAddedException Se o remetente e o destinat�rio j� forem amigos.
     * @throws FriendRequestAlredySentException Se uma solicita��o de amizade j� tiver sido enviada ao destinat�rio.
     * @throws YourselfFriendRequestException Se o remetente tentar enviar uma solicita��o de amizade para si mesmo.
     */
    public void addFriend(String sessionId, String receiverLogin) throws UserNotRegisteredException, UserAlredyAddedException, FriendRequestAlredySentException, YourselfFriendRequestException {
        try {
            User sender = this.sessions.getUserBySessionId(sessionId);
            User receiver = this.users.getUserByLogin(receiverLogin);

            sender.addFriend(receiver);
        } catch(UserNotRegisteredException e) {
            throw e;
        } catch (UserAlredyAddedException e) {
            throw e;
        } catch (FriendRequestAlredySentException e) {
            throw e;
        } catch (YourselfFriendRequestException e) {
            throw e;
        }
    }

    /**
     * Recupera a lista de amigos associados a um usu�rio identificado por seu login.
     *
     * Esse m�todo procura o usu�rio pelo seu login e retorna os amigos associados
     * associados ao seu perfil como uma string formatada. Se o usu�rio n�o estiver registrado no sistema,
     * uma exce��o ser� lan�ada.
     *
     * @param login O identificador de login do usu�rio cujos amigos devem ser recuperados.
     * @return Uma string que representa a lista de amigos de forma formatada.
     * @throws UserNotRegisteredException Se o usu�rio especificado n�o estiver registrado no sistema.
     */
    public String getUserFriends(String login) throws UserNotRegisteredException {
        try {
            User user = this.users.getUserByLogin(login);
            return user.getFriends();
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Envia uma mensagem do usu�rio identificado pelo ID de sess�o fornecido para outro usu�rio
     * identificado por seu login. Esse m�todo garante que o remetente seja um usu�rio ativo e v�lido
     * sess�o de usu�rio ativa e v�lida e que o receptor existe no sistema, evitando o envio de
     * mensagens para si mesmo.
     *
     * @param sessionId o identificador exclusivo da sess�o do remetente. Usado para recuperar
     * Usado para recuperar as informa��es do usu�rio do remetente.
     * @param receiverLogin o identificador de login do receptor. Usado para localizar o
     * Usado para localizar o receptor no sistema.
     * @param message o conte�do da mensagem a ser enviada do remetente para o receptor.
     * @throws UserNotRegisteredException se o ID da sess�o do remetente ou o login do receptor
     * n�o corresponder a nenhum usu�rio registrado no sistema.
     * @throws YourselfMessageException se o remetente tentar enviar uma mensagem para si mesmo.
     */
    public void sendMessage(String sessionId, String receiverLogin, String message) throws UserNotRegisteredException, YourselfMessageException {
        try {
            User sender = this.sessions.getUserBySessionId(sessionId);
            User receiver = this.users.getUserByLogin(receiverLogin);

            sender.sentMessage(receiver, message);
        } catch (UserNotRegisteredException e) {
            throw e;
        } catch (YourselfMessageException e) {
            throw e;
        }
    }

    /**
     * L� e recupera a pr�xima mensagem dispon�vel para o usu�rio associado ao ID de sess�o fornecido.
     *
     * @param sessionId o ID da sess�o associado ao usu�rio cuja mensagem deve ser lida
     * @return a pr�xima mensagem dispon�vel para o usu�rio
     * @throws NoMessageException se n�o houver mensagens dispon�veis para o usu�rio
     * @throws UserNotRegisteredException se o usu�rio associado ao ID da sess�o n�o estiver registrado
     */
    public String readMessage(String sessionId) throws NoMessageException, UserNotRegisteredException {
        try {
            User user = this.sessions.getUserBySessionId(sessionId);
            return user.readMessage();
        } catch (NoMessageException e) {
            throw e;
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }
}
