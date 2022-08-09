jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { MmemberGamePropsService } from '../service/mmember-game-props.service';

import { MmemberGamePropsDeleteDialogComponent } from './mmember-game-props-delete-dialog.component';

describe('MmemberGameProps Management Delete Component', () => {
  let comp: MmemberGamePropsDeleteDialogComponent;
  let fixture: ComponentFixture<MmemberGamePropsDeleteDialogComponent>;
  let service: MmemberGamePropsService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MmemberGamePropsDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(MmemberGamePropsDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MmemberGamePropsDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MmemberGamePropsService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
