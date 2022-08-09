import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MmemberDetailComponent } from './mmember-detail.component';

describe('Mmember Management Detail Component', () => {
  let comp: MmemberDetailComponent;
  let fixture: ComponentFixture<MmemberDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MmemberDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mmember: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MmemberDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MmemberDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mmember on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mmember).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
