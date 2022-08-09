package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Player.
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
    private Long version;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameInsts", "players", "mmemberGameProps" }, allowSetters = true)
    private GameClass gameClass;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mmember", "players" }, allowSetters = true)
    private Asset asset;

    @OneToMany(mappedBy = "playerB")
    @JsonIgnoreProperties(value = { "propsId", "gameClass", "playerB", "gamePlayers" }, allowSetters = true)
    private Set<GameInst> idBS = new HashSet<>();

    @OneToMany(mappedBy = "player")
    @JsonIgnoreProperties(value = { "gameInst", "player" }, allowSetters = true)
    private Set<GamePlayer> gamePlayers = new HashSet<>();

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

    public Long getVersion() {
        return this.version;
    }

    public Player version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public Set<GameInst> getIdBS() {
        return this.idBS;
    }

    public void setIdBS(Set<GameInst> gameInsts) {
        if (this.idBS != null) {
            this.idBS.forEach(i -> i.setPlayerB(null));
        }
        if (gameInsts != null) {
            gameInsts.forEach(i -> i.setPlayerB(this));
        }
        this.idBS = gameInsts;
    }

    public Player idBS(Set<GameInst> gameInsts) {
        this.setIdBS(gameInsts);
        return this;
    }

    public Player addIdB(GameInst gameInst) {
        this.idBS.add(gameInst);
        gameInst.setPlayerB(this);
        return this;
    }

    public Player removeIdB(GameInst gameInst) {
        this.idBS.remove(gameInst);
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
            "}";
    }
}
