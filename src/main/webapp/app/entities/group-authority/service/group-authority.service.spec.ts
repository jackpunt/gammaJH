import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGroupAuthority } from '../group-authority.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../group-authority.test-samples';

import { GroupAuthorityService } from './group-authority.service';

const requireRestSample: IGroupAuthority = {
  ...sampleWithRequiredData,
};

describe('GroupAuthority Service', () => {
  let service: GroupAuthorityService;
  let httpMock: HttpTestingController;
  let expectedResult: IGroupAuthority | IGroupAuthority[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GroupAuthorityService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a GroupAuthority', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const groupAuthority = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(groupAuthority).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GroupAuthority', () => {
      const groupAuthority = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(groupAuthority).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GroupAuthority', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GroupAuthority', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GroupAuthority', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addGroupAuthorityToCollectionIfMissing', () => {
      it('should add a GroupAuthority to an empty array', () => {
        const groupAuthority: IGroupAuthority = sampleWithRequiredData;
        expectedResult = service.addGroupAuthorityToCollectionIfMissing([], groupAuthority);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(groupAuthority);
      });

      it('should not add a GroupAuthority to an array that contains it', () => {
        const groupAuthority: IGroupAuthority = sampleWithRequiredData;
        const groupAuthorityCollection: IGroupAuthority[] = [
          {
            ...groupAuthority,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGroupAuthorityToCollectionIfMissing(groupAuthorityCollection, groupAuthority);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GroupAuthority to an array that doesn't contain it", () => {
        const groupAuthority: IGroupAuthority = sampleWithRequiredData;
        const groupAuthorityCollection: IGroupAuthority[] = [sampleWithPartialData];
        expectedResult = service.addGroupAuthorityToCollectionIfMissing(groupAuthorityCollection, groupAuthority);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(groupAuthority);
      });

      it('should add only unique GroupAuthority to an array', () => {
        const groupAuthorityArray: IGroupAuthority[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const groupAuthorityCollection: IGroupAuthority[] = [sampleWithRequiredData];
        expectedResult = service.addGroupAuthorityToCollectionIfMissing(groupAuthorityCollection, ...groupAuthorityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const groupAuthority: IGroupAuthority = sampleWithRequiredData;
        const groupAuthority2: IGroupAuthority = sampleWithPartialData;
        expectedResult = service.addGroupAuthorityToCollectionIfMissing([], groupAuthority, groupAuthority2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(groupAuthority);
        expect(expectedResult).toContain(groupAuthority2);
      });

      it('should accept null and undefined values', () => {
        const groupAuthority: IGroupAuthority = sampleWithRequiredData;
        expectedResult = service.addGroupAuthorityToCollectionIfMissing([], null, groupAuthority, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(groupAuthority);
      });

      it('should return initial array if no GroupAuthority is added', () => {
        const groupAuthorityCollection: IGroupAuthority[] = [sampleWithRequiredData];
        expectedResult = service.addGroupAuthorityToCollectionIfMissing(groupAuthorityCollection, undefined, null);
        expect(expectedResult).toEqual(groupAuthorityCollection);
      });
    });

    describe('compareGroupAuthority', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGroupAuthority(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGroupAuthority(entity1, entity2);
        const compareResult2 = service.compareGroupAuthority(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGroupAuthority(entity1, entity2);
        const compareResult2 = service.compareGroupAuthority(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGroupAuthority(entity1, entity2);
        const compareResult2 = service.compareGroupAuthority(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
