import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MmemberGamePropsFormService } from './mmember-game-props-form.service';
import { MmemberGamePropsService } from '../service/mmember-game-props.service';
import { IMmemberGameProps } from '../mmember-game-props.model';
import { IMmember } from 'app/entities/mmember/mmember.model';
import { MmemberService } from 'app/entities/mmember/service/mmember.service';
import { IGameClass } from 'app/entities/game-class/game-class.model';
import { GameClassService } from 'app/entities/game-class/service/game-class.service';

import { MmemberGamePropsUpdateComponent } from './mmember-game-props-update.component';

describe('MmemberGameProps Management Update Component', () => {
  let comp: MmemberGamePropsUpdateComponent;
  let fixture: ComponentFixture<MmemberGamePropsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mmemberGamePropsFormService: MmemberGamePropsFormService;
  let mmemberGamePropsService: MmemberGamePropsService;
  let mmemberService: MmemberService;
  let gameClassService: GameClassService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MmemberGamePropsUpdateComponent],
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
      .overrideTemplate(MmemberGamePropsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MmemberGamePropsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mmemberGamePropsFormService = TestBed.inject(MmemberGamePropsFormService);
    mmemberGamePropsService = TestBed.inject(MmemberGamePropsService);
    mmemberService = TestBed.inject(MmemberService);
    gameClassService = TestBed.inject(GameClassService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Mmember query and add missing value', () => {
      const mmemberGameProps: IMmemberGameProps = { id: 456 };
      const mmember: IMmember = { id: 5302 };
      mmemberGameProps.mmember = mmember;

      const mmemberCollection: IMmember[] = [{ id: 9262 }];
      jest.spyOn(mmemberService, 'query').mockReturnValue(of(new HttpResponse({ body: mmemberCollection })));
      const additionalMmembers = [mmember];
      const expectedCollection: IMmember[] = [...additionalMmembers, ...mmemberCollection];
      jest.spyOn(mmemberService, 'addMmemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mmemberGameProps });
      comp.ngOnInit();

      expect(mmemberService.query).toHaveBeenCalled();
      expect(mmemberService.addMmemberToCollectionIfMissing).toHaveBeenCalledWith(
        mmemberCollection,
        ...additionalMmembers.map(expect.objectContaining)
      );
      expect(comp.mmembersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GameClass query and add missing value', () => {
      const mmemberGameProps: IMmemberGameProps = { id: 456 };
      const gameClass: IGameClass = { id: 47826 };
      mmemberGameProps.gameClass = gameClass;

      const gameClassCollection: IGameClass[] = [{ id: 35447 }];
      jest.spyOn(gameClassService, 'query').mockReturnValue(of(new HttpResponse({ body: gameClassCollection })));
      const additionalGameClasses = [gameClass];
      const expectedCollection: IGameClass[] = [...additionalGameClasses, ...gameClassCollection];
      jest.spyOn(gameClassService, 'addGameClassToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mmemberGameProps });
      comp.ngOnInit();

      expect(gameClassService.query).toHaveBeenCalled();
      expect(gameClassService.addGameClassToCollectionIfMissing).toHaveBeenCalledWith(
        gameClassCollection,
        ...additionalGameClasses.map(expect.objectContaining)
      );
      expect(comp.gameClassesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mmemberGameProps: IMmemberGameProps = { id: 456 };
      const mmember: IMmember = { id: 24679 };
      mmemberGameProps.mmember = mmember;
      const gameClass: IGameClass = { id: 53344 };
      mmemberGameProps.gameClass = gameClass;

      activatedRoute.data = of({ mmemberGameProps });
      comp.ngOnInit();

      expect(comp.mmembersSharedCollection).toContain(mmember);
      expect(comp.gameClassesSharedCollection).toContain(gameClass);
      expect(comp.mmemberGameProps).toEqual(mmemberGameProps);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMmemberGameProps>>();
      const mmemberGameProps = { id: 123 };
      jest.spyOn(mmemberGamePropsFormService, 'getMmemberGameProps').mockReturnValue(mmemberGameProps);
      jest.spyOn(mmemberGamePropsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mmemberGameProps });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mmemberGameProps }));
      saveSubject.complete();

      // THEN
      expect(mmemberGamePropsFormService.getMmemberGameProps).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mmemberGamePropsService.update).toHaveBeenCalledWith(expect.objectContaining(mmemberGameProps));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMmemberGameProps>>();
      const mmemberGameProps = { id: 123 };
      jest.spyOn(mmemberGamePropsFormService, 'getMmemberGameProps').mockReturnValue({ id: null });
      jest.spyOn(mmemberGamePropsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mmemberGameProps: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mmemberGameProps }));
      saveSubject.complete();

      // THEN
      expect(mmemberGamePropsFormService.getMmemberGameProps).toHaveBeenCalled();
      expect(mmemberGamePropsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMmemberGameProps>>();
      const mmemberGameProps = { id: 123 };
      jest.spyOn(mmemberGamePropsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mmemberGameProps });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mmemberGamePropsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMmember', () => {
      it('Should forward to mmemberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(mmemberService, 'compareMmember');
        comp.compareMmember(entity, entity2);
        expect(mmemberService.compareMmember).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGameClass', () => {
      it('Should forward to gameClassService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(gameClassService, 'compareGameClass');
        comp.compareGameClass(entity, entity2);
        expect(gameClassService.compareGameClass).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
