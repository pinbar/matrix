package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.GroupRepository;
import com.percipient.matrix.security.Group;
import com.percipient.matrix.security.GroupAuthority;
import com.percipient.matrix.view.GroupView;

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
        Group group = null;
        if (groupView.getId() != null) {
            group = groupRepository.getGroup(groupView.getId());
        }
        if (group == null) {
            group = new Group();
            GroupAuthority grpAuthority = new GroupAuthority();
            group.setGroupAuthority(grpAuthority);
            grpAuthority.setGroup(group);
        }
        group.setName(groupView.getName());
        group.getGroupAuthority().setAuthority(groupView.getAuthority());
        return group;
    }
}
