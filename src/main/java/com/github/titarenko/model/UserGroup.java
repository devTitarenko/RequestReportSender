package com.github.titarenko.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "user_group")
public class UserGroup extends BaseObject {

    @NotNull
    @Column(name = "group_name")
    private String groupName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userGroup", cascade = CascadeType.ALL)
    private List<User> userList;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + getId() +
                "groupName='" + groupName + '\'' +
                '}';
    }
}
