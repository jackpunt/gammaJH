import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MmemberService } from '../service/mmember.service';

import { MmemberComponent } from './mmember.component';

describe('Mmember Management Component', () => {
  let comp: MmemberComponent;
  let fixture: ComponentFixture<MmemberComponent>;
  let service: MmemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'mmember', component: MmemberComponent }]), HttpClientTestingModule],
      declarations: [MmemberComponent],
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
      .overrideTemplate(MmemberComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MmemberComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MmemberService);

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
    expect(comp.mmembers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to mmemberService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMmemberIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMmemberIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
