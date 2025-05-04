package br.ufal.ic.p2.jackut.code;

import br.ufal.ic.p2.jackut.code.community.Community;
import br.ufal.ic.p2.jackut.code.community.CommunityManager;
import br.ufal.ic.p2.jackut.code.message.CommunityMessage;
import br.ufal.ic.p2.jackut.code.message.Message;
import br.ufal.ic.p2.jackut.code.message.PrivateMessage;
import br.ufal.ic.p2.jackut.code.session.SessionManager;
import br.ufal.ic.p2.jackut.code.user.User;
import br.ufal.ic.p2.jackut.code.user.UserManager;
import br.ufal.ic.p2.jackut.exceptions.community.CommunityAlredyExistException;
import br.ufal.ic.p2.jackut.exceptions.community.CommunityNotExistException;
import br.ufal.ic.p2.jackut.exceptions.community.UserAlredyJoinedCommunityException;
import br.ufal.ic.p2.jackut.exceptions.login.InvalidLoginException;
import br.ufal.ic.p2.jackut.exceptions.login.InvalidLoginOrPasswordException;
import br.ufal.ic.p2.jackut.exceptions.login.InvalidPasswordException;
import br.ufal.ic.p2.jackut.exceptions.login.LoginAlredyUsedException;
import br.ufal.ic.p2.jackut.exceptions.message.NoCommunityMessageException;
import br.ufal.ic.p2.jackut.exceptions.message.NoPrivateMessageException;
import br.ufal.ic.p2.jackut.exceptions.relations.*;
import br.ufal.ic.p2.jackut.exceptions.user.UserAttributeNotFilledException;
import br.ufal.ic.p2.jackut.exceptions.login.UserNotRegisteredException;

/**
 * A classe Jackut fornece a funcionalidade principal para um sistema de mídia social,
 * incluindo gerenciamento de usuários, gerenciamento de sessões e recursos para interagir
 * com outros usuários.
 */
public class Jackut {
    private final UserManager users;
    private final SessionManager sessions;
    private final CommunityManager communitys;

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
        this.communitys = new CommunityManager("data/communitys.ser");

        this.readData();
    }

    /**
     * Faz chamadas para os respectivos métodos de Limpeza de todos os dados na instância do sistema.
     */
    private void clearMemory() {
        this.users.clearUsers();
        this.sessions.clearSessions();
        this.communitys.clearCommunitys();
    }

    /**
     * Serializa todos os dados necessários para a persistência da aplicação.
     */
    private void saveData() {
        this.users.serialize();
        this.communitys.serialize();
    }

    /**
     * Desserializa todos os dados que foram previamente serializados para a persistência da aplicação.
     */
    private void readData() {
        this.users.deserialize();
        this.communitys.deserialize();
    }

    /**
     * Apaga todos os dados serializados, perdendo a persistência da aplicação.
     */
    private void clearData() {
        this.users.clearSerialized();
        this.communitys.clearSerialized();
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
     * @throws UserAttributeNotFilledException Se o usuário existir, mas o atributo especificado não estiver preenchido.
     */
    public String getUserAttribute(String login, String atribute) throws UserNotRegisteredException, UserAttributeNotFilledException {
        try {
            User user = this.users.getUserByLogin(login);
            return user.getAttribute(atribute);
        } catch (UserNotRegisteredException e) {
            throw e;
        } catch (UserAttributeNotFilledException e) {
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
            User friend = this.users.getUserByLogin(friendLogin);
            return user.getRelationManager().isFriend(friend);
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
     * @throws UserAttributeNotFilledException Caso o atributo "nome" não esteja preenchido.
     * @throws ThisUserIsYourEnemyException Se caso o usuário tente interagir com alguem que o declarou como inimigo.
     */
    public void addFriend(String sessionId, String receiverLogin) throws UserNotRegisteredException, UserAlredyAddedException,
            FriendRequestAlredySentException, YourselfFriendRequestException, UserAttributeNotFilledException, ThisUserIsYourEnemyException {
        User sender = this.sessions.getUserBySessionId(sessionId);
        User receiver = this.users.getUserByLogin(receiverLogin);

        if (sender.getRelationManager().isFriend(receiver)) {
            throw new UserAlredyAddedException();
        }

        if (receiver.getRelationManager().alredyReceivedRequest(sender)) {
            throw new FriendRequestAlredySentException();
        }

        if (sender == receiver) {
            throw new YourselfFriendRequestException();
        }

        if (receiver.getRelationManager().isEnemy(sender)) {
            throw new ThisUserIsYourEnemyException(receiver.getAttribute("nome"));
        }

        if (sender.getRelationManager().alredyReceivedRequest(receiver)) {
            sender.getRelationManager().addFriend(receiver);
            receiver.getRelationManager().addFriend(sender);
        } else {
            receiver.getRelationManager().receiveRequest(sender);
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
            return user.getRelationManager().getFriendsAsString();
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
     * @param receiverLogin o identificador de login do receptor. Usado para localizar o receptor no sistema.
     * @param messageContent o conteúdo da mensagem a ser enviada do remetente para o receptor.
     * @throws UserNotRegisteredException se o ID da sessão do remetente ou o login do receptor não corresponder a nenhum usuário registrado no sistema.
     * @throws YourselfMessageException se o remetente tentar enviar uma mensagem para si mesmo.
     */
    public void sendPrivateMessage(String sessionId, String receiverLogin, String messageContent) throws UserNotRegisteredException, YourselfMessageException, UserAttributeNotFilledException, ThisUserIsYourEnemyException {
        User sender = this.sessions.getUserBySessionId(sessionId);
        User receiver = this.users.getUserByLogin(receiverLogin);

        if (receiverLogin.equals(sender.getLogin())) {
            throw new YourselfMessageException();
        }

        if (receiver.getRelationManager().isEnemy(sender)) {
            throw new ThisUserIsYourEnemyException(receiver.getAttribute("nome"));
        }

        Message message = new PrivateMessage(sender, receiver, messageContent);
        message.send();
    }

    /**
     * Lê e recupera a próxima mensagem disponível para o usuário associado ao ID de sessão fornecido.
     *
     * @param sessionId o ID da sessão associado ao usuário cuja mensagem deve ser lida
     * @return a próxima mensagem disponível para o usuário
     * @throws NoPrivateMessageException se não houver mensagens disponíveis para o usuário
     * @throws UserNotRegisteredException se o usuário associado ao ID da sessão não estiver registrado
     */
    public String readPrivateMessage(String sessionId) throws NoPrivateMessageException, UserNotRegisteredException {
        User user = this.sessions.getUserBySessionId(sessionId);
        return user.readPrivateMessage();
    }

    /**
     * Cria e adiciona uma comunidade ao gerenciador de comunidades do jackut.
     *
     * @param sessionId o Id da sessão associado ao usuário que está criando a comunidade.
     * @param communityName o nome da comunidade
     * @param communityDescription a descrição da comunidade
     * @throws CommunityAlredyExistException caso já exista uma comunidade com o mesmo nome.
     * @throws UserNotRegisteredException caso o ID não esteja associado a nenhum usuário.
     */
    public void createCommunity(String sessionId, String communityName, String communityDescription) throws CommunityAlredyExistException, UserNotRegisteredException {
        try {
            User user = this.sessions.getUserBySessionId(sessionId);
            this.communitys.createCommunity(user, communityName, communityDescription);
        } catch (CommunityAlredyExistException | UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Retorna a descrição de uma comunidade pelo nome.
     *
     * @param communityName o nome da comunidade
     * @return a descrição da comunidade
     * @throws CommunityNotExistException se a comunidade não existir
     */
    public String getCommunityDescription(String communityName) throws CommunityNotExistException {
        return this.communitys.getCommunityByName(communityName).getDescription();
    }

    /**
     * Retorna o login do dono de uma comunidade.
     *
     * @param communityName o nome da comunidade
     * @return o login do proprietário da comunidade
     * @throws CommunityNotExistException se a comunidade não existir
     */
    public String getCommunityOwner(String communityName) throws CommunityNotExistException {
        return this.communitys.getCommunityByName(communityName).getOwner().getLogin();
    }

    /**
     * Retorna a lista de membros de uma comunidade em formato de string.
     *
     * @param communityName o nome da comunidade
     * @return string com os membros da comunidade
     * @throws CommunityNotExistException se a comunidade não existir
     */
    public String getCommunityMembers(String communityName) throws CommunityNotExistException {
        return this.communitys.getCommunityByName(communityName).getMemberListAsString();
    }

    /**
     * Retorna as comunidades que um usuário participa com base no login.
     *
     * @param login o login do usuário
     * @return string com os nomes das comunidades
     * @throws UserNotRegisteredException se o usuário não estiver registrado
     */
    public String getUserCommunitysByLogin(String login) throws UserNotRegisteredException {
        return this.users.getUserByLogin(login).getCommunitysAsString();
    }

    /**
     * Adiciona um usuário logado em uma sessão a uma comunidade.
     *
     * @param sessionId o identificador da sessão do usuário
     * @param communityName o nome da comunidade
     * @throws UserNotRegisteredException se o usuário não estiver registrado
     * @throws CommunityNotExistException se a comunidade não existir
     * @throws UserAlredyJoinedCommunityException se o usuário já for membro da comunidade
     */
    public void joinCommunity(String sessionId, String communityName) throws UserNotRegisteredException, CommunityNotExistException, UserAlredyJoinedCommunityException {
        Community community = this.communitys.getCommunityByName(communityName);
        User user = this.sessions.getUserBySessionId(sessionId);

        if (community.userAlredyJoined(user)) {
            throw new UserAlredyJoinedCommunityException();
        } else {
            community.addMember(user);
            user.addCommunity(communityName);
        }
    }

    /**
     * Envia uma mensagem do usuário identificado pelo ID de sessão fornecido para outro usuário
     * identificado por seu login. Esse método garante que o remetente seja um usuário ativo e válido
     * sessão de usuário ativa e válida e que o receptor existe no sistema, evitando o envio de
     * mensagens para si mesmo.
     *
     * @param sessionId o identificador exclusivo da sessão do remetente. Usado para recuperar as informações do usuário do remetente.
     * @param communityName o identificador da comunidade. Usado para localizar para localizar a comunidade receptora da mensagem no sistema.
     * @param messageContent o conteúdo da mensagem a ser enviada do remetente para o receptor.
     * @throws UserNotRegisteredException se o ID da sessão do remetente ou o login do receptor
     * não corresponder a nenhum usuário registrado no sistema.
     * @throws CommunityNotExistException se a comunidade que se deseja enviar a mensagem não existir
     */
    public void sendCommunityMessage(String sessionId, String communityName, String messageContent) throws UserNotRegisteredException, CommunityNotExistException {
        User sender = this.sessions.getUserBySessionId(sessionId);
        Community community = this.communitys.getCommunityByName(communityName);

        Message message = new CommunityMessage(sender, community, messageContent);
        message.send();
    }

    /**
     * Lê e recupera a próxima mensagem disponível para o usuário associado ao ID de sessão fornecido.
     *
     * @param sessionId o ID da sessão associado ao usuário cuja mensagem deve ser lida
     * @return a próxima mensagem disponível para o usuário
     * @throws NoCommunityMessageException se não houver mensagens disponíveis para o usuário
     * @throws UserNotRegisteredException se o usuário associado ao ID da sessão não estiver registrado
     */
    public String readCommunityMessage(String sessionId) throws NoCommunityMessageException, UserNotRegisteredException {
        User user = this.sessions.getUserBySessionId(sessionId);
        return user.readCommunityMessage();
    }

    /**
     * Adiciona um outro usuário como ídolo após as devidas verificações.
     *
     * @param sessionId
     * @param idolLogin
     * @throws UserNotRegisteredException
     * @throws YourselfFaException
     * @throws UserAlredyIsIdolException
     * @throws ThisUserIsYourEnemyException
     * @throws UserAttributeNotFilledException
     */
    public void addIdol(String sessionId, String idolLogin) throws UserNotRegisteredException, YourselfFaException, UserAlredyIsIdolException, ThisUserIsYourEnemyException, UserAttributeNotFilledException {
        User fa = this.sessions.getUserBySessionId(sessionId);
        User idol = this.users.getUserByLogin(idolLogin);

        if (fa == idol) {
            throw new YourselfFaException();
        }

        if (idol.getRelationManager().isIdol(fa)) {
            throw new UserAlredyIsIdolException();
        }

        if (idol.getRelationManager().isEnemy(fa)) {
            throw new ThisUserIsYourEnemyException(idol.getAttribute("nome"));
        }

        idol.getRelationManager().addFa(fa);
    }

    /**
     * Verifica se um usuário é fã de outro usuário.
     *
     * @param faLogin login relacionado ao fã.
     * @param idolLogin login relacionado ao idolo.
     * @return booleano indicando se é fã ou não.
     * @throws UserNotRegisteredException caso algum dos logins não estejam registrados na aplicação.
     */
    public boolean isFa(String faLogin, String idolLogin) throws UserNotRegisteredException {
        User fa = this.users.getUserByLogin(faLogin);
        User idol = this.users.getUserByLogin(idolLogin);

        return idol.getRelationManager().isIdol(fa);
    }

    /**
     * Recupera a lista de fãs de um usuário.
     *
     * @param idolLogin login do idolo cuja lista de fãs deve ser recuperada.
     * @return uma lista na forma de string com os fãs do usuário.
     * @throws UserNotRegisteredException caso o login não esteja associado a um usuário.
     */
    public String getFas(String idolLogin) throws UserNotRegisteredException {
        User idol = this.users.getUserByLogin(idolLogin);
        return idol.getRelationManager().getFasAsString();
    }

    /**
     * Adiciona um outro usuário como paquera de forma privada após as verificações.
     * Tambem verifica se a paquera é mutua.
     *
     * @param sessionId o identificador da sessão do usuário que está adicionando a paquera
     * @param paqueraLogin o login do usuário a ser adicionado como paquera
     * @throws UserNotRegisteredException se o usuário da sessão ou o paquera não estiverem registrados
     * @throws UserAttributeNotFilledException se algum dos atributos obrigatórios do usuário estiver faltando
     * @throws YourselfPaqueraException se o usuário tentar adicionar a si mesmo como paquera
     * @throws PaqueraAlredyAddedException se o usuário já tiver adicionado essa pessoa como paquera anteriormente
     * @throws ThisUserIsYourEnemyException se o usuário alvo for inimigo do usuário da sessão
     */
    public void addPaquera(String sessionId, String paqueraLogin) throws UserNotRegisteredException,
            UserAttributeNotFilledException, YourselfPaqueraException, PaqueraAlredyAddedException, ThisUserIsYourEnemyException {
        User paquerador = this.sessions.getUserBySessionId(sessionId);
        User paquera = this.users.getUserByLogin(paqueraLogin);

        if (paquerador == paquera) {
            throw new YourselfPaqueraException();
        }

        if (paquerador.getRelationManager().paqueraTheUser(paquera)) {
            throw new PaqueraAlredyAddedException();
        }

        if (paquera.getRelationManager().isEnemy(paquerador)) {
            throw new ThisUserIsYourEnemyException(paquera.getAttribute("nome"));
        }

        paquerador.getRelationManager().addPaquera(paquera);

        if (paquera.getRelationManager().paqueraTheUser(paquerador)) {
            String contentToPaquerador = paquera.getAttribute("nome") + " é seu paquera - Recado do Jackut.";
            String contentToPaquera = paquerador.getAttribute("nome") + " é seu paquera - Recado do Jackut.";

            Message messageToPaquerador = new PrivateMessage(paquera, paquerador, contentToPaquerador);
            Message messageToPaquera = new PrivateMessage(paquerador, paquera, contentToPaquera);

            messageToPaquerador.send();
            messageToPaquera.send();
        }
    }

    /**
     * Verifica se um usuário é paquera de outro.
     *
     * @param sessionsId ID de sessão relacionado ao usuário que quer "paquerar".
     * @param paqueraLogin login da paquera.
     * @return booleano indicando se é paquera ou não.
     * @throws UserNotRegisteredException caso o usuário não esteja registrado.
     */
    public boolean isPaquera(String sessionsId, String paqueraLogin) throws UserNotRegisteredException {
        User paquerador = this.sessions.getUserBySessionId(sessionsId);
        User paquera = this.users.getUserByLogin(paqueraLogin);

        return paquerador.getRelationManager().paqueraTheUser(paquera);
    }

    /**
     * Recupera toda a lista de paqueras do usuário.
     *
     * @param sessionId ID de sessão relacionaod ao usuário que quer ver suas paqueras.
     * @return a lista de paqueras do usuário como string.
     * @throws UserNotRegisteredException caso o ID não esteja associado a um usuário.
     */
    public String getPaqueras(String sessionId) throws UserNotRegisteredException {
        User paquerador = this.sessions.getUserBySessionId(sessionId);
        return paquerador.getRelationManager().getPaquerasAsString();
    }

    /**
     * Adiciona um outro usuário como inimigo.
     *
     * @param sessionId o identificador da sessão do usuário que está declarando o inimigo
     * @param enemyLogin o login do usuário que será marcado como inimigo
     * @throws UserNotRegisteredException se o usuário da sessão ou o inimigo não estiverem registrados
     * @throws UserAttributeNotFilledException se algum dos atributos obrigatórios do usuário estiver faltando
     * @throws YourselfEnemyException se o usuário tentar se declarar como inimigo de si mesmo
     * @throws EnemyAlredyDeclaredException se o inimigo já tiver sido declarado anteriormente
     */
    public void addEnemy(String sessionId, String enemyLogin) throws UserNotRegisteredException, UserAttributeNotFilledException, YourselfEnemyException, EnemyAlredyDeclaredException {
        User user = this.sessions.getUserBySessionId(sessionId);
        User enemy = this.users.getUserByLogin(enemyLogin);

        if (user == enemy) {
            throw new YourselfEnemyException();
        }

        if (user.getRelationManager().isEnemy(enemy)) {
            throw new EnemyAlredyDeclaredException();
        }

        user.getRelationManager().addEnemy(enemy);
    }

    /**
     * Faz a remoção de usuário do sistema.
     *
     * @param sessionId ID de sessão do usuário a ser removido.
     * @throws UserNotRegisteredException caso o usuário não esteja registrado.
     */
    public void removeUser(String sessionId) throws UserNotRegisteredException {
        User userToRemove = this.sessions.getUserBySessionId(sessionId);

        this.communitys.removeUserFromCommunitys(userToRemove);
        this.users.removeSentMessagesFromUser(userToRemove);
        this.users.removeUserReferences(userToRemove);
        this.sessions.removeSession(sessionId);
    }
}
