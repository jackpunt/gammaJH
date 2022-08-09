import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RoleGroupDetailComponent } from './role-group-detail.component';

describe('RoleGroup Management Detail Component', () => {
  let comp: RoleGroupDetailComponent;
  let fixture: ComponentFixture<RoleGroupDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RoleGroupDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ roleGroup: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RoleGroupDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RoleGroupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load roleGroup on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.roleGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
