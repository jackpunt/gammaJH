export interface IRoleGroup {
  id: number;
  version?: number | null;
  groupName?: string | null;
}

export type NewRoleGroup = Omit<IRoleGroup, 'id'> & { id: null };
