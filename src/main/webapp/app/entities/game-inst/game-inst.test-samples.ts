import dayjs from 'dayjs/esm';

import { IGameInst, NewGameInst } from './game-inst.model';

export const sampleWithRequiredData: IGameInst = {
  id: 82250,
};

export const sampleWithPartialData: IGameInst = {
  id: 18754,
  gameName: 'Algodón Guantes compress',
  passcode: 'functionalities',
  created: dayjs('2022-08-09T09:52'),
  started: dayjs('2022-08-08T23:20'),
  finished: dayjs('2022-08-09T19:35'),
  scoreA: 66970,
  ticks: 79711,
};

export const sampleWithFullData: IGameInst = {
  id: 13364,
  version: 33193,
  gameName: 'Hormigon',
  passcode: 'Gorro Burundi Gráfica',
  created: dayjs('2022-08-09T21:06'),
  started: dayjs('2022-08-09T04:06'),
  finished: dayjs('2022-08-08T23:44'),
  updated: dayjs('2022-08-09T06:42'),
  scoreA: 7066,
  scoreB: 94670,
  ticks: 40831,
  hostUrl: 'holística multitarea',
};

export const sampleWithNewData: NewGameInst = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
