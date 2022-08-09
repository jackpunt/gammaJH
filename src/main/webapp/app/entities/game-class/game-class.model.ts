import dayjs from 'dayjs/esm';

export interface IGameClass {
  id: number;
  version?: number | null;
  name?: string | null;
  revision?: string | null;
  launcherPath?: string | null;
  gamePath?: string | null;
  docsPath?: string | null;
  propsNames?: string | null;
  updateed?: dayjs.Dayjs | null;
}

export type NewGameClass = Omit<IGameClass, 'id'> & { id: null };
