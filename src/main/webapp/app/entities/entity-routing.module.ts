import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'game-inst-props',
        data: { pageTitle: 'gammaJhApp.gameInstProps.home.title' },
        loadChildren: () => import('./game-inst-props/game-inst-props.module').then(m => m.GameInstPropsModule),
      },
      {
        path: 'game-inst',
        data: { pageTitle: 'gammaJhApp.gameInst.home.title' },
        loadChildren: () => import('./game-inst/game-inst.module').then(m => m.GameInstModule),
      },
      {
        path: 'game-player',
        data: { pageTitle: 'gammaJhApp.gamePlayer.home.title' },
        loadChildren: () => import('./game-player/game-player.module').then(m => m.GamePlayerModule),
      },
      {
        path: 'game-class',
        data: { pageTitle: 'gammaJhApp.gameClass.home.title' },
        loadChildren: () => import('./game-class/game-class.module').then(m => m.GameClassModule),
      },
      {
        path: 'player',
        data: { pageTitle: 'gammaJhApp.player.home.title' },
        loadChildren: () => import('./player/player.module').then(m => m.PlayerModule),
      },
      {
        path: 'mmember',
        data: { pageTitle: 'gammaJhApp.mmember.home.title' },
        loadChildren: () => import('./mmember/mmember.module').then(m => m.MmemberModule),
      },
      {
        path: 'asset',
        data: { pageTitle: 'gammaJhApp.asset.home.title' },
        loadChildren: () => import('./asset/asset.module').then(m => m.AssetModule),
      },
      {
        path: 'role-group',
        data: { pageTitle: 'gammaJhApp.roleGroup.home.title' },
        loadChildren: () => import('./role-group/role-group.module').then(m => m.RoleGroupModule),
      },
      {
        path: 'group-authority',
        data: { pageTitle: 'gammaJhApp.groupAuthority.home.title' },
        loadChildren: () => import('./group-authority/group-authority.module').then(m => m.GroupAuthorityModule),
      },
      {
        path: 'mmember-game-props',
        data: { pageTitle: 'gammaJhApp.mmemberGameProps.home.title' },
        loadChildren: () => import('./mmember-game-props/mmember-game-props.module').then(m => m.MmemberGamePropsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
