package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.code.Jackut;
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
 * A classe Facade fornece uma interface para interagir com a lógica do sistema encapsulada na classe Jackut.
 * Ela simplifica a interação para gerenciar usuários, sessões, amigos e mensagens dentro do sistema.
 */
public class Facade {
    private final Jackut jackut;

    /**
     * Construtor padrão da classe Facade.
     * Inicializa a Facade e cria uma instância da classe Jackut.
     */
    public Facade() {
        this.jackut = new Jackut();
    }

    /**
     * Reinicia o estado do sistema, limpando todos os dados e a memória.
     *
     * Este método chama o método `clearSystem` do objeto `jackut`,
     * que é responsável por remover todos os dados serializados e em memória
     * associados a usuários, sessões e outros componentes do sistema.
     *
     * Após a execução, nenhuma informação de usuário será mantida no sistema,
     * retornando-o ao estado inicial.
     */
    public void zerarSistema() {
        jackut.clearSystem();
    }

    /**
     * Encerra o sistema executando os procedimentos necessários para garantir a persistência dos dados e liberar a memória.
     *
     * Este método chama o método `closeSystem` do objeto `jackut`, que realiza as seguintes ações:
     * - Salva todos os dados atuais no armazenamento persistente.
     * - Libera a memória, removendo recursos em uso e dados relacionados às sessões.
     *
     * Essa operação garante que o sistema seja deixado em um estado consistente e limpo, evitando perda de dados e vazamento de recursos.
     */
    public void encerrarSistema() {
        jackut.closeSystem();
    }

    /**
     * Cria um usuário no sistema com o login, senha e nome especificados.
     *
     * @param login O identificador de login do novo usuário. Deve ser único, não nulo e não vazio.
     * @param senha A senha do novo usuário. Não pode ser nula ou vazia.
     * @param nome O nome do usuário a ser associado ao seu perfil.
     *
     * @throws LoginAlredyUsedException Se um usuário com o login fornecido já existir no sistema.
     * @throws InvalidLoginException Se o login fornecido for inválido (por exemplo, nulo ou vazio).
     * @throws InvalidPasswordException Se a senha fornecida for inválida (por exemplo, nula ou vazia).
     */
    public void criarUsuario(String login, String senha, String nome) throws LoginAlredyUsedException, InvalidLoginException, InvalidPasswordException {
        try {
            jackut.createUser(login, senha, nome);
        } catch (LoginAlredyUsedException | InvalidLoginException | InvalidPasswordException e) {
            throw e;
        }
    }

    /**
     * Abre uma sessão para um usuário com o login e a senha fornecidos.
     *
     * Esse método valida o login e a senha do usuário. Se o usuário estiver cadastrado
     * e as credenciais estiverem corretas, ele cria uma nova sessão e retorna um ID de sessão.
     *
     * @param login O login do usuário que deseja abrir uma sessão.
     * @param senha A senha associada ao login fornecido.
     * @return Um ID de sessão único para o usuário autenticado.
     * @throws UserNotRegisteredException Se o login fornecido não corresponder a nenhum usuário cadastrado.
     * @throws InvalidLoginOrPasswordException Se o login ou senha fornecidos forem inválidos.
     */
    public String abrirSessao(String login, String senha) throws UserNotRegisteredException, InvalidLoginOrPasswordException {
        try {
            return jackut.openSession(login, senha);
        } catch (UserNotRegisteredException | InvalidLoginOrPasswordException e) {
            throw e;
        }
    }

    /**
     * Obtém o valor de um atributo específico de um usuário com base no seu login.
     *
     * Esse método interage com o sistema para buscar o atributo desejado de um usuário.
     * Lança uma exceção se o usuário não estiver cadastrado ou se o atributo especificado não estiver preenchido.
     *
     * @param login O login do usuário cujo atributo será recuperado. Deve estar cadastrado no sistema.
     * @param atributo O nome do atributo a ser recuperado. Deve ser uma chave válida e não nula.
     * @return Uma string contendo o valor do atributo especificado para o usuário.
     * @throws UserNotRegisteredException Se o usuário com o login fornecido não existir no sistema.
     * @throws UserAttributeNotFilledException Se o atributo especificado não estiver preenchido para o usuário.
     */
    public String getAtributoUsuario(String login, String atributo) throws UserNotRegisteredException, UserAttributeNotFilledException {
        try {
            return jackut.getUserAttribute(login, atributo);
        } catch (UserNotRegisteredException | UserAttributeNotFilledException e) {
            throw e;
        }
    }

    /**
     * Edita o perfil do usuário, modificando o valor de um atributo específico.
     *
     * @param id O ID do usuário cujo perfil será editado.
     * @param atributo O nome do atributo a ser modificado.
     * @param valor O novo valor a ser atribuído ao atributo.
     * @throws UserNotRegisteredException Se o usuário não estiver cadastrado no sistema.
     */
    public void editarPerfil(String id, String atributo, String valor) throws UserNotRegisteredException {
        try {
            jackut.updateProfile(id, atributo, valor);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Adiciona um amigo à lista de amigos do usuário enviando uma solicitação de amizade.
     *
     * @param id O ID do usuário que está enviando a solicitação de amizade.
     * @param amigo O ID do usuário que receberá a solicitação.
     * @throws UserNotRegisteredException Se o remetente ou destinatário não estiver cadastrado.
     * @throws UserAlredyAddedException Se os usuários já forem amigos.
     * @throws FriendRequestAlredySentException Se uma solicitação de amizade já foi enviada anteriormente.
     * @throws YourselfFriendRequestException Se o usuário tentar enviar uma solicitação para si mesmo.
     */
    public void adicionarAmigo(String id, String amigo) throws UserNotRegisteredException, UserAlredyAddedException,
            FriendRequestAlredySentException, YourselfFriendRequestException, ThisUserIsYourEnemyException, UserAttributeNotFilledException {
        try {
            jackut.addFriend(id, amigo);
        } catch (UserNotRegisteredException | FriendRequestAlredySentException | YourselfFriendRequestException |
                 UserAlredyAddedException | ThisUserIsYourEnemyException | UserAttributeNotFilledException e) {
            throw e;
        }
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param login O login do usuário.
     * @param amigo O login do possível amigo.
     * @return true se os usuários forem amigos, false caso contrário.
     * @throws UserNotRegisteredException Se o usuário não estiver cadastrado.
     */
    public boolean ehAmigo(String login, String amigo) throws UserNotRegisteredException {
        try {
            return jackut.isFriend(login, amigo);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Obtém a lista de amigos do usuário identificado pelo login.
     *
     * @param login O login do usuário.
     * @return Uma string representando a lista de amigos do usuário formatada.
     * @throws UserNotRegisteredException Se o usuário não estiver cadastrado no sistema.
     */
    public String getAmigos(String login) throws UserNotRegisteredException {
        try {
            return jackut.getUserFriends(login);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Envia uma mensagem de um usuário para outro.
     *
     * @param id O ID da sessão do remetente.
     * @param destinatario O login do destinatário.
     * @param mensagem O conteúdo da mensagem.
     * @throws UserNotRegisteredException Se o remetente ou destinatário não estiver cadastrado.
     * @throws YourselfMessageException Se o usuário tentar enviar uma mensagem para si mesmo.
     */
    public void enviarRecado(String id, String destinatario, String mensagem) throws UserNotRegisteredException,
            YourselfMessageException, ThisUserIsYourEnemyException, UserAttributeNotFilledException {
        jackut.sendPrivateMessage(id, destinatario, mensagem);
    }

    /**
     * Recupera a proxima mensagem disponivel para o usuário identificado por ID.
     *
     * @param id o ID da sessão associada ao usuário que deseja ler o recado
     * @return a próxima mensagem disponivel para o usuário
     * @throws NoPrivateMessageException se não houver mensagens disponíveis para o usuário
     * @throws UserNotRegisteredException se o ID de sessão não estiver associado a um usuário
     */
    public String lerRecado(String id) throws NoPrivateMessageException, UserNotRegisteredException {
        try {
            return jackut.readPrivateMessage(id);
        } catch (NoPrivateMessageException | UserNotRegisteredException e) {
            throw e;
        }

    }

    /**
     * Cria uma nova comunidade no sistema.
     *
     * @param id ID da sessão do usuário que está criando a comunidade.
     * @param nomeDaComunidade Nome da nova comunidade a ser criada.
     * @param descricaoDaComunidade Descrição da comunidade.
     * @throws CommunityAlredyExistException caso já exista uma comunidade com esse nome.
     * @throws UserNotRegisteredException caso o usuário da sessão não esteja registrado.
     */
    public void criarComunidade(String id, String nomeDaComunidade, String descricaoDaComunidade) throws CommunityAlredyExistException, UserNotRegisteredException {
        try {
            jackut.createCommunity(id, nomeDaComunidade, descricaoDaComunidade);
        } catch (CommunityAlredyExistException e) {
            throw e;
        }
    }

    /**
     * Retorna a descrição de uma comunidade existente.
     *
     * @param nome Nome da comunidade.
     * @return A descrição da comunidade.
     * @throws CommunityNotExistException caso a comunidade não exista.
     */
    public String getDescricaoComunidade(String nome) throws CommunityNotExistException {
        try {
            return jackut.getCommunityDescription(nome);
        } catch (CommunityNotExistException e) {
            throw e;
        }
    }

    /**
     * Retorna o login do dono (criador) de uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @return O login do dono da comunidade.
     * @throws CommunityNotExistException caso a comunidade não exista.
     */
    public String getDonoComunidade(String nome) throws CommunityNotExistException {
        try {
            return jackut.getCommunityOwner(nome);
        } catch (CommunityNotExistException e) {
            throw e;
        }
    }

    /**
     * Retorna a lista de membros de uma comunidade no formato de string.
     *
     * @param nome Nome da comunidade.
     * @return Lista dos logins dos membros no formato "{login1,login2,...}".
     * @throws CommunityNotExistException caso a comunidade não exista.
     */
    public String getMembrosComunidade(String nome) throws CommunityNotExistException {
        try {
            return jackut.getCommunityMembers(nome);
        } catch (CommunityNotExistException e) {
            throw e;
        }
    }

    /**
     * Adiciona o usuário logado a uma comunidade existente.
     *
     * @param id ID da sessão do usuário.
     * @param nome Nome da comunidade.
     * @throws UserNotRegisteredException caso o usuário não esteja registrado.
     * @throws CommunityNotExistException caso a comunidade não exista.
     * @throws UserAlredyJoinedCommunityException caso o usuário já seja membro da comunidade.
     */
    public void adicionarComunidade(String id, String nome) throws UserNotRegisteredException, CommunityNotExistException, UserAlredyJoinedCommunityException {
        try {
            jackut.joinCommunity(id, nome);
        } catch (UserNotRegisteredException | CommunityNotExistException | UserAlredyJoinedCommunityException e) {
            throw e;
        }
    }

    /**
     * Retorna as comunidades que o usuário participa.
     *
     * @param login Login do usuário.
     * @return String com os nomes das comunidades no formato "{comunidade1,comunidade2,...}".
     * @throws UserNotRegisteredException caso o usuário não esteja registrado.
     */
    public String getComunidades(String login) throws UserNotRegisteredException {
        try {
            return jackut.getUserCommunitysByLogin(login);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Envia uma mensagem para uma comunidade da qual o usuário participa.
     *
     * @param id ID da sessão do usuário remetente.
     * @param comunidade Nome da comunidade.
     * @param mensagem Conteúdo da mensagem.
     * @throws UserNotRegisteredException caso o usuário não esteja registrado.
     * @throws CommunityNotExistException caso a comunidade não exista.
     */
    public void enviarMensagem(String id, String comunidade, String mensagem) throws UserNotRegisteredException, CommunityNotExistException {
        try {
            jackut.sendCommunityMessage(id, comunidade, mensagem);
        } catch (UserNotRegisteredException | CommunityNotExistException e) {
            throw e;
        }
    }

    /**
     * Lê a última mensagem enviada em uma das comunidades que o usuário participa.
     *
     * @param id ID da sessão do usuário.
     * @return A última mensagem recebida da comunidade.
     * @throws UserNotRegisteredException caso o usuário não esteja registrado.
     * @throws NoCommunityMessageException caso não haja mensagens disponíveis.
     */
    public String lerMensagem(String id) throws UserNotRegisteredException, NoCommunityMessageException {
        try {
            return jackut.readCommunityMessage(id);
        } catch (UserNotRegisteredException | NoCommunityMessageException e) {
            throw e;
        }
    }

    /**
     * Verifica se o usuário logado é fã de outro usuário.
     *
     * @param login Login do usuário.
     * @param idolo Login do possível ídolo.
     * @return true se o usuário for fã, false caso contrário.
     * @throws UserNotRegisteredException caso algum dos usuários não esteja registrado.
     */
    public boolean ehFa(String login, String idolo) throws UserNotRegisteredException {
        try {
            return jackut.isFa(login, idolo);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Adiciona outro usuário como ídolo (fã) do usuário logado.
     *
     * @param id ID da sessão do usuário.
     * @param idolo Login do usuário a ser seguido como ídolo.
     * @throws UserAlredyIsIdolException caso o ídolo já tenha sido adicionado.
     * @throws YourselfFaException caso o usuário tente se adicionar como próprio ídolo.
     * @throws UserNotRegisteredException caso algum dos usuários não esteja registrado.
     * @throws ThisUserIsYourEnemyException caso o ídolo seja um inimigo.
     * @throws UserAttributeNotFilledException caso atributos obrigatórios do usuário não estejam preenchidos.
     */
    public void adicionarIdolo(String id, String idolo) throws UserAlredyIsIdolException, YourselfFaException, UserNotRegisteredException, ThisUserIsYourEnemyException, UserAttributeNotFilledException {
        try {
            jackut.addIdol(id, idolo);
        } catch (UserAlredyIsIdolException | YourselfFaException | UserNotRegisteredException | ThisUserIsYourEnemyException |
                 UserAttributeNotFilledException e) {
            throw e;
        }
    }

    /**
     * Retorna os fãs de um usuário.
     *
     * @param login Login do usuário.
     * @return String com os logins dos fãs no formato "{fa1,fa2,...}".
     * @throws UserNotRegisteredException caso o usuário não esteja registrado.
     */
    public String getFas(String login) throws UserNotRegisteredException {
        try {
            return jackut.getFas(login);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Verifica se o usuário logado tem uma relação de paquera com outro usuário.
     *
     * @param id ID da sessão do usuário.
     * @param paquera Login do possível paquera.
     * @return true se houver relação de paquera, false caso contrário.
     * @throws UserNotRegisteredException caso o usuário não esteja registrado.
     */
    public boolean ehPaquera(String id, String paquera) throws UserNotRegisteredException {
        try {
            return jackut.isPaquera(id, paquera);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Adiciona outro usuário como paquera do usuário logado.
     *
     * @param id ID da sessão do usuário.
     * @param paquera Login do usuário a ser adicionado como paquera.
     * @throws UserAttributeNotFilledException caso atributos obrigatórios do usuário não estejam preenchidos.
     * @throws UserNotRegisteredException caso algum dos usuários não esteja registrado.
     * @throws PaqueraAlredyAddedException caso a relação de paquera já tenha sido estabelecida.
     * @throws YourselfPaqueraException caso o usuário tente se adicionar como próprio paquera.
     * @throws ThisUserIsYourEnemyException caso o paquera seja um inimigo do usuário.
     */
    public void adicionarPaquera(String id, String paquera) throws UserAttributeNotFilledException, UserNotRegisteredException, PaqueraAlredyAddedException, YourselfPaqueraException, ThisUserIsYourEnemyException {
        try {
            jackut.addPaquera(id, paquera);
        } catch (UserAttributeNotFilledException | UserNotRegisteredException | PaqueraAlredyAddedException | YourselfPaqueraException | ThisUserIsYourEnemyException e) {
            throw e;
        }
    }

    /**
     * Retorna os usuários com quem o usuário logado tem uma relação de paquera.
     *
     * @param id ID da sessão do usuário.
     * @return String com os logins das paqueras no formato "{paquera1,paquera2,...}".
     * @throws UserNotRegisteredException caso o usuário não esteja registrado.
     */
    public String getPaqueras(String id) throws UserNotRegisteredException {
        try {
            return jackut.getPaqueras(id);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Adiciona outro usuário como inimigo do usuário logado.
     *
     * @param id ID da sessão do usuário.
     * @param inimigo Login do usuário a ser adicionado como inimigo.
     * @throws UserAttributeNotFilledException caso atributos obrigatórios do usuário não estejam preenchidos.
     * @throws UserNotRegisteredException caso algum dos usuários não esteja registrado.
     * @throws EnemyAlredyDeclaredException caso o inimigo já tenha sido declarado.
     * @throws YourselfEnemyException caso o usuário tente se adicionar como próprio inimigo.
     */
    public void adicionarInimigo(String id, String inimigo) throws UserAttributeNotFilledException, UserNotRegisteredException, EnemyAlredyDeclaredException, YourselfEnemyException {
        try {
            jackut.addEnemy(id, inimigo);
        } catch (UserAttributeNotFilledException | UserNotRegisteredException | EnemyAlredyDeclaredException | YourselfEnemyException e) {
            throw e;
        }
    }

    /**
     * Remove um usuário do sistema.
     *
     * @param id ID do usuário a ser removido.
     * @throws UserNotRegisteredException caso o usuário não esteja registrado.
     */
    public void removerUsuario(String id) throws UserNotRegisteredException {
        try {
            jackut.removeUser(id);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }
}

