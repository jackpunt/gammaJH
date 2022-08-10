import dayjs from 'dayjs/esm';
import { IGameInstProps } from 'app/entities/game-inst-props/game-inst-props.model';
import { IGameClass } from 'app/entities/game-class/game-class.model';
import { IPlayer } from 'app/entities/player/player.model';

export interface IGameInst {
  id: number;
  version?: number | null;
  gameName?: string | null;
  passcode?: string | null;
  created?: dayjs.Dayjs | null;
  started?: dayjs.Dayjs | null;
  finished?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  scoreA?: number | null;
  scoreB?: number | null;
  ticks?: number | null;
  hostUrl?: string | null;
  props?: Pick<IGameInstProps, 'id'> | null;
  gameClass?: Pick<IGameClass, 'id'> | null;
  playerA?: Pick<IPlayer, 'id'> | null;
  playerB?: Pick<IPlayer, 'id'> | null;
}

export type NewGameInst = Omit<IGameInst, 'id'> & { id: null };
