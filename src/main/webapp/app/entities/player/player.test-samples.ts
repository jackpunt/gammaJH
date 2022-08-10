import dayjs from 'dayjs/esm';

import { IPlayer, NewPlayer } from './player.model';

export const sampleWithRequiredData: IPlayer = {
  id: 92110,
};

export const sampleWithPartialData: IPlayer = {
  id: 24081,
  version: 70291,
  rank: 28559,
  score: 3535,
  rankTime: dayjs('2022-08-10T19:43'),
};

export const sampleWithFullData: IPlayer = {
  id: 97222,
  version: 47305,
  name: 'Somalia 1080p',
  rank: 40885,
  score: 26894,
  scoreTime: dayjs('2022-08-10T16:08'),
  rankTime: dayjs('2022-08-10T07:16'),
  displayClient: 'brand',
};

export const sampleWithNewData: NewPlayer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
