package br.ufal.ic.p2.jackut.code.community;

import br.ufal.ic.p2.jackut.code.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Comunidade implementada com os atributos owner, communityName, description e memberList.
 * Ela visa fornecer o gerenciamento da própria comunidade.
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
     * Retorna o usuário dono da comunidade.
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
     * Retorna a descrição da comunidade.
     *
     * @return uma String com a descrição da comunidade.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retorna os membros da comunidade como uma Lista de usuários (User).
     *
     * @return uma lista de usuários.
     */
    public List<User> getMemberList() { return this.memberList; }

    /**
     * Retorna uma string que representa uma lista com os nomes dos usuários membros da comunidade.
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
     * Verifica se um usuário já é membro da comunidade.
     *
     * @param user o usuário a ser verificado
     * @return true se o usuário já for membro, false caso contrário
     */
    public boolean userAlredyJoined(User user) {
        return this.memberList.contains(user);
    }

    /**
     * Remove um usuário da lista de membros da comunidade e também
     * remove a comunidade da lista de comunidades do usuário.
     *
     * @param user o usuário a ser removido da comunidade
     */
    public void removeMember(User user) {
        this.memberList.remove(user);
        user.removeComunity(this.communityName);
    }

    /**
     * Remove todos os membros da comunidade, exceto o proprietário.
     * Também remove a comunidade da lista de comunidades de cada membro.
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
