package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A GameInstProps.
 */
@Entity
@Table(name = "game_inst_props")
public class GameInstProps implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "seed")
    private Long seed;

    @Column(name = "map_name")
    private String mapName;

    @Column(name = "map_size")
    private Long mapSize;

    @Column(name = "npc_count")
    private Long npcCount;

    @Column(name = "json_props")
    private String jsonProps;

    @Column(name = "updated")
    private Instant updated;

    @OneToMany(mappedBy = "props")
    @JsonIgnoreProperties(value = { "gameInstProps", "gamePlayers", "props", "gameClass", "playerA", "playerB" }, allowSetters = true)
    private Set<GameInst> gameInsts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameInstProps", "gamePlayers", "props", "gameClass", "playerA", "playerB" }, allowSetters = true)
    private GameInst gameInst1;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GameInstProps id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return this.version;
    }

    public GameInstProps version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getSeed() {
        return this.seed;
    }

    public GameInstProps seed(Long seed) {
        this.setSeed(seed);
        return this;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public String getMapName() {
        return this.mapName;
    }

    public GameInstProps mapName(String mapName) {
        this.setMapName(mapName);
        return this;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Long getMapSize() {
        return this.mapSize;
    }

    public GameInstProps mapSize(Long mapSize) {
        this.setMapSize(mapSize);
        return this;
    }

    public void setMapSize(Long mapSize) {
        this.mapSize = mapSize;
    }

    public Long getNpcCount() {
        return this.npcCount;
    }

    public GameInstProps npcCount(Long npcCount) {
        this.setNpcCount(npcCount);
        return this;
    }

    public void setNpcCount(Long npcCount) {
        this.npcCount = npcCount;
    }

    public String getJsonProps() {
        return this.jsonProps;
    }

    public GameInstProps jsonProps(String jsonProps) {
        this.setJsonProps(jsonProps);
        return this;
    }

    public void setJsonProps(String jsonProps) {
        this.jsonProps = jsonProps;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public GameInstProps updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Set<GameInst> getGameInsts() {
        return this.gameInsts;
    }

    public void setGameInsts(Set<GameInst> gameInsts) {
        if (this.gameInsts != null) {
            this.gameInsts.forEach(i -> i.setProps(null));
        }
        if (gameInsts != null) {
            gameInsts.forEach(i -> i.setProps(this));
        }
        this.gameInsts = gameInsts;
    }

    public GameInstProps gameInsts(Set<GameInst> gameInsts) {
        this.setGameInsts(gameInsts);
        return this;
    }

    public GameInstProps addGameInst(GameInst gameInst) {
        this.gameInsts.add(gameInst);
        gameInst.setProps(this);
        return this;
    }

    public GameInstProps removeGameInst(GameInst gameInst) {
        this.gameInsts.remove(gameInst);
        gameInst.setProps(null);
        return this;
    }

    public GameInst getGameInst1() {
        return this.gameInst1;
    }

    public void setGameInst1(GameInst gameInst) {
        this.gameInst1 = gameInst;
    }

    public GameInstProps gameInst1(GameInst gameInst) {
        this.setGameInst1(gameInst);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameInstProps)) {
            return false;
        }
        return id != null && id.equals(((GameInstProps) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameInstProps{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", seed=" + getSeed() +
            ", mapName='" + getMapName() + "'" +
            ", mapSize=" + getMapSize() +
            ", npcCount=" + getNpcCount() +
            ", jsonProps='" + getJsonProps() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
