package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.GroupRepository;
import com.percipient.matrix.display.GroupView;
import com.percipient.matrix.security.Group;
import com.percipient.matrix.security.GroupAuthority;

public interface GroupService {

    public List<GroupView> getGroups();

    public GroupView getGroup(Integer groupId);

    public void saveGroup(GroupView groupView);

    public void deleteGroup(GroupView groupView);

}

@Service
class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    @Transactional
    public List<GroupView> getGroups() {
        List<Group> groups = groupRepository.getGroups();

        List<GroupView> groupViews = new ArrayList<GroupView>();
        for (Group group : groups) {
            GroupView groupView = getGroupViewFromGroup(group);
            groupViews.add(groupView);
        }
        return groupViews;
    }

    @Override
    @Transactional
    public GroupView getGroup(Integer groupId) {
        Group group = groupRepository.getGroup(groupId);
        GroupView groupView = getGroupViewFromGroup(group);
        return groupView;
    }

    @Override
    @Transactional
    public void saveGroup(GroupView groupView) {
        Group group = getGroupFromGroupView(groupView);
        groupRepository.saveGroup(group);
    }

    @Override
    @Transactional
    public void deleteGroup(GroupView groupView) {
        Group group = getGroupFromGroupView(groupView);
        groupRepository.deleteGroup(group);
    }

    private GroupView getGroupViewFromGroup(Group group) {
        GroupAuthority groupAuthority = group.getGroupAuthority();

        GroupView groupView = new GroupView();
        groupView.setId(group.getId());
        groupView.setName(group.getName());
        groupView.setAuthority(groupAuthority.getAuthority());
        return groupView;
    }

    private Group getGroupFromGroupView(GroupView groupView) {
        Group group = new Group();
        group.setId(groupView.getId());
        group.setName(groupView.getName());
        GroupAuthority groupAuthority = getGroupAuthority(groupView.getId(),
                groupView.getAuthority());
        groupAuthority.setGroup(group);
        group.setGroupAuthority(groupAuthority);
        return group;
    }

    private GroupAuthority getGroupAuthority(Integer groupId, String authority) {
        GroupAuthority groupAuthority = groupRepository
                .getGroupAuthority(groupId);
        groupAuthority.setGroupId(groupId);
        groupAuthority.setAuthority(authority);
        return groupAuthority;
    }
}
