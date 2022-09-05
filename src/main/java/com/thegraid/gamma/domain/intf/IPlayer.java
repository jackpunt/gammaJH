package com.thegraid.gamma.domain.intf;

import java.time.ZonedDateTime;

public interface IPlayer {
    public ZonedDateTime getScoreTime();

    public ZonedDateTime getRankTime();

    public long getId();

    // public Integer getVersion();
    // public Asset getMainJar();
    public String getMainJarPath();

    public String[] getIncludes();

    // public GameClass getGameClass();
    // public Member getMember();
    public String getName();

    public Integer getRank();

    public Integer getScore();

    public String getDisplayClient();

    // PlayerDTO is typically included in a GameInstDTO, for that GameInst:
    public Long getGamePlayerId(); // from GamePlayer

    public String getRole(); // from GamePlayer

    public void setScoreTime(ZonedDateTime scoreTime);

    public void setRankTime(ZonedDateTime rankTime);

    public void setId(long id);

    // public void setVersion(Integer version);
    // public void setMainJar(Asset mainJar);
    public void setMainJarPath(String mainJarPath);

    public void setIncludes(String... includes);

    // public void setGameClass(GameClass gameClass);
    // public void setMember(Member member);
    public void setName(String name);

    public void setRank(Integer rank);

    public void setScore(Integer score);

    public void setDisplayClient(String displayClient);

    public void setGamePlayerId(Long gpid);

    public void setRole(String role);
}
