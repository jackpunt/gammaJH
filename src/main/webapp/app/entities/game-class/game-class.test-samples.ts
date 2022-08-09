import dayjs from 'dayjs/esm';

import { IGameClass, NewGameClass } from './game-class.model';

export const sampleWithRequiredData: IGameClass = {
  id: 28064,
};

export const sampleWithPartialData: IGameClass = {
  id: 41898,
  version: 31524,
  name: 'Money Zapatos',
  updateed: dayjs('2022-08-09T14:39'),
};

export const sampleWithFullData: IGameClass = {
  id: 30565,
  version: 95687,
  name: 'CSS neutral',
  revision: 'Interacciones',
  launcherPath: 'Gorro Lugar Zapatos',
  gamePath: 'Liberia vía Tunez',
  docsPath: 'Dinánmico Rampa Naira',
  propsNames: 'invoice Savings Baleares',
  updateed: dayjs('2022-08-09T07:19'),
};

export const sampleWithNewData: NewGameClass = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
