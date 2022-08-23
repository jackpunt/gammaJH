package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Instance of a Game.
 */
@Entity
@Table(name = "game_inst")
public class GameInst implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Integer version;

    @Size(max = 64)
    @Column(name = "game_name", length = 64)
    private String gameName;

    @Size(max = 64)
    @Column(name = "host_url", length = 64)
    private String hostUrl;

    @Size(max = 64)
    @Column(name = "passcode", length = 64)
    private String passcode;

    @NotNull
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @Column(name = "started")
    private ZonedDateTime started;

    @Column(name = "finished")
    private ZonedDateTime finished;

    @NotNull
    @Column(name = "updated", nullable = false)
    private ZonedDateTime updated;

    @Column(name = "score_a")
    private Integer scoreA;

    @Column(name = "score_b")
    private Integer scoreB;

    @Column(name = "ticks")
    private Integer ticks;

    @JsonIgnoreProperties(value = { "gameInst" }, allowSetters = true)
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private GameInstProps props;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameClass", "mainJar", "user" }, allowSetters = true)
    private Player playerA;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameClass", "mainJar", "user" }, allowSetters = true)
    private Player playerB;

    @ManyToOne
    private GameClass gameClass;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GameInst id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public GameInst version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getGameName() {
        return this.gameName;
    }

    public GameInst gameName(String gameName) {
        this.setGameName(gameName);
        return this;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getHostUrl() {
        return this.hostUrl;
    }

    public GameInst hostUrl(String hostUrl) {
        this.setHostUrl(hostUrl);
        return this;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public String getPasscode() {
        return this.passcode;
    }

    public GameInst passcode(String passcode) {
        this.setPasscode(passcode);
        return this;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public GameInst created(ZonedDateTime created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getStarted() {
        return this.started;
    }

    public GameInst started(ZonedDateTime started) {
        this.setStarted(started);
        return this;
    }

    public void setStarted(ZonedDateTime started) {
        this.started = started;
    }

    public ZonedDateTime getFinished() {
        return this.finished;
    }

    public GameInst finished(ZonedDateTime finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(ZonedDateTime finished) {
        this.finished = finished;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public GameInst updated(ZonedDateTime updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Integer getScoreA() {
        return this.scoreA;
    }

    public GameInst scoreA(Integer scoreA) {
        this.setScoreA(scoreA);
        return this;
    }

    public void setScoreA(Integer scoreA) {
        this.scoreA = scoreA;
    }

    public Integer getScoreB() {
        return this.scoreB;
    }

    public GameInst scoreB(Integer scoreB) {
        this.setScoreB(scoreB);
        return this;
    }

    public void setScoreB(Integer scoreB) {
        this.scoreB = scoreB;
    }

    public Integer getTicks() {
        return this.ticks;
    }

    public GameInst ticks(Integer ticks) {
        this.setTicks(ticks);
        return this;
    }

    public void setTicks(Integer ticks) {
        this.ticks = ticks;
    }

    public GameInstProps getProps() {
        return this.props;
    }

    public void setProps(GameInstProps gameInstProps) {
        this.props = gameInstProps;
    }

    public GameInst props(GameInstProps gameInstProps) {
        this.setProps(gameInstProps);
        return this;
    }

    public Player getPlayerA() {
        return this.playerA;
    }

    public void setPlayerA(Player player) {
        this.playerA = player;
    }

    public GameInst playerA(Player player) {
        this.setPlayerA(player);
        return this;
    }

    public Player getPlayerB() {
        return this.playerB;
    }

    public void setPlayerB(Player player) {
        this.playerB = player;
    }

    public GameInst playerB(Player player) {
        this.setPlayerB(player);
        return this;
    }

    public GameClass getGameClass() {
        return this.gameClass;
    }

    public void setGameClass(GameClass gameClass) {
        this.gameClass = gameClass;
    }

    public GameInst gameClass(GameClass gameClass) {
        this.setGameClass(gameClass);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameInst)) {
            return false;
        }
        return id != null && id.equals(((GameInst) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameInst{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", gameName='" + getGameName() + "'" +
            ", hostUrl='" + getHostUrl() + "'" +
            ", passcode='" + getPasscode() + "'" +
            ", created='" + getCreated() + "'" +
            ", started='" + getStarted() + "'" +
            ", finished='" + getFinished() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", scoreA=" + getScoreA() +
            ", scoreB=" + getScoreB() +
            ", ticks=" + getTicks() +
            "}";
    }
}
