package com.thegraid.gamma.domain.intf;

public interface IGameClass {
    public long getId();

    // public Integer getVersion();
    public String getName();

    public String getRevision();

    public String getLauncherPath();

    public String getGamePath();

    // public String getDocsPath();
    public String getPropNames();

    public void setId(long id);

    // public void setVersion(Integer version);
    public void setName(String name);

    public void setRevision(String revision);

    public void setLauncherPath(String launcherPath);

    public void setGamePath(String gamePath);

    // public void setDocsPath(String docsPath);
    public void setPropNames(String propNames);
}
