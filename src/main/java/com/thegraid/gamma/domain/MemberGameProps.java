package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;

/**
 * member/player proposes properties for a new GameInst.
 */
@Schema(description = "member/player proposes properties for a new GameInst.")
@Entity
@Table(name = "member_game_props")
public class MemberGameProps implements Serializable {

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
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameInsts", "memberGameProps", "players" }, allowSetters = true)
    private GameClass gameClass;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MemberGameProps id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return this.version;
    }

    public MemberGameProps version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getSeed() {
        return this.seed;
    }

    public MemberGameProps seed(Long seed) {
        this.setSeed(seed);
        return this;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public String getMapName() {
        return this.mapName;
    }

    public MemberGameProps mapName(String mapName) {
        this.setMapName(mapName);
        return this;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Long getMapSize() {
        return this.mapSize;
    }

    public MemberGameProps mapSize(Long mapSize) {
        this.setMapSize(mapSize);
        return this;
    }

    public void setMapSize(Long mapSize) {
        this.mapSize = mapSize;
    }

    public Long getNpcCount() {
        return this.npcCount;
    }

    public MemberGameProps npcCount(Long npcCount) {
        this.setNpcCount(npcCount);
        return this;
    }

    public void setNpcCount(Long npcCount) {
        this.npcCount = npcCount;
    }

    public String getJsonProps() {
        return this.jsonProps;
    }

    public MemberGameProps jsonProps(String jsonProps) {
        this.setJsonProps(jsonProps);
        return this;
    }

    public void setJsonProps(String jsonProps) {
        this.jsonProps = jsonProps;
    }

    public String getConfigName() {
        return this.configName;
    }

    public MemberGameProps configName(String configName) {
        this.setConfigName(configName);
        return this;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MemberGameProps user(User user) {
        this.setUser(user);
        return this;
    }

    public GameClass getGameClass() {
        return this.gameClass;
    }

    public void setGameClass(GameClass gameClass) {
        this.gameClass = gameClass;
    }

    public MemberGameProps gameClass(GameClass gameClass) {
        this.setGameClass(gameClass);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberGameProps)) {
            return false;
        }
        return id != null && id.equals(((MemberGameProps) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberGameProps{" +
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
