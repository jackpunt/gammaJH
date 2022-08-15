package com.thegraid.gamma.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.thegraid.gamma.domain.Player} entity.
 */
@Schema(description = "a virtual player (the horse in a horse-race)")
public class PlayerDTO implements Serializable {

    private Long id;

    private Integer version;

    @Size(max = 45)
    private String name;

    private Integer rank;

    private Integer score;

    private Instant scoreTime;

    private Instant rankTime;

    @Size(max = 45)
    private String displayClient;

    private GameClassDTO gameClass;

    private AssetDTO mainJar;

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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Instant getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(Instant scoreTime) {
        this.scoreTime = scoreTime;
    }

    public Instant getRankTime() {
        return rankTime;
    }

    public void setRankTime(Instant rankTime) {
        this.rankTime = rankTime;
    }

    public String getDisplayClient() {
        return displayClient;
    }

    public void setDisplayClient(String displayClient) {
        this.displayClient = displayClient;
    }

    public GameClassDTO getGameClass() {
        return gameClass;
    }

    public void setGameClass(GameClassDTO gameClass) {
        this.gameClass = gameClass;
    }

    public AssetDTO getMainJar() {
        return mainJar;
    }

    public void setMainJar(AssetDTO mainJar) {
        this.mainJar = mainJar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerDTO)) {
            return false;
        }

        PlayerDTO playerDTO = (PlayerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, playerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlayerDTO{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", name='" + getName() + "'" +
            ", rank=" + getRank() +
            ", score=" + getScore() +
            ", scoreTime='" + getScoreTime() + "'" +
            ", rankTime='" + getRankTime() + "'" +
            ", displayClient='" + getDisplayClient() + "'" +
            ", gameClass=" + getGameClass() +
            ", mainJar=" + getMainJar() +
            "}";
    }
}
