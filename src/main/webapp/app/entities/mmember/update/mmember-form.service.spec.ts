import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../mmember.test-samples';

import { MmemberFormService } from './mmember-form.service';

describe('Mmember Form Service', () => {
  let service: MmemberFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MmemberFormService);
  });

  describe('Service methods', () => {
    describe('createMmemberFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMmemberFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            version: expect.any(Object),
            roleGroup: expect.any(Object),
          })
        );
      });

      it('passing IMmember should create a new form with FormGroup', () => {
        const formGroup = service.createMmemberFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            version: expect.any(Object),
            roleGroup: expect.any(Object),
          })
        );
      });
    });

    describe('getMmember', () => {
      it('should return NewMmember for default Mmember initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMmemberFormGroup(sampleWithNewData);

        const mmember = service.getMmember(formGroup) as any;

        expect(mmember).toMatchObject(sampleWithNewData);
      });

      it('should return NewMmember for empty Mmember initial value', () => {
        const formGroup = service.createMmemberFormGroup();

        const mmember = service.getMmember(formGroup) as any;

        expect(mmember).toMatchObject({});
      });

      it('should return IMmember', () => {
        const formGroup = service.createMmemberFormGroup(sampleWithRequiredData);

        const mmember = service.getMmember(formGroup) as any;

        expect(mmember).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMmember should not enable id FormControl', () => {
        const formGroup = service.createMmemberFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMmember should disable id FormControl', () => {
        const formGroup = service.createMmemberFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
