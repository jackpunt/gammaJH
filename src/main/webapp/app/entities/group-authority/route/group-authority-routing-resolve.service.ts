import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGroupAuthority } from '../group-authority.model';
import { GroupAuthorityService } from '../service/group-authority.service';

@Injectable({ providedIn: 'root' })
export class GroupAuthorityRoutingResolveService implements Resolve<IGroupAuthority | null> {
  constructor(protected service: GroupAuthorityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGroupAuthority | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((groupAuthority: HttpResponse<IGroupAuthority>) => {
          if (groupAuthority.body) {
            return of(groupAuthority.body);
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
