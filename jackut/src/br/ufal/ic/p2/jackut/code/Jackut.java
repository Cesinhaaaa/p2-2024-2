package br.ufal.ic.p2.jackut.code;

import br.ufal.ic.p2.jackut.exceptions.*;

/**
 * A classe Jackut fornece a funcionalidade principal para um sistema de mídia social,
 * incluindo gerenciamento de usuários, gerenciamento de sessões e recursos para interagir
 * com outros usuários.
 */
public class Jackut {
    private final UserManager users;
    private final SessionManager sessions;

    /**
     * Constrói uma nova instância da classe Jackut.
     *
     * Esse construtor inicializa os gerenciadores de usuário e de sessão que lidam com os dados do usuário
     * e o gerenciamento de sessões, respectivamente. O método também aciona a leitura
     * dos dados armazenados para configurar o estado do sistema.
     */
    public Jackut() {
        this.users = new UserManager("data/users.ser");
        this.sessions = new SessionManager();

        this.readData();
    }

    /**
     * Faz chamadas para os respectivos métodos de Limpeza de todos os dados na instância do sistema.
     */
    private void clearMemory() {
        this.users.clearUsers();
        this.sessions.clearSessions();
    }

    /**
     * Serializa todos os dados necessários para a persistência da aplicação.
     */
    private void saveData() {
        this.users.serialize();
    }

    /**
     * Desserializa todos os dados que foram previamente serializados para a persistência da aplicação.
     */
    private void readData() {
        this.users.deserialize();
    }

    /**
     * Apaga todos os dados serializados, perdendo a persistência da aplicação.
     */
    private void clearData() {
        this.users.clearSerialized();
    }

    /**
     * Apaga todos os dados da memória do sistema e dos arquivos de persistência.
     */
    public void clearSystem() {
        this.clearData();
        this.clearMemory();
    }

    /**
     * Encessa o sistema, salvando os dados de persistência e limpando a memória.
     */
    public void closeSystem() {
        this.saveData();
        this.clearMemory();
    }

    /**
     * Cria um novo usuário no sistema com o login, a senha e o nome de usuário especificados.
     *
     * @param login O identificador de login para o novo usuário. Deve ser exclusivo e não nulo ou vazio.
     * @param password A senha do novo usuário. Não pode ser nula ou vazia.
     * @param userName O nome do usuário a ser associado ao seu perfil.
     *
     * @throws LoginAlredyUsedException Se um usuário com o login fornecido já existir no sistema.
     * @throws InvalidLoginException Se o login fornecido for inválido (por exemplo, nulo ou vazio).
     * @throws InvalidPasswordException Se a senha fornecida for inválida (por exemplo, nula ou vazia).
     */
    public void createUser(String login, String password, String userName) throws LoginAlredyUsedException, InvalidLoginException, InvalidPasswordException {
        this.users.createUser(login, password, userName);
    }

    /**
     * Abre uma sessão para um usuário com o login e a senha fornecidos.
     *
     * Esse método valida o login e a senha do usuário, verifica se o usuário está registrado,
     * e cria uma nova sessão se as credenciais estiverem corretas.
     *
     * @param login o login do usuário que está tentando abrir uma sessão
     * @param password a senha associada ao login fornecido
     * @return um ID de sessão exclusivo para o usuário autenticado
     * @throws UserNotRegisteredException se o login fornecido não corresponder a um usuário registrado
     * @throws InvalidLoginOrPasswordException se o login ou a senha fornecidos forem inválidos
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
     * Recupera um atributo específico de um usuário com base em seu identificador de login.
     *
     * Esse método permite consultar o perfil de um usuário para obter um valor de atributo específico
     * fornecendo o login do usuário e o nome do atributo desejado. Se o usuário
     * não existir ou o atributo não estiver preenchido, será lançada uma exceção.
     *
     * @param login O login do usuário cujo atributo está sendo recuperado. Deve ser um login válido e registrado.
     * @param atribute O nome do atributo a ser recuperado. Deve ser uma chave de atributo válida e não nula no perfil do usuário.
     * @return O valor do atributo especificado para o usuário.
     * @throws UserNotRegisteredException Se nenhum usuário for encontrado com o login fornecido.
     * @throws AttributeNotFilledException Se o usuário existir, mas o atributo especificado não estiver preenchido.
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
     * Atualiza um atributo específico do perfil de um usuário identificado pelo ID de sessão fornecido.
     *
     * @param sessionId o identificador exclusivo da sessão associada ao usuário
     * @param atribute o nome do atributo a ser atualizado
     * @param value o novo valor a ser definido para o atributo especificado
     * @throws UserNotRegisteredException se o ID da sessão não corresponder a um usuário registrado
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
     * Verifica se o usuário especificado é amigo de outro usuário.
     *
     * Esse método recupera o usuário associado ao login fornecido e determina
     * se o usuário tem o amigo especificado em sua lista de amigos.
     *
     * @param login O login do usuário a ser verificado.
     * @param friendLogin O login do possível amigo a ser verificado.
     * @return true se o usuário especificado for amigo do friendLogin fornecido, false caso contrário.
     * @throws UserNotRegisteredException se o usuário associado ao login não estiver registrado no sistema.
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
     * Envia uma solicitação de amizade do usuário associado ao ID de sessão fornecido para outro usuário.
     *
     * Esse método recupera o remetente usando o `sessionId` e o destinatário usando o `receiverLogin`.
     * Em seguida, ele inicia uma solicitação de amizade para o receptor. O tratamento de erros é realizado para gerenciar
     * várias condições, como usuários não registrados, status de já amigos, solicitações de amizade duplicadas,
     * ou tentativas de adicionar a si mesmo como amigo.
     *
     * @param sessionId O ID da sessão do usuário que envia a solicitação de amizade. Deve ser válido e corresponder a uma sessão ativa.
     * @param receiverLogin O login do usuário para o qual a solicitação de amizade está sendo enviada. Deve ser um login registrado válido.
     *
     * @throws UserNotRegisteredException Se o remetente ou o destinatário não estiver registrado no sistema.
     * @throws UserAlredyAddedException Se o remetente e o destinatário já forem amigos.
     * @throws FriendRequestAlredySentException Se uma solicitação de amizade já tiver sido enviada ao destinatário.
     * @throws YourselfFriendRequestException Se o remetente tentar enviar uma solicitação de amizade para si mesmo.
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
     * Recupera a lista de amigos associados a um usuário identificado por seu login.
     *
     * Esse método procura o usuário pelo seu login e retorna os amigos associados
     * associados ao seu perfil como uma string formatada. Se o usuário não estiver registrado no sistema,
     * uma exceção será lançada.
     *
     * @param login O identificador de login do usuário cujos amigos devem ser recuperados.
     * @return Uma string que representa a lista de amigos de forma formatada.
     * @throws UserNotRegisteredException Se o usuário especificado não estiver registrado no sistema.
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
     * Envia uma mensagem do usuário identificado pelo ID de sessão fornecido para outro usuário
     * identificado por seu login. Esse método garante que o remetente seja um usuário ativo e válido
     * sessão de usuário ativa e válida e que o receptor existe no sistema, evitando o envio de
     * mensagens para si mesmo.
     *
     * @param sessionId o identificador exclusivo da sessão do remetente. Usado para recuperar
     * Usado para recuperar as informações do usuário do remetente.
     * @param receiverLogin o identificador de login do receptor. Usado para localizar o
     * Usado para localizar o receptor no sistema.
     * @param message o conteúdo da mensagem a ser enviada do remetente para o receptor.
     * @throws UserNotRegisteredException se o ID da sessão do remetente ou o login do receptor
     * não corresponder a nenhum usuário registrado no sistema.
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
     * Lê e recupera a próxima mensagem disponível para o usuário associado ao ID de sessão fornecido.
     *
     * @param sessionId o ID da sessão associado ao usuário cuja mensagem deve ser lida
     * @return a próxima mensagem disponível para o usuário
     * @throws NoMessageException se não houver mensagens disponíveis para o usuário
     * @throws UserNotRegisteredException se o usuário associado ao ID da sessão não estiver registrado
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
