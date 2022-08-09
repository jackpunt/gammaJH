package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Mmember.
 */
@Entity
@Table(name = "mmember")
public class Mmember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mmembers", "groupAuthorities" }, allowSetters = true)
    private RoleGroup roleGroup;

    @OneToMany(mappedBy = "mmember")
    @JsonIgnoreProperties(value = { "mmember", "players" }, allowSetters = true)
    private Set<Asset> assets = new HashSet<>();

    @OneToMany(mappedBy = "mmember")
    @JsonIgnoreProperties(value = { "mmember", "gameClass" }, allowSetters = true)
    private Set<MmemberGameProps> mmemberGameProps = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mmember id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return this.version;
    }

    public Mmember version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public RoleGroup getRoleGroup() {
        return this.roleGroup;
    }

    public void setRoleGroup(RoleGroup roleGroup) {
        this.roleGroup = roleGroup;
    }

    public Mmember roleGroup(RoleGroup roleGroup) {
        this.setRoleGroup(roleGroup);
        return this;
    }

    public Set<Asset> getAssets() {
        return this.assets;
    }

    public void setAssets(Set<Asset> assets) {
        if (this.assets != null) {
            this.assets.forEach(i -> i.setMmember(null));
        }
        if (assets != null) {
            assets.forEach(i -> i.setMmember(this));
        }
        this.assets = assets;
    }

    public Mmember assets(Set<Asset> assets) {
        this.setAssets(assets);
        return this;
    }

    public Mmember addAsset(Asset asset) {
        this.assets.add(asset);
        asset.setMmember(this);
        return this;
    }

    public Mmember removeAsset(Asset asset) {
        this.assets.remove(asset);
        asset.setMmember(null);
        return this;
    }

    public Set<MmemberGameProps> getMmemberGameProps() {
        return this.mmemberGameProps;
    }

    public void setMmemberGameProps(Set<MmemberGameProps> mmemberGameProps) {
        if (this.mmemberGameProps != null) {
            this.mmemberGameProps.forEach(i -> i.setMmember(null));
        }
        if (mmemberGameProps != null) {
            mmemberGameProps.forEach(i -> i.setMmember(this));
        }
        this.mmemberGameProps = mmemberGameProps;
    }

    public Mmember mmemberGameProps(Set<MmemberGameProps> mmemberGameProps) {
        this.setMmemberGameProps(mmemberGameProps);
        return this;
    }

    public Mmember addMmemberGameProps(MmemberGameProps mmemberGameProps) {
        this.mmemberGameProps.add(mmemberGameProps);
        mmemberGameProps.setMmember(this);
        return this;
    }

    public Mmember removeMmemberGameProps(MmemberGameProps mmemberGameProps) {
        this.mmemberGameProps.remove(mmemberGameProps);
        mmemberGameProps.setMmember(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mmember)) {
            return false;
        }
        return id != null && id.equals(((Mmember) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mmember{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            "}";
    }
}
