import dayjs from 'dayjs/esm';

import { IGameInstProps, NewGameInstProps } from './game-inst-props.model';

export const sampleWithRequiredData: IGameInstProps = {
  id: 70844,
  updated: dayjs('2022-08-13T02:35'),
};

export const sampleWithPartialData: IGameInstProps = {
  id: 58138,
  seed: 28956,
  updated: dayjs('2022-08-13T09:24'),
};

export const sampleWithFullData: IGameInstProps = {
  id: 49123,
  version: 70943,
  seed: 6858,
  mapName: 'Loan',
  mapSize: 86117,
  npcCount: 38474,
  jsonProps: 'Soap exploit',
  updated: dayjs('2022-08-13T10:25'),
};

export const sampleWithNewData: NewGameInstProps = {
  updated: dayjs('2022-08-13T15:45'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
