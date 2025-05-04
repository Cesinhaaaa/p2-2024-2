package br.ufal.ic.p2.jackut.code.community;

import br.ufal.ic.p2.jackut.code.persistence.SerializableData;
import br.ufal.ic.p2.jackut.code.user.User;
import br.ufal.ic.p2.jackut.exceptions.community.CommunityAlredyExistException;
import br.ufal.ic.p2.jackut.exceptions.community.CommunityNotExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CommunityManager extends SerializableData {
    Map<String, Community> communityMap;

    public CommunityManager(String filePath) {
        super(filePath);
        this.communityMap = new TreeMap<>();
    }

    public void createCommunity(User creator, String communityName, String description) throws CommunityAlredyExistException {
        if (this.communityMap.containsKey(communityName)) {
            throw new CommunityAlredyExistException();
        }

        this.communityMap.put(communityName, new Community(creator, communityName, description));
    }

    public Community getCommunityByName(String communityName) throws CommunityNotExistException {
        if (this.communityMap.containsKey(communityName)) {
            return this.communityMap.get(communityName);
        }
        throw new CommunityNotExistException();
    }

    public void removeUserFromCommunitys(User user) {
        List<String> communitysThatUserIsOwner = new ArrayList<>();
        for (String communityName : user.getCommunitys()) {
            Community community = this.getCommunityByName(communityName);
            if (user == community.getOwner()) {
                community.removeAllMembersExceptOwner();
                communitysThatUserIsOwner.add(community.getCommunityName());
            } else {
                community.removeMember(user);
            }
        }
        for (String communityName : communitysThatUserIsOwner) {
            this.communityMap.remove(communityName);
        }
    }

    public void clearCommunitys() {
        this.communityMap.clear();
    }

    @Override
    protected void castObject(Object object) {
        CommunityManager communityManager = (CommunityManager) object;
        this.communityMap = communityManager.communityMap;
    }
}
