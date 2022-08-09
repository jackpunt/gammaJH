import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMmember, NewMmember } from '../mmember.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMmember for edit and NewMmemberFormGroupInput for create.
 */
type MmemberFormGroupInput = IMmember | PartialWithRequiredKeyOf<NewMmember>;

type MmemberFormDefaults = Pick<NewMmember, 'id'>;

type MmemberFormGroupContent = {
  id: FormControl<IMmember['id'] | NewMmember['id']>;
  version: FormControl<IMmember['version']>;
  roleGroup: FormControl<IMmember['roleGroup']>;
};

export type MmemberFormGroup = FormGroup<MmemberFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MmemberFormService {
  createMmemberFormGroup(mmember: MmemberFormGroupInput = { id: null }): MmemberFormGroup {
    const mmemberRawValue = {
      ...this.getFormDefaults(),
      ...mmember,
    };
    return new FormGroup<MmemberFormGroupContent>({
      id: new FormControl(
        { value: mmemberRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      version: new FormControl(mmemberRawValue.version),
      roleGroup: new FormControl(mmemberRawValue.roleGroup),
    });
  }

  getMmember(form: MmemberFormGroup): IMmember | NewMmember {
    return form.getRawValue() as IMmember | NewMmember;
  }

  resetForm(form: MmemberFormGroup, mmember: MmemberFormGroupInput): void {
    const mmemberRawValue = { ...this.getFormDefaults(), ...mmember };
    form.reset(
      {
        ...mmemberRawValue,
        id: { value: mmemberRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MmemberFormDefaults {
    return {
      id: null,
    };
  }
}
