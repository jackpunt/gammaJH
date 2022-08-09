package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A GamePlayer.
 */
@Entity
@Table(name = "game_player")
public class GamePlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "role")
    private String role;

    @Column(name = "ready")
    private Boolean ready;

    @ManyToOne
    @JsonIgnoreProperties(value = { "propsId", "gameClass", "playerB", "gamePlayers" }, allowSetters = true)
    private GameInst gameInst;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameClass", "asset", "idBS", "gamePlayers" }, allowSetters = true)
    private Player player;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GamePlayer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return this.version;
    }

    public GamePlayer version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getRole() {
        return this.role;
    }

    public GamePlayer role(String role) {
        this.setRole(role);
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getReady() {
        return this.ready;
    }

    public GamePlayer ready(Boolean ready) {
        this.setReady(ready);
        return this;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    public GameInst getGameInst() {
        return this.gameInst;
    }

    public void setGameInst(GameInst gameInst) {
        this.gameInst = gameInst;
    }

    public GamePlayer gameInst(GameInst gameInst) {
        this.setGameInst(gameInst);
        return this;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GamePlayer player(Player player) {
        this.setPlayer(player);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GamePlayer)) {
            return false;
        }
        return id != null && id.equals(((GamePlayer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GamePlayer{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", role='" + getRole() + "'" +
            ", ready='" + getReady() + "'" +
            "}";
    }
}
