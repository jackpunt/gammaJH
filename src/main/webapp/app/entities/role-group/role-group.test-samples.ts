import { IRoleGroup, NewRoleGroup } from './role-group.model';

export const sampleWithRequiredData: IRoleGroup = {
  id: 76755,
};

export const sampleWithPartialData: IRoleGroup = {
  id: 35529,
  version: 92322,
};

export const sampleWithFullData: IRoleGroup = {
  id: 75302,
  version: 62021,
  groupName: 'Global',
};

export const sampleWithNewData: NewRoleGroup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
