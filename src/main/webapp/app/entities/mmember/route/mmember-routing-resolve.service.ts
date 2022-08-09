import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMmember } from '../mmember.model';
import { MmemberService } from '../service/mmember.service';

@Injectable({ providedIn: 'root' })
export class MmemberRoutingResolveService implements Resolve<IMmember | null> {
  constructor(protected service: MmemberService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMmember | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mmember: HttpResponse<IMmember>) => {
          if (mmember.body) {
            return of(mmember.body);
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
