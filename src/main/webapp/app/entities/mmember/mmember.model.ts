import { IRoleGroup } from 'app/entities/role-group/role-group.model';

export interface IMmember {
  id: number;
  version?: number | null;
  roleGroup?: Pick<IRoleGroup, 'id'> | null;
}

export type NewMmember = Omit<IMmember, 'id'> & { id: null };
