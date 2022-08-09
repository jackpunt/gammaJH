package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A MmemberGameProps.
 */
@Entity
@Table(name = "mmember_game_props")
public class MmemberGameProps implements Serializable {

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

    @Column(name = "config_name")
    private String configName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "roleGroup", "assets", "mmemberGameProps" }, allowSetters = true)
    private Mmember mmember;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameInsts", "players", "mmemberGameProps" }, allowSetters = true)
    private GameClass gameClass;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MmemberGameProps id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return this.version;
    }

    public MmemberGameProps version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getSeed() {
        return this.seed;
    }

    public MmemberGameProps seed(Long seed) {
        this.setSeed(seed);
        return this;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public String getMapName() {
        return this.mapName;
    }

    public MmemberGameProps mapName(String mapName) {
        this.setMapName(mapName);
        return this;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Long getMapSize() {
        return this.mapSize;
    }

    public MmemberGameProps mapSize(Long mapSize) {
        this.setMapSize(mapSize);
        return this;
    }

    public void setMapSize(Long mapSize) {
        this.mapSize = mapSize;
    }

    public Long getNpcCount() {
        return this.npcCount;
    }

    public MmemberGameProps npcCount(Long npcCount) {
        this.setNpcCount(npcCount);
        return this;
    }

    public void setNpcCount(Long npcCount) {
        this.npcCount = npcCount;
    }

    public String getJsonProps() {
        return this.jsonProps;
    }

    public MmemberGameProps jsonProps(String jsonProps) {
        this.setJsonProps(jsonProps);
        return this;
    }

    public void setJsonProps(String jsonProps) {
        this.jsonProps = jsonProps;
    }

    public String getConfigName() {
        return this.configName;
    }

    public MmemberGameProps configName(String configName) {
        this.setConfigName(configName);
        return this;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public Mmember getMmember() {
        return this.mmember;
    }

    public void setMmember(Mmember mmember) {
        this.mmember = mmember;
    }

    public MmemberGameProps mmember(Mmember mmember) {
        this.setMmember(mmember);
        return this;
    }

    public GameClass getGameClass() {
        return this.gameClass;
    }

    public void setGameClass(GameClass gameClass) {
        this.gameClass = gameClass;
    }

    public MmemberGameProps gameClass(GameClass gameClass) {
        this.setGameClass(gameClass);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MmemberGameProps)) {
            return false;
        }
        return id != null && id.equals(((MmemberGameProps) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MmemberGameProps{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", seed=" + getSeed() +
            ", mapName='" + getMapName() + "'" +
            ", mapSize=" + getMapSize() +
            ", npcCount=" + getNpcCount() +
            ", jsonProps='" + getJsonProps() + "'" +
            ", configName='" + getConfigName() + "'" +
            "}";
    }
}
