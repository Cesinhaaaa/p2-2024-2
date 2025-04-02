package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.code.Jackut;
import br.ufal.ic.p2.jackut.exceptions.*;

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
     * @throws AttributeNotFilledException Se o atributo especificado n�o estiver preenchido para o usu�rio.
     */
    public String getAtributoUsuario(String login, String atributo) throws UserNotRegisteredException, AttributeNotFilledException {
        try {
            return jackut.getUserAttribute(login, atributo);
        } catch (UserNotRegisteredException | AttributeNotFilledException e) {
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
    public void adicionarAmigo(String id, String amigo) throws UserNotRegisteredException, UserAlredyAddedException, FriendRequestAlredySentException, YourselfFriendRequestException {
        try {
            jackut.addFriend(id, amigo);
        } catch (UserNotRegisteredException | FriendRequestAlredySentException | YourselfFriendRequestException | UserAlredyAddedException e) {
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
    public void enviarRecado(String id, String destinatario, String mensagem) throws UserNotRegisteredException, YourselfMessageException {
        jackut.sendMessage(id, destinatario, mensagem);
    }

    /**
     * Recupera a proxima mensagem disponivel para o usu�rio identificado por ID.
     *
     * @param id o ID da sess�o associada ao usu�rio que deseja ler o recado
     * @return a pr�xima mensagem disponivel para o usu�rio
     * @throws NoMessageException se n�o houver mensagens dispon�veis para o usu�rio
     * @throws UserNotRegisteredException se o ID de sess�o n�o estiver associado a um usu�rio
     */
    public String lerRecado(String id) throws NoMessageException, UserNotRegisteredException {
        try {
            return jackut.readMessage(id);
        } catch (NoMessageException e) {
            throw e;
        } catch (UserNotRegisteredException e) {
            throw e;
        }
    }
}

