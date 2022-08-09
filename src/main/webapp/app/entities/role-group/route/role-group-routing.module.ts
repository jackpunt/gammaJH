import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RoleGroupComponent } from '../list/role-group.component';
import { RoleGroupDetailComponent } from '../detail/role-group-detail.component';
import { RoleGroupUpdateComponent } from '../update/role-group-update.component';
import { RoleGroupRoutingResolveService } from './role-group-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const roleGroupRoute: Routes = [
  {
    path: '',
    component: RoleGroupComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RoleGroupDetailComponent,
    resolve: {
      roleGroup: RoleGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RoleGroupUpdateComponent,
    resolve: {
      roleGroup: RoleGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RoleGroupUpdateComponent,
    resolve: {
      roleGroup: RoleGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(roleGroupRoute)],
  exports: [RouterModule],
})
export class RoleGroupRoutingModule {}
