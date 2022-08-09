import { IGroupAuthority, NewGroupAuthority } from './group-authority.model';

export const sampleWithRequiredData: IGroupAuthority = {
  id: 855,
};

export const sampleWithPartialData: IGroupAuthority = {
  id: 89072,
  version: 96067,
};

export const sampleWithFullData: IGroupAuthority = {
  id: 7840,
  version: 92332,
  authority: 'Valenciana de',
};

export const sampleWithNewData: NewGroupAuthority = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
