import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { RoleGroupService } from '../service/role-group.service';

import { RoleGroupComponent } from './role-group.component';

describe('RoleGroup Management Component', () => {
  let comp: RoleGroupComponent;
  let fixture: ComponentFixture<RoleGroupComponent>;
  let service: RoleGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'role-group', component: RoleGroupComponent }]), HttpClientTestingModule],
      declarations: [RoleGroupComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(RoleGroupComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RoleGroupComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RoleGroupService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.roleGroups?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to roleGroupService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getRoleGroupIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getRoleGroupIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
