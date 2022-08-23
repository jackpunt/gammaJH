import dayjs from 'dayjs/esm';

import { IGameClass, NewGameClass } from './game-class.model';

export const sampleWithRequiredData: IGameClass = {
  id: 28064,
  name: 'Paradigm Mouse District',
  updated: dayjs('2022-08-13T07:26'),
};

export const sampleWithPartialData: IGameClass = {
  id: 35477,
  version: 30565,
  name: 'visionary B2B Plastic',
  propNames: 'visionary Ball online',
  updated: dayjs('2022-08-13T20:36'),
};

export const sampleWithFullData: IGameClass = {
  id: 16170,
  version: 46368,
  name: 'Global',
  revision: 'well-modulated Turkmenis',
  launcherPath: 'Future Skyway Naira',
  gamePath: 'invoice Savings Florida',
  docsPath: 'methodologies Vermont',
  propNames: 'e-enable',
  updated: dayjs('2022-08-13T10:59'),
};

export const sampleWithNewData: NewGameClass = {
  name: 'Pizza',
  updated: dayjs('2022-08-12T23:11'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
