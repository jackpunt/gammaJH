package com.thegraid.gamma.domain.intf;

import com.thegraid.gamma.service.dto.AssetDTO;
import com.thegraid.gamma.service.dto.GameClassDTO;
import com.thegraid.gamma.service.dto.UserDTO;
import java.time.Instant;

/** service.dto.PlayerDTO implements IPlayerDTO */
public interface IPlayerDTO {
    public Instant getScoreTime();

    public Instant getRankTime();

    public Long getId();

    // public Integer getVersion();
    public AssetDTO getMainJar();

    //public String getMainJarPath();

    public GameClassDTO getGameClass();

    // public Member getMember();
    public UserDTO getUser();

    // public String getName();

    public Integer getRank();

    public Integer getScore();

    public String getDisplayClient();

    // PlayerDTO is typically included in a GameInstDTO, for that GameInst:
    // public Long getGamePlayerId(); // from GamePlayer

    // getGamePlayer().getRole();
    // public String getRole(); // from GamePlayer

    public void setScoreTime(Instant scoreTime);

    public void setRankTime(Instant rankTime);

    public void setId(Long id);

    // public void setVersion(Integer version);
    public void setMainJar(AssetDTO mainJar);

    // public void setMainJarPath(String mainJarPath);

    public void setGameClass(GameClassDTO gameClass);

    // public void setMember(IMemberDTO member);
    public void setUser(UserDTO member);

    // public void setName(String name);

    public void setRank(Integer rank);

    public void setScore(Integer score);

    public void setDisplayClient(String displayClient);
    // public void setGamePlayerId(Long gpid);

    // public void setRole(String role);
}
