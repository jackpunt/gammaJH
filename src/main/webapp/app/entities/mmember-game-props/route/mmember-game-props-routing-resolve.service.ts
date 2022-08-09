import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMmemberGameProps } from '../mmember-game-props.model';
import { MmemberGamePropsService } from '../service/mmember-game-props.service';

@Injectable({ providedIn: 'root' })
export class MmemberGamePropsRoutingResolveService implements Resolve<IMmemberGameProps | null> {
  constructor(protected service: MmemberGamePropsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMmemberGameProps | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mmemberGameProps: HttpResponse<IMmemberGameProps>) => {
          if (mmemberGameProps.body) {
            return of(mmemberGameProps.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
