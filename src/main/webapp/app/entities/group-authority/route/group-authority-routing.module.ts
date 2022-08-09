import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GroupAuthorityComponent } from '../list/group-authority.component';
import { GroupAuthorityDetailComponent } from '../detail/group-authority-detail.component';
import { GroupAuthorityUpdateComponent } from '../update/group-authority-update.component';
import { GroupAuthorityRoutingResolveService } from './group-authority-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const groupAuthorityRoute: Routes = [
  {
    path: '',
    component: GroupAuthorityComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GroupAuthorityDetailComponent,
    resolve: {
      groupAuthority: GroupAuthorityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GroupAuthorityUpdateComponent,
    resolve: {
      groupAuthority: GroupAuthorityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GroupAuthorityUpdateComponent,
    resolve: {
      groupAuthority: GroupAuthorityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(groupAuthorityRoute)],
  exports: [RouterModule],
})
export class GroupAuthorityRoutingModule {}
