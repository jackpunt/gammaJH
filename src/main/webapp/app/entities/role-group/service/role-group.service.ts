import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRoleGroup, NewRoleGroup } from '../role-group.model';

export type PartialUpdateRoleGroup = Partial<IRoleGroup> & Pick<IRoleGroup, 'id'>;

export type EntityResponseType = HttpResponse<IRoleGroup>;
export type EntityArrayResponseType = HttpResponse<IRoleGroup[]>;

@Injectable({ providedIn: 'root' })
export class RoleGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/role-groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(roleGroup: NewRoleGroup): Observable<EntityResponseType> {
    return this.http.post<IRoleGroup>(this.resourceUrl, roleGroup, { observe: 'response' });
  }

  update(roleGroup: IRoleGroup): Observable<EntityResponseType> {
    return this.http.put<IRoleGroup>(`${this.resourceUrl}/${this.getRoleGroupIdentifier(roleGroup)}`, roleGroup, { observe: 'response' });
  }

  partialUpdate(roleGroup: PartialUpdateRoleGroup): Observable<EntityResponseType> {
    return this.http.patch<IRoleGroup>(`${this.resourceUrl}/${this.getRoleGroupIdentifier(roleGroup)}`, roleGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRoleGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRoleGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRoleGroupIdentifier(roleGroup: Pick<IRoleGroup, 'id'>): number {
    return roleGroup.id;
  }

  compareRoleGroup(o1: Pick<IRoleGroup, 'id'> | null, o2: Pick<IRoleGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getRoleGroupIdentifier(o1) === this.getRoleGroupIdentifier(o2) : o1 === o2;
  }

  addRoleGroupToCollectionIfMissing<Type extends Pick<IRoleGroup, 'id'>>(
    roleGroupCollection: Type[],
    ...roleGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const roleGroups: Type[] = roleGroupsToCheck.filter(isPresent);
    if (roleGroups.length > 0) {
      const roleGroupCollectionIdentifiers = roleGroupCollection.map(roleGroupItem => this.getRoleGroupIdentifier(roleGroupItem)!);
      const roleGroupsToAdd = roleGroups.filter(roleGroupItem => {
        const roleGroupIdentifier = this.getRoleGroupIdentifier(roleGroupItem);
        if (roleGroupCollectionIdentifiers.includes(roleGroupIdentifier)) {
          return false;
        }
        roleGroupCollectionIdentifiers.push(roleGroupIdentifier);
        return true;
      });
      return [...roleGroupsToAdd, ...roleGroupCollection];
    }
    return roleGroupCollection;
  }
}
