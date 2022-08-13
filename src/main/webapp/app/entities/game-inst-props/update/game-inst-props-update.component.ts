import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { GameInstPropsFormService, GameInstPropsFormGroup } from './game-inst-props-form.service';
import { IGameInstProps } from '../game-inst-props.model';
import { GameInstPropsService } from '../service/game-inst-props.service';

@Component({
  selector: 'jhi-game-inst-props-update',
  templateUrl: './game-inst-props-update.component.html',
})
export class GameInstPropsUpdateComponent implements OnInit {
  isSaving = false;
  gameInstProps: IGameInstProps | null = null;

  editForm: GameInstPropsFormGroup = this.gameInstPropsFormService.createGameInstPropsFormGroup();

  constructor(
    protected gameInstPropsService: GameInstPropsService,
    protected gameInstPropsFormService: GameInstPropsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gameInstProps }) => {
      this.gameInstProps = gameInstProps;
      if (gameInstProps) {
        this.updateForm(gameInstProps);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gameInstProps = this.gameInstPropsFormService.getGameInstProps(this.editForm);
    if (gameInstProps.id !== null) {
      this.subscribeToSaveResponse(this.gameInstPropsService.update(gameInstProps));
    } else {
      this.subscribeToSaveResponse(this.gameInstPropsService.create(gameInstProps));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGameInstProps>>): void {
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

  protected updateForm(gameInstProps: IGameInstProps): void {
    this.gameInstProps = gameInstProps;
    this.gameInstPropsFormService.resetForm(this.editForm, gameInstProps);
  }
}
