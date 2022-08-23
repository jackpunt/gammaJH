import { IGamePlayer, NewGamePlayer } from './game-player.model';

export const sampleWithRequiredData: IGamePlayer = {
  id: 8447,
  role: 'purp',
  ready: false,
};

export const sampleWithPartialData: IGamePlayer = {
  id: 95468,
  version: 12651,
  role: 'Avon',
  ready: true,
};

export const sampleWithFullData: IGamePlayer = {
  id: 22604,
  version: 34380,
  role: 'Toys',
  ready: false,
};

export const sampleWithNewData: NewGamePlayer = {
  role: 'Dyna',
  ready: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
