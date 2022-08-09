import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MmemberFormService, MmemberFormGroup } from './mmember-form.service';
import { IMmember } from '../mmember.model';
import { MmemberService } from '../service/mmember.service';
import { IRoleGroup } from 'app/entities/role-group/role-group.model';
import { RoleGroupService } from 'app/entities/role-group/service/role-group.service';

@Component({
  selector: 'jhi-mmember-update',
  templateUrl: './mmember-update.component.html',
})
export class MmemberUpdateComponent implements OnInit {
  isSaving = false;
  mmember: IMmember | null = null;

  roleGroupsSharedCollection: IRoleGroup[] = [];

  editForm: MmemberFormGroup = this.mmemberFormService.createMmemberFormGroup();

  constructor(
    protected mmemberService: MmemberService,
    protected mmemberFormService: MmemberFormService,
    protected roleGroupService: RoleGroupService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareRoleGroup = (o1: IRoleGroup | null, o2: IRoleGroup | null): boolean => this.roleGroupService.compareRoleGroup(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mmember }) => {
      this.mmember = mmember;
      if (mmember) {
        this.updateForm(mmember);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mmember = this.mmemberFormService.getMmember(this.editForm);
    if (mmember.id !== null) {
      this.subscribeToSaveResponse(this.mmemberService.update(mmember));
    } else {
      this.subscribeToSaveResponse(this.mmemberService.create(mmember));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMmember>>): void {
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

  protected updateForm(mmember: IMmember): void {
    this.mmember = mmember;
    this.mmemberFormService.resetForm(this.editForm, mmember);

    this.roleGroupsSharedCollection = this.roleGroupService.addRoleGroupToCollectionIfMissing<IRoleGroup>(
      this.roleGroupsSharedCollection,
      mmember.roleGroup
    );
  }

  protected loadRelationshipsOptions(): void {
    this.roleGroupService
      .query()
      .pipe(map((res: HttpResponse<IRoleGroup[]>) => res.body ?? []))
      .pipe(
        map((roleGroups: IRoleGroup[]) =>
          this.roleGroupService.addRoleGroupToCollectionIfMissing<IRoleGroup>(roleGroups, this.mmember?.roleGroup)
        )
      )
      .subscribe((roleGroups: IRoleGroup[]) => (this.roleGroupsSharedCollection = roleGroups));
  }
}
