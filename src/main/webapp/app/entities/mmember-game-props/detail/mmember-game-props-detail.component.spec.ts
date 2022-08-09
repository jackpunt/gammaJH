import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MmemberGamePropsDetailComponent } from './mmember-game-props-detail.component';

describe('MmemberGameProps Management Detail Component', () => {
  let comp: MmemberGamePropsDetailComponent;
  let fixture: ComponentFixture<MmemberGamePropsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MmemberGamePropsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mmemberGameProps: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MmemberGamePropsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MmemberGamePropsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mmemberGameProps on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mmemberGameProps).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
