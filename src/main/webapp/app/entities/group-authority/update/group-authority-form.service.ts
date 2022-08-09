import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGroupAuthority, NewGroupAuthority } from '../group-authority.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGroupAuthority for edit and NewGroupAuthorityFormGroupInput for create.
 */
type GroupAuthorityFormGroupInput = IGroupAuthority | PartialWithRequiredKeyOf<NewGroupAuthority>;

type GroupAuthorityFormDefaults = Pick<NewGroupAuthority, 'id'>;

type GroupAuthorityFormGroupContent = {
  id: FormControl<IGroupAuthority['id'] | NewGroupAuthority['id']>;
  version: FormControl<IGroupAuthority['version']>;
  authority: FormControl<IGroupAuthority['authority']>;
  roleGroup: FormControl<IGroupAuthority['roleGroup']>;
};

export type GroupAuthorityFormGroup = FormGroup<GroupAuthorityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GroupAuthorityFormService {
  createGroupAuthorityFormGroup(groupAuthority: GroupAuthorityFormGroupInput = { id: null }): GroupAuthorityFormGroup {
    const groupAuthorityRawValue = {
      ...this.getFormDefaults(),
      ...groupAuthority,
    };
    return new FormGroup<GroupAuthorityFormGroupContent>({
      id: new FormControl(
        { value: groupAuthorityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      version: new FormControl(groupAuthorityRawValue.version),
      authority: new FormControl(groupAuthorityRawValue.authority),
      roleGroup: new FormControl(groupAuthorityRawValue.roleGroup),
    });
  }

  getGroupAuthority(form: GroupAuthorityFormGroup): IGroupAuthority | NewGroupAuthority {
    return form.getRawValue() as IGroupAuthority | NewGroupAuthority;
  }

  resetForm(form: GroupAuthorityFormGroup, groupAuthority: GroupAuthorityFormGroupInput): void {
    const groupAuthorityRawValue = { ...this.getFormDefaults(), ...groupAuthority };
    form.reset(
      {
        ...groupAuthorityRawValue,
        id: { value: groupAuthorityRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): GroupAuthorityFormDefaults {
    return {
      id: null,
    };
  }
}
