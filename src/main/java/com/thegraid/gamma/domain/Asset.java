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

    @Column(name = "name")
    private String name;

    @Column(name = "main")
    private Boolean main;

    @Column(name = "auto")
    private Boolean auto;

    @Column(name = "path")
    private String path;

    @Column(name = "include")
    private String include;

    @OneToMany(mappedBy = "asset")
    @JsonIgnoreProperties(value = { "idas", "idbs", "gamePlayers", "gameClass", "asset" }, allowSetters = true)
    private Set<Player> players = new HashSet<>();

    @ManyToOne
    private User user;

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

    public String getName() {
        return this.name;
    }

    public Asset name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getMain() {
        return this.main;
    }

    public Asset main(Boolean main) {
        this.setMain(main);
        return this;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }

    public Boolean getAuto() {
        return this.auto;
    }

    public Asset auto(Boolean auto) {
        this.setAuto(auto);
        return this;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public String getPath() {
        return this.path;
    }

    public Asset path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getInclude() {
        return this.include;
    }

    public Asset include(String include) {
        this.setInclude(include);
        return this;
    }

    public void setInclude(String include) {
        this.include = include;
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Asset user(User user) {
        this.setUser(user);
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
            ", name='" + getName() + "'" +
            ", main='" + getMain() + "'" +
            ", auto='" + getAuto() + "'" +
            ", path='" + getPath() + "'" +
            ", include='" + getInclude() + "'" +
            "}";
    }
}
