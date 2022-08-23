import dayjs from 'dayjs/esm';

import { IGameInst, NewGameInst } from './game-inst.model';

export const sampleWithRequiredData: IGameInst = {
  id: 20006,
  created: dayjs('2022-08-13T02:47'),
  updated: dayjs('2022-08-13T15:37'),
};

export const sampleWithPartialData: IGameInst = {
  id: 47357,
  version: 22952,
  gameName: 'Research',
  hostUrl: 'Gloves compress Practical',
  passcode: "Zambia Pa'anga",
  created: dayjs('2022-08-13T14:34'),
  started: dayjs('2022-08-13T15:49'),
  updated: dayjs('2022-08-13T20:51'),
  scoreA: 76184,
  ticks: 98916,
};

export const sampleWithFullData: IGameInst = {
  id: 49328,
  version: 24979,
  gameName: 'Program connecting',
  hostUrl: 'Metal bandwidth',
  passcode: 'Developer Group heuristic',
  created: dayjs('2022-08-13T15:58'),
  started: dayjs('2022-08-13T06:49'),
  finished: dayjs('2022-08-13T07:15'),
  updated: dayjs('2022-08-13T20:57'),
  scoreA: 50565,
  scoreB: 33673,
  ticks: 97930,
};

export const sampleWithNewData: NewGameInst = {
  created: dayjs('2022-08-12T23:19'),
  updated: dayjs('2022-08-13T04:51'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
