package br.ufal.ic.p2.jackut.code.community;

import br.ufal.ic.p2.jackut.code.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Comunidade implementada com os atributos owner, communityName, description e memberList.
 * Ela visa fornecer o gerenciamento da pr�pria comunidade.
 */
public class Community implements Serializable {
    private User owner;
    private String communityName;
    private String description;
    private List<User> memberList;

    public Community(User creator, String communityName, String description) {
        this.owner = creator;
        this.communityName = communityName;
        this.description = description;
        this.memberList = new ArrayList<>();
        this.addMember(creator);
        creator.addCommunity(this.communityName);
    }

    /**
     * Retorna o usu�rio dono da comunidade.
     *
     * @return o objeto User referente ao dono da comunidade.
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Retorna o nome da comunidade.
     *
     * @return uma String com o nome da comunidade.
     */
    public String getCommunityName() {
        return this.communityName;
    }

    /**
     * Retorna a descri��o da comunidade.
     *
     * @return uma String com a descri��o da comunidade.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retorna os membros da comunidade como uma Lista de usu�rios (User).
     *
     * @return uma lista de usu�rios.
     */
    public List<User> getMemberList() { return this.memberList; }

    /**
     * Retorna uma string que representa uma lista com os nomes dos usu�rios membros da comunidade.
     *
     * @return uma string com o nome dos membros da comunidade.
     */
    public String getMemberListAsString() {
        List<String> membersName = new ArrayList<>();
        for (User user : memberList) { membersName.add(user.getLogin()); }
        return "{" + String.join(",", membersName) + "}";
    }

    /**
     * Adiciona um membro a comunidade.
     *
     * @param user objeto User a ser adicionado.
     */
    public void addMember(User user) {
        this.memberList.add(user);
    }


    /**
     * Verifica se um usu�rio j� � membro da comunidade.
     *
     * @param user o usu�rio a ser verificado
     * @return true se o usu�rio j� for membro, false caso contr�rio
     */
    public boolean userAlredyJoined(User user) {
        return this.memberList.contains(user);
    }

    /**
     * Remove um usu�rio da lista de membros da comunidade e tamb�m
     * remove a comunidade da lista de comunidades do usu�rio.
     *
     * @param user o usu�rio a ser removido da comunidade
     */
    public void removeMember(User user) {
        this.memberList.remove(user);
        user.removeComunity(this.communityName);
    }

    /**
     * Remove todos os membros da comunidade, exceto o propriet�rio.
     * Tamb�m remove a comunidade da lista de comunidades de cada membro.
     */
    public void removeAllMembersExceptOwner() {
        for (User user : this.memberList) {
            if (user != this.owner) {
                user.removeComunity(this.communityName);
            }
        }
        this.memberList.clear();
    }

}
