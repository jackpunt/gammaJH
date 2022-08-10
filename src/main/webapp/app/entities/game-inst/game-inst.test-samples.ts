import dayjs from 'dayjs/esm';

import { IGameInst, NewGameInst } from './game-inst.model';

export const sampleWithRequiredData: IGameInst = {
  id: 82250,
};

export const sampleWithPartialData: IGameInst = {
  id: 18754,
  gameName: 'Cotton Gloves compress',
  passcode: 'functionalities',
  created: dayjs('2022-08-10T08:29'),
  started: dayjs('2022-08-09T21:57'),
  finished: dayjs('2022-08-10T18:12'),
  scoreA: 66970,
  ticks: 79711,
};

export const sampleWithFullData: IGameInst = {
  id: 13364,
  version: 33193,
  gameName: 'Frozen',
  passcode: 'Hat Cameroon hardware',
  created: dayjs('2022-08-10T19:43'),
  started: dayjs('2022-08-10T02:43'),
  finished: dayjs('2022-08-09T22:21'),
  updated: dayjs('2022-08-10T05:18'),
  scoreA: 7066,
  scoreB: 94670,
  ticks: 40831,
  hostUrl: 'heuristic multi-state',
};

export const sampleWithNewData: NewGameInst = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
