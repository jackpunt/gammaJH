import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MmemberGamePropsComponent } from '../list/mmember-game-props.component';
import { MmemberGamePropsDetailComponent } from '../detail/mmember-game-props-detail.component';
import { MmemberGamePropsUpdateComponent } from '../update/mmember-game-props-update.component';
import { MmemberGamePropsRoutingResolveService } from './mmember-game-props-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const mmemberGamePropsRoute: Routes = [
  {
    path: '',
    component: MmemberGamePropsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MmemberGamePropsDetailComponent,
    resolve: {
      mmemberGameProps: MmemberGamePropsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MmemberGamePropsUpdateComponent,
    resolve: {
      mmemberGameProps: MmemberGamePropsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MmemberGamePropsUpdateComponent,
    resolve: {
      mmemberGameProps: MmemberGamePropsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mmemberGamePropsRoute)],
  exports: [RouterModule],
})
export class MmemberGamePropsRoutingModule {}
