import { IMmember } from 'app/entities/mmember/mmember.model';
import { IGameClass } from 'app/entities/game-class/game-class.model';

export interface IMmemberGameProps {
  id: number;
  version?: number | null;
  seed?: number | null;
  mapName?: string | null;
  mapSize?: number | null;
  npcCount?: number | null;
  jsonProps?: string | null;
  configName?: string | null;
  mmember?: Pick<IMmember, 'id'> | null;
  gameClass?: Pick<IGameClass, 'id'> | null;
}

export type NewMmemberGameProps = Omit<IMmemberGameProps, 'id'> & { id: null };
