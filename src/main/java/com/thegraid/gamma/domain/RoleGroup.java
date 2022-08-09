package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A RoleGroup.
 */
@Entity
@Table(name = "role_group")
public class RoleGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "group_name")
    private String groupName;

    @OneToMany(mappedBy = "roleGroup")
    @JsonIgnoreProperties(value = { "roleGroup", "assets", "mmemberGameProps" }, allowSetters = true)
    private Set<Mmember> mmembers = new HashSet<>();

    @OneToMany(mappedBy = "roleGroup")
    @JsonIgnoreProperties(value = { "roleGroup" }, allowSetters = true)
    private Set<GroupAuthority> groupAuthorities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RoleGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return this.version;
    }

    public RoleGroup version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public RoleGroup groupName(String groupName) {
        this.setGroupName(groupName);
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<Mmember> getMmembers() {
        return this.mmembers;
    }

    public void setMmembers(Set<Mmember> mmembers) {
        if (this.mmembers != null) {
            this.mmembers.forEach(i -> i.setRoleGroup(null));
        }
        if (mmembers != null) {
            mmembers.forEach(i -> i.setRoleGroup(this));
        }
        this.mmembers = mmembers;
    }

    public RoleGroup mmembers(Set<Mmember> mmembers) {
        this.setMmembers(mmembers);
        return this;
    }

    public RoleGroup addMmember(Mmember mmember) {
        this.mmembers.add(mmember);
        mmember.setRoleGroup(this);
        return this;
    }

    public RoleGroup removeMmember(Mmember mmember) {
        this.mmembers.remove(mmember);
        mmember.setRoleGroup(null);
        return this;
    }

    public Set<GroupAuthority> getGroupAuthorities() {
        return this.groupAuthorities;
    }

    public void setGroupAuthorities(Set<GroupAuthority> groupAuthorities) {
        if (this.groupAuthorities != null) {
            this.groupAuthorities.forEach(i -> i.setRoleGroup(null));
        }
        if (groupAuthorities != null) {
            groupAuthorities.forEach(i -> i.setRoleGroup(this));
        }
        this.groupAuthorities = groupAuthorities;
    }

    public RoleGroup groupAuthorities(Set<GroupAuthority> groupAuthorities) {
        this.setGroupAuthorities(groupAuthorities);
        return this;
    }

    public RoleGroup addGroupAuthority(GroupAuthority groupAuthority) {
        this.groupAuthorities.add(groupAuthority);
        groupAuthority.setRoleGroup(this);
        return this;
    }

    public RoleGroup removeGroupAuthority(GroupAuthority groupAuthority) {
        this.groupAuthorities.remove(groupAuthority);
        groupAuthority.setRoleGroup(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleGroup)) {
            return false;
        }
        return id != null && id.equals(((RoleGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleGroup{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", groupName='" + getGroupName() + "'" +
            "}";
    }
}
