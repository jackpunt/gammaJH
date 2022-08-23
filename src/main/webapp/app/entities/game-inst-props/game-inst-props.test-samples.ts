import dayjs from 'dayjs/esm';

import { IGameInstProps, NewGameInstProps } from './game-inst-props.model';

export const sampleWithRequiredData: IGameInstProps = {
  id: 83087,
  updated: dayjs('2022-08-13T15:42'),
};

export const sampleWithPartialData: IGameInstProps = {
  id: 54692,
  version: 70943,
  updated: dayjs('2022-08-13T20:53'),
};

export const sampleWithFullData: IGameInstProps = {
  id: 14055,
  version: 62572,
  seed: 78411,
  mapName: 'AI Soap',
  mapSize: 40518,
  npcCount: 85128,
  jsonProps: 'Bedfordshire 24/365',
  updated: dayjs('2022-08-13T12:53'),
};

export const sampleWithNewData: NewGameInstProps = {
  updated: dayjs('2022-08-13T13:07'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
