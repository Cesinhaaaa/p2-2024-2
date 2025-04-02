package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.code.Jackut;
import br.ufal.ic.p2.jackut.exceptions.*;

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
     * @throws AttributeNotFilledException Se o atributo especificado não estiver preenchido para o usuário.
     */
    public String getAtributoUsuario(String login, String atributo) throws UserNotRegisteredException, AttributeNotFilledException {
        try {
            return jackut.getUserAttribute(login, atributo);
        } catch (UserNotRegisteredException | AttributeNotFilledException e) {
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
    public void adicionarAmigo(String id, String amigo) throws UserNotRegisteredException, UserAlredyAddedException, FriendRequestAlredySentException, YourselfFriendRequestException {
        try {
            jackut.addFriend(id, amigo);
        } catch (UserNotRegisteredException | FriendRequestAlredySentException | YourselfFriendRequestException | UserAlredyAddedException e) {
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
    public void enviarRecado(String id, String destinatario, String mensagem) throws UserNotRegisteredException, YourselfMessageException {
        jackut.sendMessage(id, destinatario, mensagem);
    }

    /**
     * Recupera a proxima mensagem disponivel para o usuário identificado por ID.
     *
     * @param id o ID da sessão associada ao usuário que deseja ler o recado
     * @return a próxima mensagem disponivel para o usuário
     * @throws NoMessageException se não houver mensagens disponíveis para o usuário
     * @throws UserNotRegisteredException se o ID de sessão não estiver associado a um usuário
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

