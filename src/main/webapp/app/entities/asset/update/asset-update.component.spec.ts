import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AssetFormService } from './asset-form.service';
import { AssetService } from '../service/asset.service';
import { IAsset } from '../asset.model';
import { IMmember } from 'app/entities/mmember/mmember.model';
import { MmemberService } from 'app/entities/mmember/service/mmember.service';

import { AssetUpdateComponent } from './asset-update.component';

describe('Asset Management Update Component', () => {
  let comp: AssetUpdateComponent;
  let fixture: ComponentFixture<AssetUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assetFormService: AssetFormService;
  let assetService: AssetService;
  let mmemberService: MmemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AssetUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AssetUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssetUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assetFormService = TestBed.inject(AssetFormService);
    assetService = TestBed.inject(AssetService);
    mmemberService = TestBed.inject(MmemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Mmember query and add missing value', () => {
      const asset: IAsset = { id: 456 };
      const mmember: IMmember = { id: 7577 };
      asset.mmember = mmember;

      const mmemberCollection: IMmember[] = [{ id: 96857 }];
      jest.spyOn(mmemberService, 'query').mockReturnValue(of(new HttpResponse({ body: mmemberCollection })));
      const additionalMmembers = [mmember];
      const expectedCollection: IMmember[] = [...additionalMmembers, ...mmemberCollection];
      jest.spyOn(mmemberService, 'addMmemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ asset });
      comp.ngOnInit();

      expect(mmemberService.query).toHaveBeenCalled();
      expect(mmemberService.addMmemberToCollectionIfMissing).toHaveBeenCalledWith(
        mmemberCollection,
        ...additionalMmembers.map(expect.objectContaining)
      );
      expect(comp.mmembersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const asset: IAsset = { id: 456 };
      const mmember: IMmember = { id: 31532 };
      asset.mmember = mmember;

      activatedRoute.data = of({ asset });
      comp.ngOnInit();

      expect(comp.mmembersSharedCollection).toContain(mmember);
      expect(comp.asset).toEqual(asset);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsset>>();
      const asset = { id: 123 };
      jest.spyOn(assetFormService, 'getAsset').mockReturnValue(asset);
      jest.spyOn(assetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asset });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: asset }));
      saveSubject.complete();

      // THEN
      expect(assetFormService.getAsset).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(assetService.update).toHaveBeenCalledWith(expect.objectContaining(asset));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsset>>();
      const asset = { id: 123 };
      jest.spyOn(assetFormService, 'getAsset').mockReturnValue({ id: null });
      jest.spyOn(assetService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asset: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: asset }));
      saveSubject.complete();

      // THEN
      expect(assetFormService.getAsset).toHaveBeenCalled();
      expect(assetService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsset>>();
      const asset = { id: 123 };
      jest.spyOn(assetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asset });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assetService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMmember', () => {
      it('Should forward to mmemberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(mmemberService, 'compareMmember');
        comp.compareMmember(entity, entity2);
        expect(mmemberService.compareMmember).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
