import dayjs from 'dayjs/esm';

import { IGameClass, NewGameClass } from './game-class.model';

export const sampleWithRequiredData: IGameClass = {
  id: 28064,
};

export const sampleWithPartialData: IGameClass = {
  id: 41898,
  version: 31524,
  name: 'Money Shoes',
  updated: dayjs('2022-08-13T14:01'),
};

export const sampleWithFullData: IGameClass = {
  id: 30565,
  version: 95687,
  name: 'CSS neutral',
  revision: 'Interactions',
  launcherPath: 'Hat Loop Shoes',
  gamePath: 'Macedonia well-modulated Turkmenistan',
  docsPath: 'Future Skyway Naira',
  propsNames: 'invoice Savings Florida',
  updated: dayjs('2022-08-13T06:41'),
};

export const sampleWithNewData: NewGameClass = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
