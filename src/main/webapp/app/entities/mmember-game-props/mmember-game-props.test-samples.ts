import { IMmemberGameProps, NewMmemberGameProps } from './mmember-game-props.model';

export const sampleWithRequiredData: IMmemberGameProps = {
  id: 95956,
};

export const sampleWithPartialData: IMmemberGameProps = {
  id: 31728,
  version: 435,
  seed: 39760,
  mapSize: 44193,
  jsonProps: 'Account',
};

export const sampleWithFullData: IMmemberGameProps = {
  id: 27600,
  version: 95110,
  seed: 53727,
  mapName: 'program infomediaries',
  mapSize: 60461,
  npcCount: 86027,
  jsonProps: 'invoice',
  configName: 'direccional Hormigon sticky',
};

export const sampleWithNewData: NewMmemberGameProps = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
