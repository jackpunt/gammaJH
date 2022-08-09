import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RoleGroupFormService } from './role-group-form.service';
import { RoleGroupService } from '../service/role-group.service';
import { IRoleGroup } from '../role-group.model';

import { RoleGroupUpdateComponent } from './role-group-update.component';

describe('RoleGroup Management Update Component', () => {
  let comp: RoleGroupUpdateComponent;
  let fixture: ComponentFixture<RoleGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let roleGroupFormService: RoleGroupFormService;
  let roleGroupService: RoleGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RoleGroupUpdateComponent],
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
      .overrideTemplate(RoleGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RoleGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    roleGroupFormService = TestBed.inject(RoleGroupFormService);
    roleGroupService = TestBed.inject(RoleGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const roleGroup: IRoleGroup = { id: 456 };

      activatedRoute.data = of({ roleGroup });
      comp.ngOnInit();

      expect(comp.roleGroup).toEqual(roleGroup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoleGroup>>();
      const roleGroup = { id: 123 };
      jest.spyOn(roleGroupFormService, 'getRoleGroup').mockReturnValue(roleGroup);
      jest.spyOn(roleGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: roleGroup }));
      saveSubject.complete();

      // THEN
      expect(roleGroupFormService.getRoleGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(roleGroupService.update).toHaveBeenCalledWith(expect.objectContaining(roleGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoleGroup>>();
      const roleGroup = { id: 123 };
      jest.spyOn(roleGroupFormService, 'getRoleGroup').mockReturnValue({ id: null });
      jest.spyOn(roleGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: roleGroup }));
      saveSubject.complete();

      // THEN
      expect(roleGroupFormService.getRoleGroup).toHaveBeenCalled();
      expect(roleGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoleGroup>>();
      const roleGroup = { id: 123 };
      jest.spyOn(roleGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(roleGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
