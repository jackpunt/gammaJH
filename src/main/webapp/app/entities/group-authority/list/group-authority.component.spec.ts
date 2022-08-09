import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { GroupAuthorityService } from '../service/group-authority.service';

import { GroupAuthorityComponent } from './group-authority.component';

describe('GroupAuthority Management Component', () => {
  let comp: GroupAuthorityComponent;
  let fixture: ComponentFixture<GroupAuthorityComponent>;
  let service: GroupAuthorityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'group-authority', component: GroupAuthorityComponent }]), HttpClientTestingModule],
      declarations: [GroupAuthorityComponent],
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
      .overrideTemplate(GroupAuthorityComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GroupAuthorityComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(GroupAuthorityService);

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
    expect(comp.groupAuthorities?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to groupAuthorityService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getGroupAuthorityIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getGroupAuthorityIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
