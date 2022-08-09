import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MmemberComponent } from './list/mmember.component';
import { MmemberDetailComponent } from './detail/mmember-detail.component';
import { MmemberUpdateComponent } from './update/mmember-update.component';
import { MmemberDeleteDialogComponent } from './delete/mmember-delete-dialog.component';
import { MmemberRoutingModule } from './route/mmember-routing.module';

@NgModule({
  imports: [SharedModule, MmemberRoutingModule],
  declarations: [MmemberComponent, MmemberDetailComponent, MmemberUpdateComponent, MmemberDeleteDialogComponent],
})
export class MmemberModule {}
