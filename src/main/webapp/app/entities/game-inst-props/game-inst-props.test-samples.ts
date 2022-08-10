import dayjs from 'dayjs/esm';

import { IGameInstProps, NewGameInstProps } from './game-inst-props.model';

export const sampleWithRequiredData: IGameInstProps = {
  id: 83087,
};

export const sampleWithPartialData: IGameInstProps = {
  id: 54692,
  seed: 70943,
};

export const sampleWithFullData: IGameInstProps = {
  id: 6858,
  version: 14055,
  seed: 62572,
  mapName: 'exploit Delaware interface',
  mapSize: 54582,
  npcCount: 50482,
  jsonProps: 'portals',
  updated: dayjs('2022-08-09T22:55'),
};

export const sampleWithNewData: NewGameInstProps = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
