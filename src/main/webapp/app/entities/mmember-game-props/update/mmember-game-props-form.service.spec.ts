import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../mmember-game-props.test-samples';

import { MmemberGamePropsFormService } from './mmember-game-props-form.service';

describe('MmemberGameProps Form Service', () => {
  let service: MmemberGamePropsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MmemberGamePropsFormService);
  });

  describe('Service methods', () => {
    describe('createMmemberGamePropsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMmemberGamePropsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            version: expect.any(Object),
            seed: expect.any(Object),
            mapName: expect.any(Object),
            mapSize: expect.any(Object),
            npcCount: expect.any(Object),
            jsonProps: expect.any(Object),
            configName: expect.any(Object),
            mmember: expect.any(Object),
            gameClass: expect.any(Object),
          })
        );
      });

      it('passing IMmemberGameProps should create a new form with FormGroup', () => {
        const formGroup = service.createMmemberGamePropsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            version: expect.any(Object),
            seed: expect.any(Object),
            mapName: expect.any(Object),
            mapSize: expect.any(Object),
            npcCount: expect.any(Object),
            jsonProps: expect.any(Object),
            configName: expect.any(Object),
            mmember: expect.any(Object),
            gameClass: expect.any(Object),
          })
        );
      });
    });

    describe('getMmemberGameProps', () => {
      it('should return NewMmemberGameProps for default MmemberGameProps initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMmemberGamePropsFormGroup(sampleWithNewData);

        const mmemberGameProps = service.getMmemberGameProps(formGroup) as any;

        expect(mmemberGameProps).toMatchObject(sampleWithNewData);
      });

      it('should return NewMmemberGameProps for empty MmemberGameProps initial value', () => {
        const formGroup = service.createMmemberGamePropsFormGroup();

        const mmemberGameProps = service.getMmemberGameProps(formGroup) as any;

        expect(mmemberGameProps).toMatchObject({});
      });

      it('should return IMmemberGameProps', () => {
        const formGroup = service.createMmemberGamePropsFormGroup(sampleWithRequiredData);

        const mmemberGameProps = service.getMmemberGameProps(formGroup) as any;

        expect(mmemberGameProps).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMmemberGameProps should not enable id FormControl', () => {
        const formGroup = service.createMmemberGamePropsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMmemberGameProps should disable id FormControl', () => {
        const formGroup = service.createMmemberGamePropsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
