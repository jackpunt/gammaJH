package com.thegraid.gamma.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.thegraid.gamma.domain.GameClass} entity.
 */
@Schema(description = "Which Game engine/jar to play.")
public class GameClassDTO implements Serializable {

    private Long id;

    private Integer version;

    @Size(max = 45)
    private String name;

    @Size(max = 45)
    private String revision;

    private String launcherPath;

    private String gamePath;

    private String docsPath;

    private String propsNames;

    private Instant updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getLauncherPath() {
        return launcherPath;
    }

    public void setLauncherPath(String launcherPath) {
        this.launcherPath = launcherPath;
    }

    public String getGamePath() {
        return gamePath;
    }

    public void setGamePath(String gamePath) {
        this.gamePath = gamePath;
    }

    public String getDocsPath() {
        return docsPath;
    }

    public void setDocsPath(String docsPath) {
        this.docsPath = docsPath;
    }

    public String getPropsNames() {
        return propsNames;
    }

    public void setPropsNames(String propsNames) {
        this.propsNames = propsNames;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameClassDTO)) {
            return false;
        }

        GameClassDTO gameClassDTO = (GameClassDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gameClassDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameClassDTO{" +
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
