import dayjs from 'dayjs/esm';

import { IGameInst, NewGameInst } from './game-inst.model';

export const sampleWithRequiredData: IGameInst = {
  id: 20006,
};

export const sampleWithPartialData: IGameInst = {
  id: 47357,
  version: 22952,
  hostUrl: 'Research',
  passcode: 'Gloves compress Practical',
  created: dayjs('2022-08-13T12:08'),
  started: dayjs('2022-08-13T09:14'),
  finished: dayjs('2022-08-12T22:42'),
  scoreA: 14896,
  ticks: 66970,
};

export const sampleWithFullData: IGameInst = {
  id: 49328,
  version: 79711,
  gameName: 'emulation',
  hostUrl: 'Engineer Program connecting',
  passcode: 'Metal bandwidth',
  created: dayjs('2022-08-13T03:29'),
  started: dayjs('2022-08-12T23:06'),
  finished: dayjs('2022-08-13T06:04'),
  updated: dayjs('2022-08-13T20:50'),
  scoreA: 94670,
  scoreB: 40831,
  ticks: 63714,
};

export const sampleWithNewData: NewGameInst = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
