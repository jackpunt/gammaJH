import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GroupAuthorityComponent } from './list/group-authority.component';
import { GroupAuthorityDetailComponent } from './detail/group-authority-detail.component';
import { GroupAuthorityUpdateComponent } from './update/group-authority-update.component';
import { GroupAuthorityDeleteDialogComponent } from './delete/group-authority-delete-dialog.component';
import { GroupAuthorityRoutingModule } from './route/group-authority-routing.module';

@NgModule({
  imports: [SharedModule, GroupAuthorityRoutingModule],
  declarations: [
    GroupAuthorityComponent,
    GroupAuthorityDetailComponent,
    GroupAuthorityUpdateComponent,
    GroupAuthorityDeleteDialogComponent,
  ],
})
export class GroupAuthorityModule {}
