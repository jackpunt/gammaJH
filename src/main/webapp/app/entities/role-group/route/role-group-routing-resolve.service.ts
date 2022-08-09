import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRoleGroup } from '../role-group.model';
import { RoleGroupService } from '../service/role-group.service';

@Injectable({ providedIn: 'root' })
export class RoleGroupRoutingResolveService implements Resolve<IRoleGroup | null> {
  constructor(protected service: RoleGroupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRoleGroup | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((roleGroup: HttpResponse<IRoleGroup>) => {
          if (roleGroup.body) {
            return of(roleGroup.body);
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
