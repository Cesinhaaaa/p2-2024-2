package br.ufal.ic.p2.jackut.code.relations;

import br.ufal.ic.p2.jackut.code.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia os diferentes tipos de relacionamentos entre usu�rios,
 * incluindo amigos, f�s, paqueras e inimigos.
 */
public class RelationsManager implements Serializable {
    private List<User> friends;
    private List<User> friendRequests;
    private List<User> fas;
    private List<User> paqueras;
    private List<User> enemies;

    /**
     * Construtor que inicializa todas as listas de relacionamento como vazias.
     */
    public RelationsManager() {
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.fas = new ArrayList<>();
        this.paqueras = new ArrayList<>();
        this.enemies = new ArrayList<>();
    }

    /**
     * Adiciona um usu�rio � lista de amigos e remove de solicita��es recebidas.
     *
     * @param user o usu�rio a ser adicionado como amigo
     */
    public void addFriend(User user) {
        this.friends.add(user);
        this.friendRequests.remove(user);
    }

    /**
     * Verifica se um usu�rio � amigo.
     *
     * @param user o usu�rio a ser verificado
     * @return true se for amigo, false caso contr�rio
     */
    public boolean isFriend(User user) {
        return this.friends.contains(user);
    }

    /**
     * Retorna os logins dos amigos em formato de string.
     *
     * @return string formatada com os logins dos amigos
     */
    public String getFriendsAsString() {
        return asString(this.friends);
    }

    /**
     * Adiciona uma solicita��o de amizade recebida.
     *
     * @param user o usu�rio que enviou a solicita��o
     */
    public void receiveRequest(User user) {
        this.friendRequests.add(user);
    }

    /**
     * Verifica se uma solicita��o de amizade j� foi recebida de determinado usu�rio.
     *
     * @param user o usu�rio a ser verificado
     * @return true se a solicita��o j� foi recebida, false caso contr�rio
     */
    public boolean alredyReceivedRequest(User user) {
        return this.friendRequests.contains(user);
    }

    /**
     * Adiciona um f� � lista de f�s.
     *
     * @param user o usu�rio que virou f�
     */
    public void addFa(User user) {
        this.fas.add(user);
    }

    /**
     * Verifica se um determinado usu�rio � f� (idolatrado).
     *
     * @param fa o poss�vel f�
     * @return true se for f�, false caso contr�rio
     */
    public boolean isIdol(User fa) {
        return this.fas.contains(fa);
    }

    /**
     * Retorna os logins dos f�s em formato de string.
     *
     * @return string formatada com os logins dos f�s
     */
    public String getFasAsString() {
        return this.asString(this.fas);
    }

    /**
     * Adiciona um usu�rio � lista de paqueras.
     *
     * @param user o usu�rio adicionado como paquera
     */
    public void addPaquera(User user) {
        this.paqueras.add(user);
    }

    /**
     * Verifica se um usu�rio j� foi adicionado como paquera.
     *
     * @param paquerador o usu�rio a ser verificado
     * @return true se j� for paquera, false caso contr�rio
     */
    public boolean paqueraTheUser(User paquerador) {
        return this.paqueras.contains(paquerador);
    }

    /**
     * Retorna os logins das paqueras em formato de string.
     *
     * @return string formatada com os logins das paqueras
     */
    public String getPaquerasAsString() {
        return asString(this.paqueras);
    }

    /**
     * Adiciona um inimigo � lista de inimigos.
     *
     * @param enemy o usu�rio a ser adicionado como inimigo
     */
    public void addEnemy(User enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Verifica se um usu�rio � inimigo.
     *
     * @param enemy o usu�rio a ser verificado
     * @return true se for inimigo, false caso contr�rio
     */
    public boolean isEnemy(User enemy) {
        return this.enemies.contains(enemy);
    }

    /**
     * Converte uma lista de usu�rios em uma string contendo seus logins.
     *
     * @param users a lista de usu�rios a ser convertida
     * @return string formatada com os logins dos usu�rios
     */
    private String asString(List<User> users) {
        List<String> logins = new ArrayList<>();
        for (User user : users) {
            logins.add(user.getLogin());
        }
        return "{" + String.join(",", logins) + "}";
    }

    /**
     * Remove todas as refer�ncias a um usu�rio de todas as listas de relacionamento.
     *
     * @param user o usu�rio cujas refer�ncias ser�o removidas
     */
    public void removeReferencesFromUser(User user) {
        this.friends.remove(user);
        this.friendRequests.remove(user);
        this.fas.remove(user);
        this.paqueras.remove(user);
        this.enemies.remove(user);
    }
}
