import { IUser } from 'app/entities/user/user.model';

export interface IAccountInfo {
  id: number;
  type?: string | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewAccountInfo = Omit<IAccountInfo, 'id'> & { id: null };
