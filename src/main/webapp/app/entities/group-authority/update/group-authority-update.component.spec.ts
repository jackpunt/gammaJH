import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GroupAuthorityFormService } from './group-authority-form.service';
import { GroupAuthorityService } from '../service/group-authority.service';
import { IGroupAuthority } from '../group-authority.model';
import { IRoleGroup } from 'app/entities/role-group/role-group.model';
import { RoleGroupService } from 'app/entities/role-group/service/role-group.service';

import { GroupAuthorityUpdateComponent } from './group-authority-update.component';

describe('GroupAuthority Management Update Component', () => {
  let comp: GroupAuthorityUpdateComponent;
  let fixture: ComponentFixture<GroupAuthorityUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let groupAuthorityFormService: GroupAuthorityFormService;
  let groupAuthorityService: GroupAuthorityService;
  let roleGroupService: RoleGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GroupAuthorityUpdateComponent],
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
      .overrideTemplate(GroupAuthorityUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GroupAuthorityUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    groupAuthorityFormService = TestBed.inject(GroupAuthorityFormService);
    groupAuthorityService = TestBed.inject(GroupAuthorityService);
    roleGroupService = TestBed.inject(RoleGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RoleGroup query and add missing value', () => {
      const groupAuthority: IGroupAuthority = { id: 456 };
      const roleGroup: IRoleGroup = { id: 77586 };
      groupAuthority.roleGroup = roleGroup;

      const roleGroupCollection: IRoleGroup[] = [{ id: 80743 }];
      jest.spyOn(roleGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: roleGroupCollection })));
      const additionalRoleGroups = [roleGroup];
      const expectedCollection: IRoleGroup[] = [...additionalRoleGroups, ...roleGroupCollection];
      jest.spyOn(roleGroupService, 'addRoleGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ groupAuthority });
      comp.ngOnInit();

      expect(roleGroupService.query).toHaveBeenCalled();
      expect(roleGroupService.addRoleGroupToCollectionIfMissing).toHaveBeenCalledWith(
        roleGroupCollection,
        ...additionalRoleGroups.map(expect.objectContaining)
      );
      expect(comp.roleGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const groupAuthority: IGroupAuthority = { id: 456 };
      const roleGroup: IRoleGroup = { id: 31882 };
      groupAuthority.roleGroup = roleGroup;

      activatedRoute.data = of({ groupAuthority });
      comp.ngOnInit();

      expect(comp.roleGroupsSharedCollection).toContain(roleGroup);
      expect(comp.groupAuthority).toEqual(groupAuthority);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGroupAuthority>>();
      const groupAuthority = { id: 123 };
      jest.spyOn(groupAuthorityFormService, 'getGroupAuthority').mockReturnValue(groupAuthority);
      jest.spyOn(groupAuthorityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groupAuthority });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: groupAuthority }));
      saveSubject.complete();

      // THEN
      expect(groupAuthorityFormService.getGroupAuthority).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(groupAuthorityService.update).toHaveBeenCalledWith(expect.objectContaining(groupAuthority));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGroupAuthority>>();
      const groupAuthority = { id: 123 };
      jest.spyOn(groupAuthorityFormService, 'getGroupAuthority').mockReturnValue({ id: null });
      jest.spyOn(groupAuthorityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groupAuthority: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: groupAuthority }));
      saveSubject.complete();

      // THEN
      expect(groupAuthorityFormService.getGroupAuthority).toHaveBeenCalled();
      expect(groupAuthorityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGroupAuthority>>();
      const groupAuthority = { id: 123 };
      jest.spyOn(groupAuthorityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groupAuthority });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(groupAuthorityService.update).toHaveBeenCalled();
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
