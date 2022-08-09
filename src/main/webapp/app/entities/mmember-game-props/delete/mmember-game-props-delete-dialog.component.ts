import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMmemberGameProps } from '../mmember-game-props.model';
import { MmemberGamePropsService } from '../service/mmember-game-props.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './mmember-game-props-delete-dialog.component.html',
})
export class MmemberGamePropsDeleteDialogComponent {
  mmemberGameProps?: IMmemberGameProps;

  constructor(protected mmemberGamePropsService: MmemberGamePropsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mmemberGamePropsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
