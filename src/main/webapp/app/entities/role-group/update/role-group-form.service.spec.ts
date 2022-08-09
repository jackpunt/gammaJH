import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../role-group.test-samples';

import { RoleGroupFormService } from './role-group-form.service';

describe('RoleGroup Form Service', () => {
  let service: RoleGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoleGroupFormService);
  });

  describe('Service methods', () => {
    describe('createRoleGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRoleGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            version: expect.any(Object),
            groupName: expect.any(Object),
          })
        );
      });

      it('passing IRoleGroup should create a new form with FormGroup', () => {
        const formGroup = service.createRoleGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            version: expect.any(Object),
            groupName: expect.any(Object),
          })
        );
      });
    });

    describe('getRoleGroup', () => {
      it('should return NewRoleGroup for default RoleGroup initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRoleGroupFormGroup(sampleWithNewData);

        const roleGroup = service.getRoleGroup(formGroup) as any;

        expect(roleGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewRoleGroup for empty RoleGroup initial value', () => {
        const formGroup = service.createRoleGroupFormGroup();

        const roleGroup = service.getRoleGroup(formGroup) as any;

        expect(roleGroup).toMatchObject({});
      });

      it('should return IRoleGroup', () => {
        const formGroup = service.createRoleGroupFormGroup(sampleWithRequiredData);

        const roleGroup = service.getRoleGroup(formGroup) as any;

        expect(roleGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRoleGroup should not enable id FormControl', () => {
        const formGroup = service.createRoleGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRoleGroup should disable id FormControl', () => {
        const formGroup = service.createRoleGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
