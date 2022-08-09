import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MmemberGamePropsFormService, MmemberGamePropsFormGroup } from './mmember-game-props-form.service';
import { IMmemberGameProps } from '../mmember-game-props.model';
import { MmemberGamePropsService } from '../service/mmember-game-props.service';
import { IMmember } from 'app/entities/mmember/mmember.model';
import { MmemberService } from 'app/entities/mmember/service/mmember.service';
import { IGameClass } from 'app/entities/game-class/game-class.model';
import { GameClassService } from 'app/entities/game-class/service/game-class.service';

@Component({
  selector: 'jhi-mmember-game-props-update',
  templateUrl: './mmember-game-props-update.component.html',
})
export class MmemberGamePropsUpdateComponent implements OnInit {
  isSaving = false;
  mmemberGameProps: IMmemberGameProps | null = null;

  mmembersSharedCollection: IMmember[] = [];
  gameClassesSharedCollection: IGameClass[] = [];

  editForm: MmemberGamePropsFormGroup = this.mmemberGamePropsFormService.createMmemberGamePropsFormGroup();

  constructor(
    protected mmemberGamePropsService: MmemberGamePropsService,
    protected mmemberGamePropsFormService: MmemberGamePropsFormService,
    protected mmemberService: MmemberService,
    protected gameClassService: GameClassService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMmember = (o1: IMmember | null, o2: IMmember | null): boolean => this.mmemberService.compareMmember(o1, o2);

  compareGameClass = (o1: IGameClass | null, o2: IGameClass | null): boolean => this.gameClassService.compareGameClass(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mmemberGameProps }) => {
      this.mmemberGameProps = mmemberGameProps;
      if (mmemberGameProps) {
        this.updateForm(mmemberGameProps);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mmemberGameProps = this.mmemberGamePropsFormService.getMmemberGameProps(this.editForm);
    if (mmemberGameProps.id !== null) {
      this.subscribeToSaveResponse(this.mmemberGamePropsService.update(mmemberGameProps));
    } else {
      this.subscribeToSaveResponse(this.mmemberGamePropsService.create(mmemberGameProps));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMmemberGameProps>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(mmemberGameProps: IMmemberGameProps): void {
    this.mmemberGameProps = mmemberGameProps;
    this.mmemberGamePropsFormService.resetForm(this.editForm, mmemberGameProps);

    this.mmembersSharedCollection = this.mmemberService.addMmemberToCollectionIfMissing<IMmember>(
      this.mmembersSharedCollection,
      mmemberGameProps.mmember
    );
    this.gameClassesSharedCollection = this.gameClassService.addGameClassToCollectionIfMissing<IGameClass>(
      this.gameClassesSharedCollection,
      mmemberGameProps.gameClass
    );
  }

  protected loadRelationshipsOptions(): void {
    this.mmemberService
      .query()
      .pipe(map((res: HttpResponse<IMmember[]>) => res.body ?? []))
      .pipe(
        map((mmembers: IMmember[]) =>
          this.mmemberService.addMmemberToCollectionIfMissing<IMmember>(mmembers, this.mmemberGameProps?.mmember)
        )
      )
      .subscribe((mmembers: IMmember[]) => (this.mmembersSharedCollection = mmembers));

    this.gameClassService
      .query()
      .pipe(map((res: HttpResponse<IGameClass[]>) => res.body ?? []))
      .pipe(
        map((gameClasses: IGameClass[]) =>
          this.gameClassService.addGameClassToCollectionIfMissing<IGameClass>(gameClasses, this.mmemberGameProps?.gameClass)
        )
      )
      .subscribe((gameClasses: IGameClass[]) => (this.gameClassesSharedCollection = gameClasses));
  }
}
