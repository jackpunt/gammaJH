import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGroupAuthority } from '../group-authority.model';
import { GroupAuthorityService } from '../service/group-authority.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './group-authority-delete-dialog.component.html',
})
export class GroupAuthorityDeleteDialogComponent {
  groupAuthority?: IGroupAuthority;

  constructor(protected groupAuthorityService: GroupAuthorityService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.groupAuthorityService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
