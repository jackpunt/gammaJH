import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GroupAuthorityDetailComponent } from './group-authority-detail.component';

describe('GroupAuthority Management Detail Component', () => {
  let comp: GroupAuthorityDetailComponent;
  let fixture: ComponentFixture<GroupAuthorityDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GroupAuthorityDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ groupAuthority: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GroupAuthorityDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GroupAuthorityDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load groupAuthority on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.groupAuthority).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
