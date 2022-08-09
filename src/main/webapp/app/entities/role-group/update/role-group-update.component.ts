import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RoleGroupFormService, RoleGroupFormGroup } from './role-group-form.service';
import { IRoleGroup } from '../role-group.model';
import { RoleGroupService } from '../service/role-group.service';

@Component({
  selector: 'jhi-role-group-update',
  templateUrl: './role-group-update.component.html',
})
export class RoleGroupUpdateComponent implements OnInit {
  isSaving = false;
  roleGroup: IRoleGroup | null = null;

  editForm: RoleGroupFormGroup = this.roleGroupFormService.createRoleGroupFormGroup();

  constructor(
    protected roleGroupService: RoleGroupService,
    protected roleGroupFormService: RoleGroupFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleGroup }) => {
      this.roleGroup = roleGroup;
      if (roleGroup) {
        this.updateForm(roleGroup);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const roleGroup = this.roleGroupFormService.getRoleGroup(this.editForm);
    if (roleGroup.id !== null) {
      this.subscribeToSaveResponse(this.roleGroupService.update(roleGroup));
    } else {
      this.subscribeToSaveResponse(this.roleGroupService.create(roleGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoleGroup>>): void {
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

  protected updateForm(roleGroup: IRoleGroup): void {
    this.roleGroup = roleGroup;
    this.roleGroupFormService.resetForm(this.editForm, roleGroup);
  }
}
