package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Asset.
 */
@Entity
@Table(name = "asset")
public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JsonIgnoreProperties(value = { "roleGroup", "assets", "mmemberGameProps" }, allowSetters = true)
    private Mmember mmember;

    @OneToMany(mappedBy = "asset")
    @JsonIgnoreProperties(value = { "gameClass", "asset", "idBS", "gamePlayers" }, allowSetters = true)
    private Set<Player> players = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Asset id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return this.version;
    }

    public Asset version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Mmember getMmember() {
        return this.mmember;
    }

    public void setMmember(Mmember mmember) {
        this.mmember = mmember;
    }

    public Asset mmember(Mmember mmember) {
        this.setMmember(mmember);
        return this;
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(Set<Player> players) {
        if (this.players != null) {
            this.players.forEach(i -> i.setAsset(null));
        }
        if (players != null) {
            players.forEach(i -> i.setAsset(this));
        }
        this.players = players;
    }

    public Asset players(Set<Player> players) {
        this.setPlayers(players);
        return this;
    }

    public Asset addPlayer(Player player) {
        this.players.add(player);
        player.setAsset(this);
        return this;
    }

    public Asset removePlayer(Player player) {
        this.players.remove(player);
        player.setAsset(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asset)) {
            return false;
        }
        return id != null && id.equals(((Asset) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Asset{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            "}";
    }
}
