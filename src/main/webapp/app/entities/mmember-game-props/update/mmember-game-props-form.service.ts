import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMmemberGameProps, NewMmemberGameProps } from '../mmember-game-props.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMmemberGameProps for edit and NewMmemberGamePropsFormGroupInput for create.
 */
type MmemberGamePropsFormGroupInput = IMmemberGameProps | PartialWithRequiredKeyOf<NewMmemberGameProps>;

type MmemberGamePropsFormDefaults = Pick<NewMmemberGameProps, 'id'>;

type MmemberGamePropsFormGroupContent = {
  id: FormControl<IMmemberGameProps['id'] | NewMmemberGameProps['id']>;
  version: FormControl<IMmemberGameProps['version']>;
  seed: FormControl<IMmemberGameProps['seed']>;
  mapName: FormControl<IMmemberGameProps['mapName']>;
  mapSize: FormControl<IMmemberGameProps['mapSize']>;
  npcCount: FormControl<IMmemberGameProps['npcCount']>;
  jsonProps: FormControl<IMmemberGameProps['jsonProps']>;
  configName: FormControl<IMmemberGameProps['configName']>;
  mmember: FormControl<IMmemberGameProps['mmember']>;
  gameClass: FormControl<IMmemberGameProps['gameClass']>;
};

export type MmemberGamePropsFormGroup = FormGroup<MmemberGamePropsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MmemberGamePropsFormService {
  createMmemberGamePropsFormGroup(mmemberGameProps: MmemberGamePropsFormGroupInput = { id: null }): MmemberGamePropsFormGroup {
    const mmemberGamePropsRawValue = {
      ...this.getFormDefaults(),
      ...mmemberGameProps,
    };
    return new FormGroup<MmemberGamePropsFormGroupContent>({
      id: new FormControl(
        { value: mmemberGamePropsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      version: new FormControl(mmemberGamePropsRawValue.version),
      seed: new FormControl(mmemberGamePropsRawValue.seed),
      mapName: new FormControl(mmemberGamePropsRawValue.mapName),
      mapSize: new FormControl(mmemberGamePropsRawValue.mapSize),
      npcCount: new FormControl(mmemberGamePropsRawValue.npcCount),
      jsonProps: new FormControl(mmemberGamePropsRawValue.jsonProps),
      configName: new FormControl(mmemberGamePropsRawValue.configName),
      mmember: new FormControl(mmemberGamePropsRawValue.mmember),
      gameClass: new FormControl(mmemberGamePropsRawValue.gameClass),
    });
  }

  getMmemberGameProps(form: MmemberGamePropsFormGroup): IMmemberGameProps | NewMmemberGameProps {
    return form.getRawValue() as IMmemberGameProps | NewMmemberGameProps;
  }

  resetForm(form: MmemberGamePropsFormGroup, mmemberGameProps: MmemberGamePropsFormGroupInput): void {
    const mmemberGamePropsRawValue = { ...this.getFormDefaults(), ...mmemberGameProps };
    form.reset(
      {
        ...mmemberGamePropsRawValue,
        id: { value: mmemberGamePropsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MmemberGamePropsFormDefaults {
    return {
      id: null,
    };
  }
}
