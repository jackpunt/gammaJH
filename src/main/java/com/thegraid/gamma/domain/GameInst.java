package com.thegraid.gamma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A GameInst.
 */
@Entity
@Table(name = "game_inst")
public class GameInst implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "passcode")
    private String passcode;

    @Column(name = "created")
    private Instant created;

    @Column(name = "started")
    private Instant started;

    @Column(name = "finished")
    private Instant finished;

    @Column(name = "updated")
    private Instant updated;

    @Column(name = "score_a")
    private Long scoreA;

    @Column(name = "score_b")
    private Long scoreB;

    @Column(name = "ticks")
    private Long ticks;

    @Column(name = "host_url")
    private String hostUrl;

    @OneToMany(mappedBy = "gameInst1")
    @JsonIgnoreProperties(value = { "gameInsts", "gameInst1" }, allowSetters = true)
    private Set<GameInstProps> gameInstProps = new HashSet<>();

    @OneToMany(mappedBy = "gameInst")
    @JsonIgnoreProperties(value = { "gameInst", "player" }, allowSetters = true)
    private Set<GamePlayer> gamePlayers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameInsts", "gameInst1" }, allowSetters = true)
    private GameInstProps props;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameInsts", "memberGameProps", "players" }, allowSetters = true)
    private GameClass gameClass;

    @ManyToOne
    @JsonIgnoreProperties(value = { "idas", "idbs", "gamePlayers", "gameClass", "asset" }, allowSetters = true)
    private Player playerA;

    @ManyToOne
    @JsonIgnoreProperties(value = { "idas", "idbs", "gamePlayers", "gameClass", "asset" }, allowSetters = true)
    private Player playerB;

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

    public Long getVersion() {
        return this.version;
    }

    public GameInst version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
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

    public Instant getCreated() {
        return this.created;
    }

    public GameInst created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getStarted() {
        return this.started;
    }

    public GameInst started(Instant started) {
        this.setStarted(started);
        return this;
    }

    public void setStarted(Instant started) {
        this.started = started;
    }

    public Instant getFinished() {
        return this.finished;
    }

    public GameInst finished(Instant finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(Instant finished) {
        this.finished = finished;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public GameInst updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Long getScoreA() {
        return this.scoreA;
    }

    public GameInst scoreA(Long scoreA) {
        this.setScoreA(scoreA);
        return this;
    }

    public void setScoreA(Long scoreA) {
        this.scoreA = scoreA;
    }

    public Long getScoreB() {
        return this.scoreB;
    }

    public GameInst scoreB(Long scoreB) {
        this.setScoreB(scoreB);
        return this;
    }

    public void setScoreB(Long scoreB) {
        this.scoreB = scoreB;
    }

    public Long getTicks() {
        return this.ticks;
    }

    public GameInst ticks(Long ticks) {
        this.setTicks(ticks);
        return this;
    }

    public void setTicks(Long ticks) {
        this.ticks = ticks;
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

    public Set<GameInstProps> getGameInstProps() {
        return this.gameInstProps;
    }

    public void setGameInstProps(Set<GameInstProps> gameInstProps) {
        if (this.gameInstProps != null) {
            this.gameInstProps.forEach(i -> i.setGameInst1(null));
        }
        if (gameInstProps != null) {
            gameInstProps.forEach(i -> i.setGameInst1(this));
        }
        this.gameInstProps = gameInstProps;
    }

    public GameInst gameInstProps(Set<GameInstProps> gameInstProps) {
        this.setGameInstProps(gameInstProps);
        return this;
    }

    public GameInst addGameInstProps(GameInstProps gameInstProps) {
        this.gameInstProps.add(gameInstProps);
        gameInstProps.setGameInst1(this);
        return this;
    }

    public GameInst removeGameInstProps(GameInstProps gameInstProps) {
        this.gameInstProps.remove(gameInstProps);
        gameInstProps.setGameInst1(null);
        return this;
    }

    public Set<GamePlayer> getGamePlayers() {
        return this.gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        if (this.gamePlayers != null) {
            this.gamePlayers.forEach(i -> i.setGameInst(null));
        }
        if (gamePlayers != null) {
            gamePlayers.forEach(i -> i.setGameInst(this));
        }
        this.gamePlayers = gamePlayers;
    }

    public GameInst gamePlayers(Set<GamePlayer> gamePlayers) {
        this.setGamePlayers(gamePlayers);
        return this;
    }

    public GameInst addGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayers.add(gamePlayer);
        gamePlayer.setGameInst(this);
        return this;
    }

    public GameInst removeGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayers.remove(gamePlayer);
        gamePlayer.setGameInst(null);
        return this;
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
            ", passcode='" + getPasscode() + "'" +
            ", created='" + getCreated() + "'" +
            ", started='" + getStarted() + "'" +
            ", finished='" + getFinished() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", scoreA=" + getScoreA() +
            ", scoreB=" + getScoreB() +
            ", ticks=" + getTicks() +
            ", hostUrl='" + getHostUrl() + "'" +
            "}";
    }
}
