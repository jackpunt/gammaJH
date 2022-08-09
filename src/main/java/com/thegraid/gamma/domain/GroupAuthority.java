package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A GroupAuthority.
 */
@Entity
@Table(name = "group_authority")
public class GroupAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "authority")
    private String authority;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mmembers", "groupAuthorities" }, allowSetters = true)
    private RoleGroup roleGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GroupAuthority id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return this.version;
    }

    public GroupAuthority version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAuthority() {
        return this.authority;
    }

    public GroupAuthority authority(String authority) {
        this.setAuthority(authority);
        return this;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public RoleGroup getRoleGroup() {
        return this.roleGroup;
    }

    public void setRoleGroup(RoleGroup roleGroup) {
        this.roleGroup = roleGroup;
    }

    public GroupAuthority roleGroup(RoleGroup roleGroup) {
        this.setRoleGroup(roleGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupAuthority)) {
            return false;
        }
        return id != null && id.equals(((GroupAuthority) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupAuthority{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", authority='" + getAuthority() + "'" +
            "}";
    }
}
