import { IRoleGroup } from 'app/entities/role-group/role-group.model';

export interface IGroupAuthority {
  id: number;
  version?: number | null;
  authority?: string | null;
  roleGroup?: Pick<IRoleGroup, 'id'> | null;
}

export type NewGroupAuthority = Omit<IGroupAuthority, 'id'> & { id: null };
