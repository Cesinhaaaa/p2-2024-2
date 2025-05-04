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
 * A classe Facade fornece uma interface para interagir com a l�gica do sistema encapsulada na classe Jackut.
 * Ela simplifica a intera��o para gerenciar usu�rios, sess�es, amigos e mensagens dentro do sistema.
 */
public class Facade {
    private final Jackut jackut;

    /**
     * Construtor padr�o da classe Facade.
     * Inicializa a Facade e cria uma inst�ncia da classe Jackut.
     */
    public Facade() {
        this.jackut = new Jackut();
    }

    /**
     * Reinicia o estado do sistema, limpando todos os dados e a mem�ria.
     *
     * Este m�todo chama o m�todo `clearSystem` do objeto `jackut`,
     * que � respons�vel por remover todos os dados serializados e em mem�ria
     * associados a usu�rios, sess�es e outros componentes do sistema.
     *
     * Ap�s a execu��o, nenhuma informa��o de usu�rio ser� mantida no sistema,
     * retornando-o ao estado inicial.
     */
    public void zerarSistema() {
        jackut.clearSystem();
    }

    /**
     * Encerra o sistema executando os procedimentos necess�rios para garantir a persist�ncia dos dados e liberar a mem�ria.
     *
     * Este m�todo chama o m�todo `closeSystem` do objeto `jackut`, que realiza as seguintes a��es:
     * - Salva todos os dados atuais no armazenamento persistente.
     * - Libera a mem�ria, removendo recursos em uso e dados relacionados �s sess�es.
     *
     * Essa opera��o garante que o sistema seja deixado em um estado consistente e limpo, evitando perda de dados e vazamento de recursos.
     */
    public void encerrarSistema() {
        jackut.closeSystem();
    }

    /**
     * Cria um usu�rio no sistema com o login, senha e nome especificados.
     *
     * @param login O identificador de login do novo usu�rio. Deve ser �nico, n�o nulo e n�o vazio.
     * @param senha A senha do novo usu�rio. N�o pode ser nula ou vazia.
     * @param nome O nome do usu�rio a ser associado ao seu perfil.
     *
     * @throws LoginAlredyUsedException Se um usu�rio com o login fornecido j� existir no sistema.
     * @throws InvalidLoginException Se o login fornecido for inv�lido (por exemplo, nulo ou vazio).
     * @throws InvalidPasswordException Se a senha fornecida for inv�lida (por exemplo, nula ou vazia).
     */
    public void criarUsuario(String login, String senha, String nome) throws LoginAlredyUsedException, InvalidLoginException, InvalidPasswordException {
        try {
            jackut.createUser(login, senha, nome);
        } catch (LoginAlredyUsedException | InvalidLoginException | InvalidPasswordException e) {
            throw e;
        }
    }

    /**
     * Abre uma sess�o para um usu�rio com o login e a senha fornecidos.
     *
     * Esse m�todo valida o login e a senha do usu�rio. Se o usu�rio estiver cadastrado
     * e as credenciais estiverem corretas, ele cria uma nova sess�o e retorna um ID de sess�o.
     *
     * @param login O login do usu�rio que deseja abrir uma sess�o.
     * @param senha A senha associada ao login fornecido.
     * @return Um ID de sess�o �nico para o usu�rio autenticado.
     * @throws UserNotRegisteredException Se o login fornecido n�o corresponder a nenhum usu�rio cadastrado.
     * @throws InvalidLoginOrPasswordException Se o login ou senha fornecidos forem inv�lidos.
     */
    public String abrirSessao(String login, String senha) throws UserNotRegisteredException, InvalidLoginOrPasswordException {
        try {
            return jackut.openSession(login, senha);
        } catch (UserNotRegisteredException | InvalidLoginOrPasswordException e) {
            throw e;
        }
    }

    /**
     * Obt�m o valor de um atributo espec�fico de um usu�rio com base no seu login.
     *
     * Esse m�todo interage com o sistema para buscar o atributo desejado de um usu�rio.
     * Lan�a uma exce��o se o usu�rio n�o estiver cadastrado ou se o atributo especificado n�o estiver preenchido.
     *
     * @param login O login do usu�rio cujo atributo ser� recuperado. Deve estar cadastrado no sistema.
     * @param atributo O nome do atributo a ser recuperado. Deve ser uma chave v�lida e n�o nula.
     * @return Uma string contendo o valor do atributo especificado para o usu�rio.
     * @throws UserNotRegisteredException Se o usu�rio com o login fornecido n�o existir no sistema.
     * @throws UserAttributeNotFilledException Se o atributo especificado n�o estiver preenchido para o usu�rio.
     */
    public String getAtributoUsuario(String login, String atributo) throws UserNotRegisteredException, UserAttributeNotFilledException {
        try {
            return jackut.getUserAttribute(login, atributo);
        } catch (UserNotRegisteredException | UserAttributeNotFilledException e) {
            throw e;
        }
    }

    /**
     * Edita o perfil do usu�rio, modificando o valor de um atributo espec�fico.
     *
     * @param id O ID do usu�rio cujo perfil ser� editado.
     * @param atributo O nome do atributo a ser modificado.
     * @param valor O novo valor a ser atribu�do ao atributo.
     * @throws UserNotRegisteredException Se o usu�rio n�o estiver cadastrado no sistema.
     */
    public void editarPerfil(String id, String atributo, String valor) throws UserNotRegisteredException {
        try {
            jackut.updateProfile(id, atributo, valor);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Adiciona um amigo � lista de amigos do usu�rio enviando uma solicita��o de amizade.
     *
     * @param id O ID do usu�rio que est� enviando a solicita��o de amizade.
     * @param amigo O ID do usu�rio que receber� a solicita��o.
     * @throws UserNotRegisteredException Se o remetente ou destinat�rio n�o estiver cadastrado.
     * @throws UserAlredyAddedException Se os usu�rios j� forem amigos.
     * @throws FriendRequestAlredySentException Se uma solicita��o de amizade j� foi enviada anteriormente.
     * @throws YourselfFriendRequestException Se o usu�rio tentar enviar uma solicita��o para si mesmo.
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
     * Verifica se dois usu�rios s�o amigos.
     *
     * @param login O login do usu�rio.
     * @param amigo O login do poss�vel amigo.
     * @return true se os usu�rios forem amigos, false caso contr�rio.
     * @throws UserNotRegisteredException Se o usu�rio n�o estiver cadastrado.
     */
    public boolean ehAmigo(String login, String amigo) throws UserNotRegisteredException {
        try {
            return jackut.isFriend(login, amigo);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Obt�m a lista de amigos do usu�rio identificado pelo login.
     *
     * @param login O login do usu�rio.
     * @return Uma string representando a lista de amigos do usu�rio formatada.
     * @throws UserNotRegisteredException Se o usu�rio n�o estiver cadastrado no sistema.
     */
    public String getAmigos(String login) throws UserNotRegisteredException {
        try {
            return jackut.getUserFriends(login);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Envia uma mensagem de um usu�rio para outro.
     *
     * @param id O ID da sess�o do remetente.
     * @param destinatario O login do destinat�rio.
     * @param mensagem O conte�do da mensagem.
     * @throws UserNotRegisteredException Se o remetente ou destinat�rio n�o estiver cadastrado.
     * @throws YourselfMessageException Se o usu�rio tentar enviar uma mensagem para si mesmo.
     */
    public void enviarRecado(String id, String destinatario, String mensagem) throws UserNotRegisteredException,
            YourselfMessageException, ThisUserIsYourEnemyException, UserAttributeNotFilledException {
        jackut.sendPrivateMessage(id, destinatario, mensagem);
    }

    /**
     * Recupera a proxima mensagem disponivel para o usu�rio identificado por ID.
     *
     * @param id o ID da sess�o associada ao usu�rio que deseja ler o recado
     * @return a pr�xima mensagem disponivel para o usu�rio
     * @throws NoPrivateMessageException se n�o houver mensagens dispon�veis para o usu�rio
     * @throws UserNotRegisteredException se o ID de sess�o n�o estiver associado a um usu�rio
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
     * @param id ID da sess�o do usu�rio que est� criando a comunidade.
     * @param nomeDaComunidade Nome da nova comunidade a ser criada.
     * @param descricaoDaComunidade Descri��o da comunidade.
     * @throws CommunityAlredyExistException caso j� exista uma comunidade com esse nome.
     * @throws UserNotRegisteredException caso o usu�rio da sess�o n�o esteja registrado.
     */
    public void criarComunidade(String id, String nomeDaComunidade, String descricaoDaComunidade) throws CommunityAlredyExistException, UserNotRegisteredException {
        try {
            jackut.createCommunity(id, nomeDaComunidade, descricaoDaComunidade);
        } catch (CommunityAlredyExistException e) {
            throw e;
        }
    }

    /**
     * Retorna a descri��o de uma comunidade existente.
     *
     * @param nome Nome da comunidade.
     * @return A descri��o da comunidade.
     * @throws CommunityNotExistException caso a comunidade n�o exista.
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
     * @throws CommunityNotExistException caso a comunidade n�o exista.
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
     * @throws CommunityNotExistException caso a comunidade n�o exista.
     */
    public String getMembrosComunidade(String nome) throws CommunityNotExistException {
        try {
            return jackut.getCommunityMembers(nome);
        } catch (CommunityNotExistException e) {
            throw e;
        }
    }

    /**
     * Adiciona o usu�rio logado a uma comunidade existente.
     *
     * @param id ID da sess�o do usu�rio.
     * @param nome Nome da comunidade.
     * @throws UserNotRegisteredException caso o usu�rio n�o esteja registrado.
     * @throws CommunityNotExistException caso a comunidade n�o exista.
     * @throws UserAlredyJoinedCommunityException caso o usu�rio j� seja membro da comunidade.
     */
    public void adicionarComunidade(String id, String nome) throws UserNotRegisteredException, CommunityNotExistException, UserAlredyJoinedCommunityException {
        try {
            jackut.joinCommunity(id, nome);
        } catch (UserNotRegisteredException | CommunityNotExistException | UserAlredyJoinedCommunityException e) {
            throw e;
        }
    }

    /**
     * Retorna as comunidades que o usu�rio participa.
     *
     * @param login Login do usu�rio.
     * @return String com os nomes das comunidades no formato "{comunidade1,comunidade2,...}".
     * @throws UserNotRegisteredException caso o usu�rio n�o esteja registrado.
     */
    public String getComunidades(String login) throws UserNotRegisteredException {
        try {
            return jackut.getUserCommunitysByLogin(login);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Envia uma mensagem para uma comunidade da qual o usu�rio participa.
     *
     * @param id ID da sess�o do usu�rio remetente.
     * @param comunidade Nome da comunidade.
     * @param mensagem Conte�do da mensagem.
     * @throws UserNotRegisteredException caso o usu�rio n�o esteja registrado.
     * @throws CommunityNotExistException caso a comunidade n�o exista.
     */
    public void enviarMensagem(String id, String comunidade, String mensagem) throws UserNotRegisteredException, CommunityNotExistException {
        try {
            jackut.sendCommunityMessage(id, comunidade, mensagem);
        } catch (UserNotRegisteredException | CommunityNotExistException e) {
            throw e;
        }
    }

    /**
     * L� a �ltima mensagem enviada em uma das comunidades que o usu�rio participa.
     *
     * @param id ID da sess�o do usu�rio.
     * @return A �ltima mensagem recebida da comunidade.
     * @throws UserNotRegisteredException caso o usu�rio n�o esteja registrado.
     * @throws NoCommunityMessageException caso n�o haja mensagens dispon�veis.
     */
    public String lerMensagem(String id) throws UserNotRegisteredException, NoCommunityMessageException {
        try {
            return jackut.readCommunityMessage(id);
        } catch (UserNotRegisteredException | NoCommunityMessageException e) {
            throw e;
        }
    }

    /**
     * Verifica se o usu�rio logado � f� de outro usu�rio.
     *
     * @param login Login do usu�rio.
     * @param idolo Login do poss�vel �dolo.
     * @return true se o usu�rio for f�, false caso contr�rio.
     * @throws UserNotRegisteredException caso algum dos usu�rios n�o esteja registrado.
     */
    public boolean ehFa(String login, String idolo) throws UserNotRegisteredException {
        try {
            return jackut.isFa(login, idolo);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Adiciona outro usu�rio como �dolo (f�) do usu�rio logado.
     *
     * @param id ID da sess�o do usu�rio.
     * @param idolo Login do usu�rio a ser seguido como �dolo.
     * @throws UserAlredyIsIdolException caso o �dolo j� tenha sido adicionado.
     * @throws YourselfFaException caso o usu�rio tente se adicionar como pr�prio �dolo.
     * @throws UserNotRegisteredException caso algum dos usu�rios n�o esteja registrado.
     * @throws ThisUserIsYourEnemyException caso o �dolo seja um inimigo.
     * @throws UserAttributeNotFilledException caso atributos obrigat�rios do usu�rio n�o estejam preenchidos.
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
     * Retorna os f�s de um usu�rio.
     *
     * @param login Login do usu�rio.
     * @return String com os logins dos f�s no formato "{fa1,fa2,...}".
     * @throws UserNotRegisteredException caso o usu�rio n�o esteja registrado.
     */
    public String getFas(String login) throws UserNotRegisteredException {
        try {
            return jackut.getFas(login);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Verifica se o usu�rio logado tem uma rela��o de paquera com outro usu�rio.
     *
     * @param id ID da sess�o do usu�rio.
     * @param paquera Login do poss�vel paquera.
     * @return true se houver rela��o de paquera, false caso contr�rio.
     * @throws UserNotRegisteredException caso o usu�rio n�o esteja registrado.
     */
    public boolean ehPaquera(String id, String paquera) throws UserNotRegisteredException {
        try {
            return jackut.isPaquera(id, paquera);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Adiciona outro usu�rio como paquera do usu�rio logado.
     *
     * @param id ID da sess�o do usu�rio.
     * @param paquera Login do usu�rio a ser adicionado como paquera.
     * @throws UserAttributeNotFilledException caso atributos obrigat�rios do usu�rio n�o estejam preenchidos.
     * @throws UserNotRegisteredException caso algum dos usu�rios n�o esteja registrado.
     * @throws PaqueraAlredyAddedException caso a rela��o de paquera j� tenha sido estabelecida.
     * @throws YourselfPaqueraException caso o usu�rio tente se adicionar como pr�prio paquera.
     * @throws ThisUserIsYourEnemyException caso o paquera seja um inimigo do usu�rio.
     */
    public void adicionarPaquera(String id, String paquera) throws UserAttributeNotFilledException, UserNotRegisteredException, PaqueraAlredyAddedException, YourselfPaqueraException, ThisUserIsYourEnemyException {
        try {
            jackut.addPaquera(id, paquera);
        } catch (UserAttributeNotFilledException | UserNotRegisteredException | PaqueraAlredyAddedException | YourselfPaqueraException | ThisUserIsYourEnemyException e) {
            throw e;
        }
    }

    /**
     * Retorna os usu�rios com quem o usu�rio logado tem uma rela��o de paquera.
     *
     * @param id ID da sess�o do usu�rio.
     * @return String com os logins das paqueras no formato "{paquera1,paquera2,...}".
     * @throws UserNotRegisteredException caso o usu�rio n�o esteja registrado.
     */
    public String getPaqueras(String id) throws UserNotRegisteredException {
        try {
            return jackut.getPaqueras(id);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }

    /**
     * Adiciona outro usu�rio como inimigo do usu�rio logado.
     *
     * @param id ID da sess�o do usu�rio.
     * @param inimigo Login do usu�rio a ser adicionado como inimigo.
     * @throws UserAttributeNotFilledException caso atributos obrigat�rios do usu�rio n�o estejam preenchidos.
     * @throws UserNotRegisteredException caso algum dos usu�rios n�o esteja registrado.
     * @throws EnemyAlredyDeclaredException caso o inimigo j� tenha sido declarado.
     * @throws YourselfEnemyException caso o usu�rio tente se adicionar como pr�prio inimigo.
     */
    public void adicionarInimigo(String id, String inimigo) throws UserAttributeNotFilledException, UserNotRegisteredException, EnemyAlredyDeclaredException, YourselfEnemyException {
        try {
            jackut.addEnemy(id, inimigo);
        } catch (UserAttributeNotFilledException | UserNotRegisteredException | EnemyAlredyDeclaredException | YourselfEnemyException e) {
            throw e;
        }
    }

    /**
     * Remove um usu�rio do sistema.
     *
     * @param id ID do usu�rio a ser removido.
     * @throws UserNotRegisteredException caso o usu�rio n�o esteja registrado.
     */
    public void removerUsuario(String id) throws UserNotRegisteredException {
        try {
            jackut.removeUser(id);
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }
}

