package com.thegraid.gamma.domain.intf;

import com.thegraid.gamma.service.dto.GameClassDTO;
import com.thegraid.gamma.service.dto.PlayerDTO;
import java.time.Instant;
import java.util.Map;

public interface IGameInstDTO {
    public Long getId();

    public GameClassDTO getGameClass();

    public PlayerDTO getPlayerA();

    public PlayerDTO getPlayerB();

    public String getGameName();

    public String getHostUrl();

    public String getPasscode();

    // findGameInstProps(this.getId()).parseJSON().asMap()
    public Map<String, Object> getPropertyMap();

    public Integer getScoreA();

    public Integer getScoreB();

    public Integer getTicks();

    public Instant getCreated();

    public Instant getStarted();

    public Instant getFinished();

    public void setId(Long id);

    public void setGameClass(GameClassDTO gameClass);

    public void setPlayerA(PlayerDTO playerA);

    public void setPlayerB(PlayerDTO playerB);

    public void setGameName(String gameName);

    public void setHostUrl(String hostUrl);

    public void setPasscode(String passcode);

    public void setScoreA(Integer scoreA);

    public void setScoreB(Integer scoreB);

    public void setTicks(Integer ticks);

    public void setCreated(Instant created);

    public void setStarted(Instant started);

    public void setFinished(Instant finished);
}
