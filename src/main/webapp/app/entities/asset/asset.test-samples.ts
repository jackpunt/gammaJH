import { IAsset, NewAsset } from './asset.model';

export const sampleWithRequiredData: IAsset = {
  id: 64724,
};

export const sampleWithPartialData: IAsset = {
  id: 27601,
};

export const sampleWithFullData: IAsset = {
  id: 77403,
  version: 38136,
};

export const sampleWithNewData: NewAsset = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
