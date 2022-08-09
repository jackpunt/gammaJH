import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MmemberFormService } from './mmember-form.service';
import { MmemberService } from '../service/mmember.service';
import { IMmember } from '../mmember.model';
import { IRoleGroup } from 'app/entities/role-group/role-group.model';
import { RoleGroupService } from 'app/entities/role-group/service/role-group.service';

import { MmemberUpdateComponent } from './mmember-update.component';

describe('Mmember Management Update Component', () => {
  let comp: MmemberUpdateComponent;
  let fixture: ComponentFixture<MmemberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mmemberFormService: MmemberFormService;
  let mmemberService: MmemberService;
  let roleGroupService: RoleGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MmemberUpdateComponent],
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
      .overrideTemplate(MmemberUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MmemberUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mmemberFormService = TestBed.inject(MmemberFormService);
    mmemberService = TestBed.inject(MmemberService);
    roleGroupService = TestBed.inject(RoleGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RoleGroup query and add missing value', () => {
      const mmember: IMmember = { id: 456 };
      const roleGroup: IRoleGroup = { id: 53372 };
      mmember.roleGroup = roleGroup;

      const roleGroupCollection: IRoleGroup[] = [{ id: 48798 }];
      jest.spyOn(roleGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: roleGroupCollection })));
      const additionalRoleGroups = [roleGroup];
      const expectedCollection: IRoleGroup[] = [...additionalRoleGroups, ...roleGroupCollection];
      jest.spyOn(roleGroupService, 'addRoleGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mmember });
      comp.ngOnInit();

      expect(roleGroupService.query).toHaveBeenCalled();
      expect(roleGroupService.addRoleGroupToCollectionIfMissing).toHaveBeenCalledWith(
        roleGroupCollection,
        ...additionalRoleGroups.map(expect.objectContaining)
      );
      expect(comp.roleGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mmember: IMmember = { id: 456 };
      const roleGroup: IRoleGroup = { id: 18930 };
      mmember.roleGroup = roleGroup;

      activatedRoute.data = of({ mmember });
      comp.ngOnInit();

      expect(comp.roleGroupsSharedCollection).toContain(roleGroup);
      expect(comp.mmember).toEqual(mmember);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMmember>>();
      const mmember = { id: 123 };
      jest.spyOn(mmemberFormService, 'getMmember').mockReturnValue(mmember);
      jest.spyOn(mmemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mmember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mmember }));
      saveSubject.complete();

      // THEN
      expect(mmemberFormService.getMmember).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mmemberService.update).toHaveBeenCalledWith(expect.objectContaining(mmember));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMmember>>();
      const mmember = { id: 123 };
      jest.spyOn(mmemberFormService, 'getMmember').mockReturnValue({ id: null });
      jest.spyOn(mmemberService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mmember: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mmember }));
      saveSubject.complete();

      // THEN
      expect(mmemberFormService.getMmember).toHaveBeenCalled();
      expect(mmemberService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMmember>>();
      const mmember = { id: 123 };
      jest.spyOn(mmemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mmember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mmemberService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRoleGroup', () => {
      it('Should forward to roleGroupService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(roleGroupService, 'compareRoleGroup');
        comp.compareRoleGroup(entity, entity2);
        expect(roleGroupService.compareRoleGroup).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
