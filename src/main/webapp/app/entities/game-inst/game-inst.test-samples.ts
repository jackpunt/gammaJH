import dayjs from 'dayjs/esm';

import { IGameInst, NewGameInst } from './game-inst.model';

export const sampleWithRequiredData: IGameInst = {
  id: 82250,
  created: dayjs('2022-08-13T15:37'),
  updated: dayjs('2022-08-13T07:54'),
};

export const sampleWithPartialData: IGameInst = {
  id: 18754,
  version: 94694,
  gameName: 'mindshare',
  hostUrl: 'Shoes',
  passcode: 'Practical architectures Chicken',
  created: dayjs('2022-08-13T19:19'),
  finished: dayjs('2022-08-13T14:34'),
  updated: dayjs('2022-08-13T15:49'),
  scoreB: 7036,
};

export const sampleWithFullData: IGameInst = {
  id: 76184,
  version: 98916,
  gameName: 'Uruguay',
  hostUrl: 'connecting Cameroon hardware',
  passcode: 'mobile',
  created: dayjs('2022-08-13T20:50'),
  started: dayjs('2022-08-12T23:49'),
  finished: dayjs('2022-08-13T12:44'),
  updated: dayjs('2022-08-13T07:14'),
  scoreA: 28501,
  scoreB: 45049,
  ticks: 44541,
};

export const sampleWithNewData: NewGameInst = {
  created: dayjs('2022-08-13T15:58'),
  updated: dayjs('2022-08-13T06:49'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
