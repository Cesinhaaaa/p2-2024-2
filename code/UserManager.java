package br.ufal.ic.p2.jackut.code;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A classe UserManager � respons�vel por gerenciar os dados dos usu�rios dentro de uma aplica��o.
 * Ela estende a classe SerializableData, que facilita a serializa��o e desserializa��o
 * dos dados para um arquivo. A classe mant�m uma lista de usu�rios e fornece m�todos
 * para opera��es de gerenciamento de usu�rios, como adicionar novos usu�rios, limpar a lista,
 * verificar logins existentes e recuperar detalhes de usu�rios.
 */
public class UserManager extends SerializableData {
    private List<User> userList;

    /**
     * Constr�i uma nova inst�ncia de UserManager, inicializando a lista de usu�rios para gerenciar os usu�rios
     * e configurando o caminho do arquivo para persist�ncia de dados.
     *
     * @param filePath o caminho do arquivo onde os dados dos usu�rios ser�o serializados e armazenados
     */
    public UserManager(String filePath) {
        super(filePath);
        this.userList = new ArrayList<User>();
    }

    /**
     * Limpa a lista de usu�rios gerenciada pela inst�ncia de UserManager.
     *
     * Este m�todo remove todas as entradas da lista interna de usu�rios, efetivamente
     * resetando o sistema de gerenciamento de usu�rios para um estado sem usu�rios.
     * Essa a��o � irrevers�vel e deve ser usada com cautela, pois todos os dados
     * dos usu�rios gerenciados por esta inst�ncia ser�o perdidos.
     */
    public void clearUsers() {
        this.userList.clear();
    }

    /**
     * Recupera um usu�rio pelo seu login.
     *
     * Este m�todo busca um usu�rio na lista de usu�rios que corresponda ao login especificado.
     * Se um usu�rio correspondente for encontrado, ele � retornado. Caso contr�rio,
     * uma exce��o UserNotRegisteredException � lan�ada.
     *
     * @param login o login do usu�rio a ser recuperado
     * @return o objeto User associado ao login especificado
     * @throws UserNotRegisteredException se nenhum usu�rio com o login especificado for encontrado
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
     * Verifica se um determinado login j� est� em uso por algum usu�rio na lista de usu�rios.
     *
     * @param login o login a ser verificado na lista de usu�rios
     * @return true se o login j� existir, false caso contr�rio
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
     * Cria um novo usu�rio com o login, senha e nome de usu�rio especificados.
     * Se o login j� existir, uma exce��o ser� lan�ada.
     *
     * @param login o nome de login para o novo usu�rio
     * @param password a senha para o novo usu�rio
     * @param userName o nome do usu�rio
     * @throws LoginAlredyUsedException se o login especificado j� estiver em uso
     * @throws InvalidLoginException se o login fornecido for inv�lido
     * @throws InvalidPasswordException se a senha fornecida for inv�lida
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
     * Valida o login e a senha para autentica��o do usu�rio.
     * Este m�todo verifica se o login e a senha n�o s�o nulos ou vazios
     * e se o login existe no sistema. Se alguma dessas condi��es falhar,
     * uma exce��o InvalidLoginOrPasswordException ser� lan�ada.
     *
     * @param login o nome de login a ser verificado, n�o pode ser nulo ou vazio
     * @param password a senha a ser verificada, n�o pode ser nula ou vazia
     * @throws InvalidLoginOrPasswordException se o login ou a senha forem inv�lidos
     *         ou se o login n�o existir no sistema
     */
    public void loginCheck(String login, String password) throws InvalidLoginOrPasswordException {
        if (login == null || password == null || login.isEmpty() || password.isEmpty() || !this.loginPreExist(login)) {
            throw new InvalidLoginOrPasswordException();
        }
    }

    /**
     * Converte o objeto fornecido para o tipo UserManager e atualiza o campo userList
     * do objeto atual com a lista de usu�rios do objeto fornecido.
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
