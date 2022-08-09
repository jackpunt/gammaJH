import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRoleGroup } from '../role-group.model';
import { RoleGroupService } from '../service/role-group.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './role-group-delete-dialog.component.html',
})
export class RoleGroupDeleteDialogComponent {
  roleGroup?: IRoleGroup;

  constructor(protected roleGroupService: RoleGroupService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.roleGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
