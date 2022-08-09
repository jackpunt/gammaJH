import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MmemberGamePropsService } from '../service/mmember-game-props.service';

import { MmemberGamePropsComponent } from './mmember-game-props.component';

describe('MmemberGameProps Management Component', () => {
  let comp: MmemberGamePropsComponent;
  let fixture: ComponentFixture<MmemberGamePropsComponent>;
  let service: MmemberGamePropsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'mmember-game-props', component: MmemberGamePropsComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [MmemberGamePropsComponent],
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
      .overrideTemplate(MmemberGamePropsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MmemberGamePropsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MmemberGamePropsService);

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
    expect(comp.mmemberGameProps?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to mmemberGamePropsService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMmemberGamePropsIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMmemberGamePropsIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
