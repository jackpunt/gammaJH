import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRoleGroup, NewRoleGroup } from '../role-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRoleGroup for edit and NewRoleGroupFormGroupInput for create.
 */
type RoleGroupFormGroupInput = IRoleGroup | PartialWithRequiredKeyOf<NewRoleGroup>;

type RoleGroupFormDefaults = Pick<NewRoleGroup, 'id'>;

type RoleGroupFormGroupContent = {
  id: FormControl<IRoleGroup['id'] | NewRoleGroup['id']>;
  version: FormControl<IRoleGroup['version']>;
  groupName: FormControl<IRoleGroup['groupName']>;
};

export type RoleGroupFormGroup = FormGroup<RoleGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RoleGroupFormService {
  createRoleGroupFormGroup(roleGroup: RoleGroupFormGroupInput = { id: null }): RoleGroupFormGroup {
    const roleGroupRawValue = {
      ...this.getFormDefaults(),
      ...roleGroup,
    };
    return new FormGroup<RoleGroupFormGroupContent>({
      id: new FormControl(
        { value: roleGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      version: new FormControl(roleGroupRawValue.version),
      groupName: new FormControl(roleGroupRawValue.groupName),
    });
  }

  getRoleGroup(form: RoleGroupFormGroup): IRoleGroup | NewRoleGroup {
    return form.getRawValue() as IRoleGroup | NewRoleGroup;
  }

  resetForm(form: RoleGroupFormGroup, roleGroup: RoleGroupFormGroupInput): void {
    const roleGroupRawValue = { ...this.getFormDefaults(), ...roleGroup };
    form.reset(
      {
        ...roleGroupRawValue,
        id: { value: roleGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RoleGroupFormDefaults {
    return {
      id: null,
    };
  }
}
