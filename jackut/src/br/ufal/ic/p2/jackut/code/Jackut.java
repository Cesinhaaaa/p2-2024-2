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
 * A classe Jackut fornece a funcionalidade principal para um sistema de m�dia social,
 * incluindo gerenciamento de usu�rios, gerenciamento de sess�es e recursos para interagir
 * com outros usu�rios.
 */
public class Jackut {
    private final UserManager users;
    private final SessionManager sessions;
    private final CommunityManager communitys;

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
        this.communitys = new CommunityManager("data/communitys.ser");

        this.readData();
    }

    /**
     * Faz chamadas para os respectivos m�todos de Limpeza de todos os dados na inst�ncia do sistema.
     */
    private void clearMemory() {
        this.users.clearUsers();
        this.sessions.clearSessions();
        this.communitys.clearCommunitys();
    }

    /**
     * Serializa todos os dados necess�rios para a persist�ncia da aplica��o.
     */
    private void saveData() {
        this.users.serialize();
        this.communitys.serialize();
    }

    /**
     * Desserializa todos os dados que foram previamente serializados para a persist�ncia da aplica��o.
     */
    private void readData() {
        this.users.deserialize();
        this.communitys.deserialize();
    }

    /**
     * Apaga todos os dados serializados, perdendo a persist�ncia da aplica��o.
     */
    private void clearData() {
        this.users.clearSerialized();
        this.communitys.clearSerialized();
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
     * @throws UserAttributeNotFilledException Se o usu�rio existir, mas o atributo especificado n�o estiver preenchido.
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
            User friend = this.users.getUserByLogin(friendLogin);
            return user.getRelationManager().isFriend(friend);
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
     * @throws UserAttributeNotFilledException Caso o atributo "nome" n�o esteja preenchido.
     * @throws ThisUserIsYourEnemyException Se caso o usu�rio tente interagir com alguem que o declarou como inimigo.
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
            return user.getRelationManager().getFriendsAsString();
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
     * @param receiverLogin o identificador de login do receptor. Usado para localizar o receptor no sistema.
     * @param messageContent o conte�do da mensagem a ser enviada do remetente para o receptor.
     * @throws UserNotRegisteredException se o ID da sess�o do remetente ou o login do receptor n�o corresponder a nenhum usu�rio registrado no sistema.
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
     * L� e recupera a pr�xima mensagem dispon�vel para o usu�rio associado ao ID de sess�o fornecido.
     *
     * @param sessionId o ID da sess�o associado ao usu�rio cuja mensagem deve ser lida
     * @return a pr�xima mensagem dispon�vel para o usu�rio
     * @throws NoPrivateMessageException se n�o houver mensagens dispon�veis para o usu�rio
     * @throws UserNotRegisteredException se o usu�rio associado ao ID da sess�o n�o estiver registrado
     */
    public String readPrivateMessage(String sessionId) throws NoPrivateMessageException, UserNotRegisteredException {
        User user = this.sessions.getUserBySessionId(sessionId);
        return user.readPrivateMessage();
    }

    /**
     * Cria e adiciona uma comunidade ao gerenciador de comunidades do jackut.
     *
     * @param sessionId o Id da sess�o associado ao usu�rio que est� criando a comunidade.
     * @param communityName o nome da comunidade
     * @param communityDescription a descri��o da comunidade
     * @throws CommunityAlredyExistException caso j� exista uma comunidade com o mesmo nome.
     * @throws UserNotRegisteredException caso o ID n�o esteja associado a nenhum usu�rio.
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
     * Retorna a descri��o de uma comunidade pelo nome.
     *
     * @param communityName o nome da comunidade
     * @return a descri��o da comunidade
     * @throws CommunityNotExistException se a comunidade n�o existir
     */
    public String getCommunityDescription(String communityName) throws CommunityNotExistException {
        return this.communitys.getCommunityByName(communityName).getDescription();
    }

    /**
     * Retorna o login do dono de uma comunidade.
     *
     * @param communityName o nome da comunidade
     * @return o login do propriet�rio da comunidade
     * @throws CommunityNotExistException se a comunidade n�o existir
     */
    public String getCommunityOwner(String communityName) throws CommunityNotExistException {
        return this.communitys.getCommunityByName(communityName).getOwner().getLogin();
    }

    /**
     * Retorna a lista de membros de uma comunidade em formato de string.
     *
     * @param communityName o nome da comunidade
     * @return string com os membros da comunidade
     * @throws CommunityNotExistException se a comunidade n�o existir
     */
    public String getCommunityMembers(String communityName) throws CommunityNotExistException {
        return this.communitys.getCommunityByName(communityName).getMemberListAsString();
    }

    /**
     * Retorna as comunidades que um usu�rio participa com base no login.
     *
     * @param login o login do usu�rio
     * @return string com os nomes das comunidades
     * @throws UserNotRegisteredException se o usu�rio n�o estiver registrado
     */
    public String getUserCommunitysByLogin(String login) throws UserNotRegisteredException {
        return this.users.getUserByLogin(login).getCommunitysAsString();
    }

    /**
     * Adiciona um usu�rio logado em uma sess�o a uma comunidade.
     *
     * @param sessionId o identificador da sess�o do usu�rio
     * @param communityName o nome da comunidade
     * @throws UserNotRegisteredException se o usu�rio n�o estiver registrado
     * @throws CommunityNotExistException se a comunidade n�o existir
     * @throws UserAlredyJoinedCommunityException se o usu�rio j� for membro da comunidade
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
     * Envia uma mensagem do usu�rio identificado pelo ID de sess�o fornecido para outro usu�rio
     * identificado por seu login. Esse m�todo garante que o remetente seja um usu�rio ativo e v�lido
     * sess�o de usu�rio ativa e v�lida e que o receptor existe no sistema, evitando o envio de
     * mensagens para si mesmo.
     *
     * @param sessionId o identificador exclusivo da sess�o do remetente. Usado para recuperar as informa��es do usu�rio do remetente.
     * @param communityName o identificador da comunidade. Usado para localizar para localizar a comunidade receptora da mensagem no sistema.
     * @param messageContent o conte�do da mensagem a ser enviada do remetente para o receptor.
     * @throws UserNotRegisteredException se o ID da sess�o do remetente ou o login do receptor
     * n�o corresponder a nenhum usu�rio registrado no sistema.
     * @throws CommunityNotExistException se a comunidade que se deseja enviar a mensagem n�o existir
     */
    public void sendCommunityMessage(String sessionId, String communityName, String messageContent) throws UserNotRegisteredException, CommunityNotExistException {
        User sender = this.sessions.getUserBySessionId(sessionId);
        Community community = this.communitys.getCommunityByName(communityName);

        Message message = new CommunityMessage(sender, community, messageContent);
        message.send();
    }

    /**
     * L� e recupera a pr�xima mensagem dispon�vel para o usu�rio associado ao ID de sess�o fornecido.
     *
     * @param sessionId o ID da sess�o associado ao usu�rio cuja mensagem deve ser lida
     * @return a pr�xima mensagem dispon�vel para o usu�rio
     * @throws NoCommunityMessageException se n�o houver mensagens dispon�veis para o usu�rio
     * @throws UserNotRegisteredException se o usu�rio associado ao ID da sess�o n�o estiver registrado
     */
    public String readCommunityMessage(String sessionId) throws NoCommunityMessageException, UserNotRegisteredException {
        User user = this.sessions.getUserBySessionId(sessionId);
        return user.readCommunityMessage();
    }

    /**
     * Adiciona um outro usu�rio como �dolo ap�s as devidas verifica��es.
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
     * Verifica se um usu�rio � f� de outro usu�rio.
     *
     * @param faLogin login relacionado ao f�.
     * @param idolLogin login relacionado ao idolo.
     * @return booleano indicando se � f� ou n�o.
     * @throws UserNotRegisteredException caso algum dos logins n�o estejam registrados na aplica��o.
     */
    public boolean isFa(String faLogin, String idolLogin) throws UserNotRegisteredException {
        User fa = this.users.getUserByLogin(faLogin);
        User idol = this.users.getUserByLogin(idolLogin);

        return idol.getRelationManager().isIdol(fa);
    }

    /**
     * Recupera a lista de f�s de um usu�rio.
     *
     * @param idolLogin login do idolo cuja lista de f�s deve ser recuperada.
     * @return uma lista na forma de string com os f�s do usu�rio.
     * @throws UserNotRegisteredException caso o login n�o esteja associado a um usu�rio.
     */
    public String getFas(String idolLogin) throws UserNotRegisteredException {
        User idol = this.users.getUserByLogin(idolLogin);
        return idol.getRelationManager().getFasAsString();
    }

    /**
     * Adiciona um outro usu�rio como paquera de forma privada ap�s as verifica��es.
     * Tambem verifica se a paquera � mutua.
     *
     * @param sessionId o identificador da sess�o do usu�rio que est� adicionando a paquera
     * @param paqueraLogin o login do usu�rio a ser adicionado como paquera
     * @throws UserNotRegisteredException se o usu�rio da sess�o ou o paquera n�o estiverem registrados
     * @throws UserAttributeNotFilledException se algum dos atributos obrigat�rios do usu�rio estiver faltando
     * @throws YourselfPaqueraException se o usu�rio tentar adicionar a si mesmo como paquera
     * @throws PaqueraAlredyAddedException se o usu�rio j� tiver adicionado essa pessoa como paquera anteriormente
     * @throws ThisUserIsYourEnemyException se o usu�rio alvo for inimigo do usu�rio da sess�o
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
            String contentToPaquerador = paquera.getAttribute("nome") + " � seu paquera - Recado do Jackut.";
            String contentToPaquera = paquerador.getAttribute("nome") + " � seu paquera - Recado do Jackut.";

            Message messageToPaquerador = new PrivateMessage(paquera, paquerador, contentToPaquerador);
            Message messageToPaquera = new PrivateMessage(paquerador, paquera, contentToPaquera);

            messageToPaquerador.send();
            messageToPaquera.send();
        }
    }

    /**
     * Verifica se um usu�rio � paquera de outro.
     *
     * @param sessionsId ID de sess�o relacionado ao usu�rio que quer "paquerar".
     * @param paqueraLogin login da paquera.
     * @return booleano indicando se � paquera ou n�o.
     * @throws UserNotRegisteredException caso o usu�rio n�o esteja registrado.
     */
    public boolean isPaquera(String sessionsId, String paqueraLogin) throws UserNotRegisteredException {
        User paquerador = this.sessions.getUserBySessionId(sessionsId);
        User paquera = this.users.getUserByLogin(paqueraLogin);

        return paquerador.getRelationManager().paqueraTheUser(paquera);
    }

    /**
     * Recupera toda a lista de paqueras do usu�rio.
     *
     * @param sessionId ID de sess�o relacionaod ao usu�rio que quer ver suas paqueras.
     * @return a lista de paqueras do usu�rio como string.
     * @throws UserNotRegisteredException caso o ID n�o esteja associado a um usu�rio.
     */
    public String getPaqueras(String sessionId) throws UserNotRegisteredException {
        User paquerador = this.sessions.getUserBySessionId(sessionId);
        return paquerador.getRelationManager().getPaquerasAsString();
    }

    /**
     * Adiciona um outro usu�rio como inimigo.
     *
     * @param sessionId o identificador da sess�o do usu�rio que est� declarando o inimigo
     * @param enemyLogin o login do usu�rio que ser� marcado como inimigo
     * @throws UserNotRegisteredException se o usu�rio da sess�o ou o inimigo n�o estiverem registrados
     * @throws UserAttributeNotFilledException se algum dos atributos obrigat�rios do usu�rio estiver faltando
     * @throws YourselfEnemyException se o usu�rio tentar se declarar como inimigo de si mesmo
     * @throws EnemyAlredyDeclaredException se o inimigo j� tiver sido declarado anteriormente
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
     * Faz a remo��o de usu�rio do sistema.
     *
     * @param sessionId ID de sess�o do usu�rio a ser removido.
     * @throws UserNotRegisteredException caso o usu�rio n�o esteja registrado.
     */
    public void removeUser(String sessionId) throws UserNotRegisteredException {
        User userToRemove = this.sessions.getUserBySessionId(sessionId);

        this.communitys.removeUserFromCommunitys(userToRemove);
        this.users.removeSentMessagesFromUser(userToRemove);
        this.users.removeUserReferences(userToRemove);
        this.sessions.removeSession(sessionId);
    }
}
