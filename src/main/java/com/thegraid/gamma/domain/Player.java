package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * a virtual player (the horse in a horse-race)
 */
@Entity
@Table(name = "player")
public class Player implements Serializable {

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

    @Column(name = "jhi_rank")
    private Integer rank;

    @Column(name = "score")
    private Integer score;

    @Column(name = "score_time")
    private Instant scoreTime;

    @Column(name = "rank_time")
    private Instant rankTime;

    @Size(max = 45)
    @Column(name = "display_client", length = 45)
    private String displayClient;

    @OneToMany(mappedBy = "playerA")
    @JsonIgnoreProperties(value = { "props", "gamePlayers", "gameClass", "playerA", "playerB" }, allowSetters = true)
    private Set<GameInst> idas = new HashSet<>();

    @OneToMany(mappedBy = "playerB")
    @JsonIgnoreProperties(value = { "props", "gamePlayers", "gameClass", "playerA", "playerB" }, allowSetters = true)
    private Set<GameInst> idbs = new HashSet<>();

    @OneToMany(mappedBy = "player")
    @JsonIgnoreProperties(value = { "gameInst", "player" }, allowSetters = true)
    private Set<GamePlayer> gamePlayers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameInsts", "memberGameProps", "players" }, allowSetters = true)
    private GameClass gameClass;

    @ManyToOne
    @JsonIgnoreProperties(value = { "players", "user" }, allowSetters = true)
    private Asset asset;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Player id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public Player version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return this.name;
    }

    public Player name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return this.rank;
    }

    public Player rank(Integer rank) {
        this.setRank(rank);
        return this;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getScore() {
        return this.score;
    }

    public Player score(Integer score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Instant getScoreTime() {
        return this.scoreTime;
    }

    public Player scoreTime(Instant scoreTime) {
        this.setScoreTime(scoreTime);
        return this;
    }

    public void setScoreTime(Instant scoreTime) {
        this.scoreTime = scoreTime;
    }

    public Instant getRankTime() {
        return this.rankTime;
    }

    public Player rankTime(Instant rankTime) {
        this.setRankTime(rankTime);
        return this;
    }

    public void setRankTime(Instant rankTime) {
        this.rankTime = rankTime;
    }

    public String getDisplayClient() {
        return this.displayClient;
    }

    public Player displayClient(String displayClient) {
        this.setDisplayClient(displayClient);
        return this;
    }

    public void setDisplayClient(String displayClient) {
        this.displayClient = displayClient;
    }

    public Set<GameInst> getIdas() {
        return this.idas;
    }

    public void setIdas(Set<GameInst> gameInsts) {
        if (this.idas != null) {
            this.idas.forEach(i -> i.setPlayerA(null));
        }
        if (gameInsts != null) {
            gameInsts.forEach(i -> i.setPlayerA(this));
        }
        this.idas = gameInsts;
    }

    public Player idas(Set<GameInst> gameInsts) {
        this.setIdas(gameInsts);
        return this;
    }

    public Player addIda(GameInst gameInst) {
        this.idas.add(gameInst);
        gameInst.setPlayerA(this);
        return this;
    }

    public Player removeIda(GameInst gameInst) {
        this.idas.remove(gameInst);
        gameInst.setPlayerA(null);
        return this;
    }

    public Set<GameInst> getIdbs() {
        return this.idbs;
    }

    public void setIdbs(Set<GameInst> gameInsts) {
        if (this.idbs != null) {
            this.idbs.forEach(i -> i.setPlayerB(null));
        }
        if (gameInsts != null) {
            gameInsts.forEach(i -> i.setPlayerB(this));
        }
        this.idbs = gameInsts;
    }

    public Player idbs(Set<GameInst> gameInsts) {
        this.setIdbs(gameInsts);
        return this;
    }

    public Player addIdb(GameInst gameInst) {
        this.idbs.add(gameInst);
        gameInst.setPlayerB(this);
        return this;
    }

    public Player removeIdb(GameInst gameInst) {
        this.idbs.remove(gameInst);
        gameInst.setPlayerB(null);
        return this;
    }

    public Set<GamePlayer> getGamePlayers() {
        return this.gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        if (this.gamePlayers != null) {
            this.gamePlayers.forEach(i -> i.setPlayer(null));
        }
        if (gamePlayers != null) {
            gamePlayers.forEach(i -> i.setPlayer(this));
        }
        this.gamePlayers = gamePlayers;
    }

    public Player gamePlayers(Set<GamePlayer> gamePlayers) {
        this.setGamePlayers(gamePlayers);
        return this;
    }

    public Player addGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayers.add(gamePlayer);
        gamePlayer.setPlayer(this);
        return this;
    }

    public Player removeGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayers.remove(gamePlayer);
        gamePlayer.setPlayer(null);
        return this;
    }

    public GameClass getGameClass() {
        return this.gameClass;
    }

    public void setGameClass(GameClass gameClass) {
        this.gameClass = gameClass;
    }

    public Player gameClass(GameClass gameClass) {
        this.setGameClass(gameClass);
        return this;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Player asset(Asset asset) {
        this.setAsset(asset);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        return id != null && id.equals(((Player) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", name='" + getName() + "'" +
            ", rank=" + getRank() +
            ", score=" + getScore() +
            ", scoreTime='" + getScoreTime() + "'" +
            ", rankTime='" + getRankTime() + "'" +
            ", displayClient='" + getDisplayClient() + "'" +
            "}";
    }
}
