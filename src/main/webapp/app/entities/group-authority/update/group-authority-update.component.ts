import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { GroupAuthorityFormService, GroupAuthorityFormGroup } from './group-authority-form.service';
import { IGroupAuthority } from '../group-authority.model';
import { GroupAuthorityService } from '../service/group-authority.service';
import { IRoleGroup } from 'app/entities/role-group/role-group.model';
import { RoleGroupService } from 'app/entities/role-group/service/role-group.service';

@Component({
  selector: 'jhi-group-authority-update',
  templateUrl: './group-authority-update.component.html',
})
export class GroupAuthorityUpdateComponent implements OnInit {
  isSaving = false;
  groupAuthority: IGroupAuthority | null = null;

  roleGroupsSharedCollection: IRoleGroup[] = [];

  editForm: GroupAuthorityFormGroup = this.groupAuthorityFormService.createGroupAuthorityFormGroup();

  constructor(
    protected groupAuthorityService: GroupAuthorityService,
    protected groupAuthorityFormService: GroupAuthorityFormService,
    protected roleGroupService: RoleGroupService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareRoleGroup = (o1: IRoleGroup | null, o2: IRoleGroup | null): boolean => this.roleGroupService.compareRoleGroup(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groupAuthority }) => {
      this.groupAuthority = groupAuthority;
      if (groupAuthority) {
        this.updateForm(groupAuthority);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const groupAuthority = this.groupAuthorityFormService.getGroupAuthority(this.editForm);
    if (groupAuthority.id !== null) {
      this.subscribeToSaveResponse(this.groupAuthorityService.update(groupAuthority));
    } else {
      this.subscribeToSaveResponse(this.groupAuthorityService.create(groupAuthority));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupAuthority>>): void {
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

  protected updateForm(groupAuthority: IGroupAuthority): void {
    this.groupAuthority = groupAuthority;
    this.groupAuthorityFormService.resetForm(this.editForm, groupAuthority);

    this.roleGroupsSharedCollection = this.roleGroupService.addRoleGroupToCollectionIfMissing<IRoleGroup>(
      this.roleGroupsSharedCollection,
      groupAuthority.roleGroup
    );
  }

  protected loadRelationshipsOptions(): void {
    this.roleGroupService
      .query()
      .pipe(map((res: HttpResponse<IRoleGroup[]>) => res.body ?? []))
      .pipe(
        map((roleGroups: IRoleGroup[]) =>
          this.roleGroupService.addRoleGroupToCollectionIfMissing<IRoleGroup>(roleGroups, this.groupAuthority?.roleGroup)
        )
      )
      .subscribe((roleGroups: IRoleGroup[]) => (this.roleGroupsSharedCollection = roleGroups));
  }
}
