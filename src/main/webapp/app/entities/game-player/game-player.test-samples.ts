import { IGamePlayer, NewGamePlayer } from './game-player.model';

export const sampleWithRequiredData: IGamePlayer = {
  id: 8447,
};

export const sampleWithPartialData: IGamePlayer = {
  id: 23161,
  version: 96413,
};

export const sampleWithFullData: IGamePlayer = {
  id: 91942,
  version: 26709,
  role: 'Seguridada Región',
  ready: false,
};

export const sampleWithNewData: NewGamePlayer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
