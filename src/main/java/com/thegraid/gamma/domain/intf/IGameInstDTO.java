package com.thegraid.gamma.domain.intf;

import com.thegraid.gamma.service.dto.GameClassDTO;
import com.thegraid.gamma.service.dto.PlayerDTO;
import java.time.ZonedDateTime;
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

    public ZonedDateTime getCreated();

    public ZonedDateTime getStarted();

    public ZonedDateTime getFinished();

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

    public void setCreated(ZonedDateTime created);

    public void setStarted(ZonedDateTime started);

    public void setFinished(ZonedDateTime finished);
}
