import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMmemberGameProps, NewMmemberGameProps } from '../mmember-game-props.model';

export type PartialUpdateMmemberGameProps = Partial<IMmemberGameProps> & Pick<IMmemberGameProps, 'id'>;

export type EntityResponseType = HttpResponse<IMmemberGameProps>;
export type EntityArrayResponseType = HttpResponse<IMmemberGameProps[]>;

@Injectable({ providedIn: 'root' })
export class MmemberGamePropsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mmember-game-props');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mmemberGameProps: NewMmemberGameProps): Observable<EntityResponseType> {
    return this.http.post<IMmemberGameProps>(this.resourceUrl, mmemberGameProps, { observe: 'response' });
  }

  update(mmemberGameProps: IMmemberGameProps): Observable<EntityResponseType> {
    return this.http.put<IMmemberGameProps>(
      `${this.resourceUrl}/${this.getMmemberGamePropsIdentifier(mmemberGameProps)}`,
      mmemberGameProps,
      { observe: 'response' }
    );
  }

  partialUpdate(mmemberGameProps: PartialUpdateMmemberGameProps): Observable<EntityResponseType> {
    return this.http.patch<IMmemberGameProps>(
      `${this.resourceUrl}/${this.getMmemberGamePropsIdentifier(mmemberGameProps)}`,
      mmemberGameProps,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMmemberGameProps>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMmemberGameProps[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMmemberGamePropsIdentifier(mmemberGameProps: Pick<IMmemberGameProps, 'id'>): number {
    return mmemberGameProps.id;
  }

  compareMmemberGameProps(o1: Pick<IMmemberGameProps, 'id'> | null, o2: Pick<IMmemberGameProps, 'id'> | null): boolean {
    return o1 && o2 ? this.getMmemberGamePropsIdentifier(o1) === this.getMmemberGamePropsIdentifier(o2) : o1 === o2;
  }

  addMmemberGamePropsToCollectionIfMissing<Type extends Pick<IMmemberGameProps, 'id'>>(
    mmemberGamePropsCollection: Type[],
    ...mmemberGamePropsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mmemberGameProps: Type[] = mmemberGamePropsToCheck.filter(isPresent);
    if (mmemberGameProps.length > 0) {
      const mmemberGamePropsCollectionIdentifiers = mmemberGamePropsCollection.map(
        mmemberGamePropsItem => this.getMmemberGamePropsIdentifier(mmemberGamePropsItem)!
      );
      const mmemberGamePropsToAdd = mmemberGameProps.filter(mmemberGamePropsItem => {
        const mmemberGamePropsIdentifier = this.getMmemberGamePropsIdentifier(mmemberGamePropsItem);
        if (mmemberGamePropsCollectionIdentifiers.includes(mmemberGamePropsIdentifier)) {
          return false;
        }
        mmemberGamePropsCollectionIdentifiers.push(mmemberGamePropsIdentifier);
        return true;
      });
      return [...mmemberGamePropsToAdd, ...mmemberGamePropsCollection];
    }
    return mmemberGamePropsCollection;
  }
}
