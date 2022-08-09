import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MmemberComponent } from '../list/mmember.component';
import { MmemberDetailComponent } from '../detail/mmember-detail.component';
import { MmemberUpdateComponent } from '../update/mmember-update.component';
import { MmemberRoutingResolveService } from './mmember-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const mmemberRoute: Routes = [
  {
    path: '',
    component: MmemberComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MmemberDetailComponent,
    resolve: {
      mmember: MmemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MmemberUpdateComponent,
    resolve: {
      mmember: MmemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MmemberUpdateComponent,
    resolve: {
      mmember: MmemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mmemberRoute)],
  exports: [RouterModule],
})
export class MmemberRoutingModule {}
