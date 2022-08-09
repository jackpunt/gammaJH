import { IMmember } from 'app/entities/mmember/mmember.model';

export interface IAsset {
  id: number;
  version?: number | null;
  mmember?: Pick<IMmember, 'id'> | null;
}

export type NewAsset = Omit<IAsset, 'id'> & { id: null };
