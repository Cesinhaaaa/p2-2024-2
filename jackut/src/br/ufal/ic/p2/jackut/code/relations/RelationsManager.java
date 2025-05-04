package br.ufal.ic.p2.jackut.code.relations;

import br.ufal.ic.p2.jackut.code.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia os diferentes tipos de relacionamentos entre usuários,
 * incluindo amigos, fãs, paqueras e inimigos.
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
     * Adiciona um usuário à lista de amigos e remove de solicitações recebidas.
     *
     * @param user o usuário a ser adicionado como amigo
     */
    public void addFriend(User user) {
        this.friends.add(user);
        this.friendRequests.remove(user);
    }

    /**
     * Verifica se um usuário é amigo.
     *
     * @param user o usuário a ser verificado
     * @return true se for amigo, false caso contrário
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
     * Adiciona uma solicitação de amizade recebida.
     *
     * @param user o usuário que enviou a solicitação
     */
    public void receiveRequest(User user) {
        this.friendRequests.add(user);
    }

    /**
     * Verifica se uma solicitação de amizade já foi recebida de determinado usuário.
     *
     * @param user o usuário a ser verificado
     * @return true se a solicitação já foi recebida, false caso contrário
     */
    public boolean alredyReceivedRequest(User user) {
        return this.friendRequests.contains(user);
    }

    /**
     * Adiciona um fã à lista de fãs.
     *
     * @param user o usuário que virou fã
     */
    public void addFa(User user) {
        this.fas.add(user);
    }

    /**
     * Verifica se um determinado usuário é fã (idolatrado).
     *
     * @param fa o possível fã
     * @return true se for fã, false caso contrário
     */
    public boolean isIdol(User fa) {
        return this.fas.contains(fa);
    }

    /**
     * Retorna os logins dos fãs em formato de string.
     *
     * @return string formatada com os logins dos fãs
     */
    public String getFasAsString() {
        return this.asString(this.fas);
    }

    /**
     * Adiciona um usuário à lista de paqueras.
     *
     * @param user o usuário adicionado como paquera
     */
    public void addPaquera(User user) {
        this.paqueras.add(user);
    }

    /**
     * Verifica se um usuário já foi adicionado como paquera.
     *
     * @param paquerador o usuário a ser verificado
     * @return true se já for paquera, false caso contrário
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
     * Adiciona um inimigo à lista de inimigos.
     *
     * @param enemy o usuário a ser adicionado como inimigo
     */
    public void addEnemy(User enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Verifica se um usuário é inimigo.
     *
     * @param enemy o usuário a ser verificado
     * @return true se for inimigo, false caso contrário
     */
    public boolean isEnemy(User enemy) {
        return this.enemies.contains(enemy);
    }

    /**
     * Converte uma lista de usuários em uma string contendo seus logins.
     *
     * @param users a lista de usuários a ser convertida
     * @return string formatada com os logins dos usuários
     */
    private String asString(List<User> users) {
        List<String> logins = new ArrayList<>();
        for (User user : users) {
            logins.add(user.getLogin());
        }
        return "{" + String.join(",", logins) + "}";
    }

    /**
     * Remove todas as referências a um usuário de todas as listas de relacionamento.
     *
     * @param user o usuário cujas referências serão removidas
     */
    public void removeReferencesFromUser(User user) {
        this.friends.remove(user);
        this.friendRequests.remove(user);
        this.fas.remove(user);
        this.paqueras.remove(user);
        this.enemies.remove(user);
    }
}
