import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGameClass, NewGameClass } from '../game-class.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGameClass for edit and NewGameClassFormGroupInput for create.
 */
type GameClassFormGroupInput = IGameClass | PartialWithRequiredKeyOf<NewGameClass>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IGameClass | NewGameClass> = Omit<T, 'updated'> & {
  updated?: string | null;
};

type GameClassFormRawValue = FormValueOf<IGameClass>;

type NewGameClassFormRawValue = FormValueOf<NewGameClass>;

type GameClassFormDefaults = Pick<NewGameClass, 'id' | 'updated'>;

type GameClassFormGroupContent = {
  id: FormControl<GameClassFormRawValue['id'] | NewGameClass['id']>;
  version: FormControl<GameClassFormRawValue['version']>;
  name: FormControl<GameClassFormRawValue['name']>;
  revision: FormControl<GameClassFormRawValue['revision']>;
  launcherPath: FormControl<GameClassFormRawValue['launcherPath']>;
  gamePath: FormControl<GameClassFormRawValue['gamePath']>;
  docsPath: FormControl<GameClassFormRawValue['docsPath']>;
  propsNames: FormControl<GameClassFormRawValue['propsNames']>;
  updated: FormControl<GameClassFormRawValue['updated']>;
};

export type GameClassFormGroup = FormGroup<GameClassFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GameClassFormService {
  createGameClassFormGroup(gameClass: GameClassFormGroupInput = { id: null }): GameClassFormGroup {
    const gameClassRawValue = this.convertGameClassToGameClassRawValue({
      ...this.getFormDefaults(),
      ...gameClass,
    });
    return new FormGroup<GameClassFormGroupContent>({
      id: new FormControl(
        { value: gameClassRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      version: new FormControl(gameClassRawValue.version),
      name: new FormControl(gameClassRawValue.name),
      revision: new FormControl(gameClassRawValue.revision),
      launcherPath: new FormControl(gameClassRawValue.launcherPath),
      gamePath: new FormControl(gameClassRawValue.gamePath),
      docsPath: new FormControl(gameClassRawValue.docsPath),
      propsNames: new FormControl(gameClassRawValue.propsNames),
      updated: new FormControl(gameClassRawValue.updated),
    });
  }

  getGameClass(form: GameClassFormGroup): IGameClass | NewGameClass {
    return this.convertGameClassRawValueToGameClass(form.getRawValue() as GameClassFormRawValue | NewGameClassFormRawValue);
  }

  resetForm(form: GameClassFormGroup, gameClass: GameClassFormGroupInput): void {
    const gameClassRawValue = this.convertGameClassToGameClassRawValue({ ...this.getFormDefaults(), ...gameClass });
    form.reset(
      {
        ...gameClassRawValue,
        id: { value: gameClassRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): GameClassFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      updated: currentTime,
    };
  }

  private convertGameClassRawValueToGameClass(rawGameClass: GameClassFormRawValue | NewGameClassFormRawValue): IGameClass | NewGameClass {
    return {
      ...rawGameClass,
      updated: dayjs(rawGameClass.updated, DATE_TIME_FORMAT),
    };
  }

  private convertGameClassToGameClassRawValue(
    gameClass: IGameClass | (Partial<NewGameClass> & GameClassFormDefaults)
  ): GameClassFormRawValue | PartialWithRequiredKeyOf<NewGameClassFormRawValue> {
    return {
      ...gameClass,
      updated: gameClass.updated ? gameClass.updated.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}