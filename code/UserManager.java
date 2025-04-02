package br.ufal.ic.p2.jackut.code;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A classe UserManager é responsável por gerenciar os dados dos usuários dentro de uma aplicação.
 * Ela estende a classe SerializableData, que facilita a serialização e desserialização
 * dos dados para um arquivo. A classe mantém uma lista de usuários e fornece métodos
 * para operações de gerenciamento de usuários, como adicionar novos usuários, limpar a lista,
 * verificar logins existentes e recuperar detalhes de usuários.
 */
public class UserManager extends SerializableData {
    private List<User> userList;

    /**
     * Constrói uma nova instância de UserManager, inicializando a lista de usuários para gerenciar os usuários
     * e configurando o caminho do arquivo para persistência de dados.
     *
     * @param filePath o caminho do arquivo onde os dados dos usuários serão serializados e armazenados
     */
    public UserManager(String filePath) {
        super(filePath);
        this.userList = new ArrayList<User>();
    }

    /**
     * Limpa a lista de usuários gerenciada pela instância de UserManager.
     *
     * Este método remove todas as entradas da lista interna de usuários, efetivamente
     * resetando o sistema de gerenciamento de usuários para um estado sem usuários.
     * Essa ação é irreversível e deve ser usada com cautela, pois todos os dados
     * dos usuários gerenciados por esta instância serão perdidos.
     */
    public void clearUsers() {
        this.userList.clear();
    }

    /**
     * Recupera um usuário pelo seu login.
     *
     * Este método busca um usuário na lista de usuários que corresponda ao login especificado.
     * Se um usuário correspondente for encontrado, ele é retornado. Caso contrário,
     * uma exceção UserNotRegisteredException é lançada.
     *
     * @param login o login do usuário a ser recuperado
     * @return o objeto User associado ao login especificado
     * @throws UserNotRegisteredException se nenhum usuário com o login especificado for encontrado
     */
    public User getUserByLogin(String login) throws UserNotRegisteredException {
        for (User user : this.userList) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        throw new UserNotRegisteredException();
    }

    /**
     * Verifica se um determinado login já está em uso por algum usuário na lista de usuários.
     *
     * @param login o login a ser verificado na lista de usuários
     * @return true se o login já existir, false caso contrário
     */
    public boolean loginPreExist(String login) {
        for (User user : this.userList) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cria um novo usuário com o login, senha e nome de usuário especificados.
     * Se o login já existir, uma exceção será lançada.
     *
     * @param login o nome de login para o novo usuário
     * @param password a senha para o novo usuário
     * @param userName o nome do usuário
     * @throws LoginAlredyUsedException se o login especificado já estiver em uso
     * @throws InvalidLoginException se o login fornecido for inválido
     * @throws InvalidPasswordException se a senha fornecida for inválida
     */
    public void createUser(String login, String password, String userName) throws LoginAlredyUsedException, InvalidLoginException, InvalidPasswordException {
        if (this.loginPreExist(login)) {
            throw new LoginAlredyUsedException();
        } else {
            try {
                User user = new User(login, password, userName);
                this.userList.add(user);
            } catch (InvalidLoginException e) {
                throw e;
            } catch (InvalidPasswordException e) {
                throw e;
            }
        }
    }

    /**
     * Valida o login e a senha para autenticação do usuário.
     * Este método verifica se o login e a senha não são nulos ou vazios
     * e se o login existe no sistema. Se alguma dessas condições falhar,
     * uma exceção InvalidLoginOrPasswordException será lançada.
     *
     * @param login o nome de login a ser verificado, não pode ser nulo ou vazio
     * @param password a senha a ser verificada, não pode ser nula ou vazia
     * @throws InvalidLoginOrPasswordException se o login ou a senha forem inválidos
     *         ou se o login não existir no sistema
     */
    public void loginCheck(String login, String password) throws InvalidLoginOrPasswordException {
        if (login == null || password == null || login.isEmpty() || password.isEmpty() || !this.loginPreExist(login)) {
            throw new InvalidLoginOrPasswordException();
        }
    }

    /**
     * Converte o objeto fornecido para o tipo UserManager e atualiza o campo userList
     * do objeto atual com a lista de usuários do objeto fornecido.
     *
     * @param obj o objeto a ser convertido para UserManager.
     */
    @Override
    protected void castObject(Object obj) {
        UserManager users = (UserManager) obj;
        if (users != null) {
            this.userList = users.userList;
        }
    }
}
