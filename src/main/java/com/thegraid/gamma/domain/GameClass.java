package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A GameClass.
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
    private Long version;

    @Column(name = "name")
    private String name;

    @Column(name = "revision")
    private String revision;

    @Column(name = "launcher_path")
    private String launcherPath;

    @Column(name = "game_path")
    private String gamePath;

    @Column(name = "docs_path")
    private String docsPath;

    @Column(name = "props_names")
    private String propsNames;

    @Column(name = "updateed")
    private Instant updateed;

    @OneToMany(mappedBy = "gameClass")
    @JsonIgnoreProperties(value = { "propsId", "gameClass", "playerB", "gamePlayers" }, allowSetters = true)
    private Set<GameInst> gameInsts = new HashSet<>();

    @OneToMany(mappedBy = "gameClass")
    @JsonIgnoreProperties(value = { "gameClass", "asset", "idBS", "gamePlayers" }, allowSetters = true)
    private Set<Player> players = new HashSet<>();

    @OneToMany(mappedBy = "gameClass")
    @JsonIgnoreProperties(value = { "mmember", "gameClass" }, allowSetters = true)
    private Set<MmemberGameProps> mmemberGameProps = new HashSet<>();

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

    public Long getVersion() {
        return this.version;
    }

    public GameClass version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
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

    public Instant getUpdateed() {
        return this.updateed;
    }

    public GameClass updateed(Instant updateed) {
        this.setUpdateed(updateed);
        return this;
    }

    public void setUpdateed(Instant updateed) {
        this.updateed = updateed;
    }

    public Set<GameInst> getGameInsts() {
        return this.gameInsts;
    }

    public void setGameInsts(Set<GameInst> gameInsts) {
        if (this.gameInsts != null) {
            this.gameInsts.forEach(i -> i.setGameClass(null));
        }
        if (gameInsts != null) {
            gameInsts.forEach(i -> i.setGameClass(this));
        }
        this.gameInsts = gameInsts;
    }

    public GameClass gameInsts(Set<GameInst> gameInsts) {
        this.setGameInsts(gameInsts);
        return this;
    }

    public GameClass addGameInst(GameInst gameInst) {
        this.gameInsts.add(gameInst);
        gameInst.setGameClass(this);
        return this;
    }

    public GameClass removeGameInst(GameInst gameInst) {
        this.gameInsts.remove(gameInst);
        gameInst.setGameClass(null);
        return this;
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(Set<Player> players) {
        if (this.players != null) {
            this.players.forEach(i -> i.setGameClass(null));
        }
        if (players != null) {
            players.forEach(i -> i.setGameClass(this));
        }
        this.players = players;
    }

    public GameClass players(Set<Player> players) {
        this.setPlayers(players);
        return this;
    }

    public GameClass addPlayer(Player player) {
        this.players.add(player);
        player.setGameClass(this);
        return this;
    }

    public GameClass removePlayer(Player player) {
        this.players.remove(player);
        player.setGameClass(null);
        return this;
    }

    public Set<MmemberGameProps> getMmemberGameProps() {
        return this.mmemberGameProps;
    }

    public void setMmemberGameProps(Set<MmemberGameProps> mmemberGameProps) {
        if (this.mmemberGameProps != null) {
            this.mmemberGameProps.forEach(i -> i.setGameClass(null));
        }
        if (mmemberGameProps != null) {
            mmemberGameProps.forEach(i -> i.setGameClass(this));
        }
        this.mmemberGameProps = mmemberGameProps;
    }

    public GameClass mmemberGameProps(Set<MmemberGameProps> mmemberGameProps) {
        this.setMmemberGameProps(mmemberGameProps);
        return this;
    }

    public GameClass addMmemberGameProps(MmemberGameProps mmemberGameProps) {
        this.mmemberGameProps.add(mmemberGameProps);
        mmemberGameProps.setGameClass(this);
        return this;
    }

    public GameClass removeMmemberGameProps(MmemberGameProps mmemberGameProps) {
        this.mmemberGameProps.remove(mmemberGameProps);
        mmemberGameProps.setGameClass(null);
        return this;
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
            ", updateed='" + getUpdateed() + "'" +
            "}";
    }
}
