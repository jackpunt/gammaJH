package com.thegraid.gamma.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Which Game engine/jar to play.
 */
@Entity
@Table(name = "game_class")
public class GameClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Integer version;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @Size(max = 45)
    @Column(name = "revision", length = 45)
    private String revision;

    @Column(name = "launcher_path")
    private String launcherPath;

    @Column(name = "game_path")
    private String gamePath;

    @Column(name = "docs_path")
    private String docsPath;

    @Column(name = "props_names")
    private String propsNames;

    @Column(name = "updated")
    private Instant updated;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GameClass id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public GameClass version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return this.name;
    }

    public GameClass name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRevision() {
        return this.revision;
    }

    public GameClass revision(String revision) {
        this.setRevision(revision);
        return this;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getLauncherPath() {
        return this.launcherPath;
    }

    public GameClass launcherPath(String launcherPath) {
        this.setLauncherPath(launcherPath);
        return this;
    }

    public void setLauncherPath(String launcherPath) {
        this.launcherPath = launcherPath;
    }

    public String getGamePath() {
        return this.gamePath;
    }

    public GameClass gamePath(String gamePath) {
        this.setGamePath(gamePath);
        return this;
    }

    public void setGamePath(String gamePath) {
        this.gamePath = gamePath;
    }

    public String getDocsPath() {
        return this.docsPath;
    }

    public GameClass docsPath(String docsPath) {
        this.setDocsPath(docsPath);
        return this;
    }

    public void setDocsPath(String docsPath) {
        this.docsPath = docsPath;
    }

    public String getPropsNames() {
        return this.propsNames;
    }

    public GameClass propsNames(String propsNames) {
        this.setPropsNames(propsNames);
        return this;
    }

    public void setPropsNames(String propsNames) {
        this.propsNames = propsNames;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public GameClass updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameClass)) {
            return false;
        }
        return id != null && id.equals(((GameClass) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameClass{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", name='" + getName() + "'" +
            ", revision='" + getRevision() + "'" +
            ", launcherPath='" + getLauncherPath() + "'" +
            ", gamePath='" + getGamePath() + "'" +
            ", docsPath='" + getDocsPath() + "'" +
            ", propsNames='" + getPropsNames() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
