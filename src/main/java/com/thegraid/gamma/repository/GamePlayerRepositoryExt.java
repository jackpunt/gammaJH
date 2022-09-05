package com.thegraid.gamma.repository;

import com.thegraid.gamma.domain.GameInst;
import com.thegraid.gamma.domain.GamePlayer;
import com.thegraid.gamma.domain.Player;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface GamePlayerRepositoryExt extends GamePlayerRepository {
    @Query("select gamePlayer from GamePlayer AS gamePlayer where gamePlayer.player = :plyr")
    List<GamePlayer> getGamePlayers(@Param("plyr") Player plyr);

    @Query("select gamePlayer from GamePlayer AS gamePlayer where gamePlayer.gameInst = :gi")
    List<GamePlayer> getGamePlayers(@Param("gi") GameInst gi);
}
