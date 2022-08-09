import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMmember, NewMmember } from '../mmember.model';

export type PartialUpdateMmember = Partial<IMmember> & Pick<IMmember, 'id'>;

export type EntityResponseType = HttpResponse<IMmember>;
export type EntityArrayResponseType = HttpResponse<IMmember[]>;

@Injectable({ providedIn: 'root' })
export class MmemberService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mmembers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mmember: NewMmember): Observable<EntityResponseType> {
    return this.http.post<IMmember>(this.resourceUrl, mmember, { observe: 'response' });
  }

  update(mmember: IMmember): Observable<EntityResponseType> {
    return this.http.put<IMmember>(`${this.resourceUrl}/${this.getMmemberIdentifier(mmember)}`, mmember, { observe: 'response' });
  }

  partialUpdate(mmember: PartialUpdateMmember): Observable<EntityResponseType> {
    return this.http.patch<IMmember>(`${this.resourceUrl}/${this.getMmemberIdentifier(mmember)}`, mmember, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMmember>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMmember[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMmemberIdentifier(mmember: Pick<IMmember, 'id'>): number {
    return mmember.id;
  }

  compareMmember(o1: Pick<IMmember, 'id'> | null, o2: Pick<IMmember, 'id'> | null): boolean {
    return o1 && o2 ? this.getMmemberIdentifier(o1) === this.getMmemberIdentifier(o2) : o1 === o2;
  }

  addMmemberToCollectionIfMissing<Type extends Pick<IMmember, 'id'>>(
    mmemberCollection: Type[],
    ...mmembersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mmembers: Type[] = mmembersToCheck.filter(isPresent);
    if (mmembers.length > 0) {
      const mmemberCollectionIdentifiers = mmemberCollection.map(mmemberItem => this.getMmemberIdentifier(mmemberItem)!);
      const mmembersToAdd = mmembers.filter(mmemberItem => {
        const mmemberIdentifier = this.getMmemberIdentifier(mmemberItem);
        if (mmemberCollectionIdentifiers.includes(mmemberIdentifier)) {
          return false;
        }
        mmemberCollectionIdentifiers.push(mmemberIdentifier);
        return true;
      });
      return [...mmembersToAdd, ...mmemberCollection];
    }
    return mmemberCollection;
  }
}
