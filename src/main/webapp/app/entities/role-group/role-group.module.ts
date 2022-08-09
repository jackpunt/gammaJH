import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RoleGroupComponent } from './list/role-group.component';
import { RoleGroupDetailComponent } from './detail/role-group-detail.component';
import { RoleGroupUpdateComponent } from './update/role-group-update.component';
import { RoleGroupDeleteDialogComponent } from './delete/role-group-delete-dialog.component';
import { RoleGroupRoutingModule } from './route/role-group-routing.module';

@NgModule({
  imports: [SharedModule, RoleGroupRoutingModule],
  declarations: [RoleGroupComponent, RoleGroupDetailComponent, RoleGroupUpdateComponent, RoleGroupDeleteDialogComponent],
})
export class RoleGroupModule {}
