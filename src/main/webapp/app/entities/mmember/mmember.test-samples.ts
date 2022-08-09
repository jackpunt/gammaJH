import { IMmember, NewMmember } from './mmember.model';

export const sampleWithRequiredData: IMmember = {
  id: 74594,
};

export const sampleWithPartialData: IMmember = {
  id: 61997,
  version: 92382,
};

export const sampleWithFullData: IMmember = {
  id: 78346,
  version: 21504,
};

export const sampleWithNewData: NewMmember = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
