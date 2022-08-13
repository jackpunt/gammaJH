import dayjs from 'dayjs/esm';

export interface IGameInstProps {
  id: number;
  version?: number | null;
  seed?: number | null;
  mapName?: string | null;
  mapSize?: number | null;
  npcCount?: number | null;
  jsonProps?: string | null;
  updated?: dayjs.Dayjs | null;
}

export type NewGameInstProps = Omit<IGameInstProps, 'id'> & { id: null };
