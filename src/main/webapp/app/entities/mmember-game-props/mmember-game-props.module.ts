import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MmemberGamePropsComponent } from './list/mmember-game-props.component';
import { MmemberGamePropsDetailComponent } from './detail/mmember-game-props-detail.component';
import { MmemberGamePropsUpdateComponent } from './update/mmember-game-props-update.component';
import { MmemberGamePropsDeleteDialogComponent } from './delete/mmember-game-props-delete-dialog.component';
import { MmemberGamePropsRoutingModule } from './route/mmember-game-props-routing.module';

@NgModule({
  imports: [SharedModule, MmemberGamePropsRoutingModule],
  declarations: [
    MmemberGamePropsComponent,
    MmemberGamePropsDetailComponent,
    MmemberGamePropsUpdateComponent,
    MmemberGamePropsDeleteDialogComponent,
  ],
})
export class MmemberGamePropsModule {}
