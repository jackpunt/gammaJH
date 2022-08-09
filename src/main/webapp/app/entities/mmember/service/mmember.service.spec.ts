import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMmember } from '../mmember.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../mmember.test-samples';

import { MmemberService } from './mmember.service';

const requireRestSample: IMmember = {
  ...sampleWithRequiredData,
};

describe('Mmember Service', () => {
  let service: MmemberService;
  let httpMock: HttpTestingController;
  let expectedResult: IMmember | IMmember[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MmemberService);
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

    it('should create a Mmember', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const mmember = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mmember).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Mmember', () => {
      const mmember = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mmember).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Mmember', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Mmember', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Mmember', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMmemberToCollectionIfMissing', () => {
      it('should add a Mmember to an empty array', () => {
        const mmember: IMmember = sampleWithRequiredData;
        expectedResult = service.addMmemberToCollectionIfMissing([], mmember);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mmember);
      });

      it('should not add a Mmember to an array that contains it', () => {
        const mmember: IMmember = sampleWithRequiredData;
        const mmemberCollection: IMmember[] = [
          {
            ...mmember,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMmemberToCollectionIfMissing(mmemberCollection, mmember);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Mmember to an array that doesn't contain it", () => {
        const mmember: IMmember = sampleWithRequiredData;
        const mmemberCollection: IMmember[] = [sampleWithPartialData];
        expectedResult = service.addMmemberToCollectionIfMissing(mmemberCollection, mmember);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mmember);
      });

      it('should add only unique Mmember to an array', () => {
        const mmemberArray: IMmember[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mmemberCollection: IMmember[] = [sampleWithRequiredData];
        expectedResult = service.addMmemberToCollectionIfMissing(mmemberCollection, ...mmemberArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mmember: IMmember = sampleWithRequiredData;
        const mmember2: IMmember = sampleWithPartialData;
        expectedResult = service.addMmemberToCollectionIfMissing([], mmember, mmember2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mmember);
        expect(expectedResult).toContain(mmember2);
      });

      it('should accept null and undefined values', () => {
        const mmember: IMmember = sampleWithRequiredData;
        expectedResult = service.addMmemberToCollectionIfMissing([], null, mmember, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mmember);
      });

      it('should return initial array if no Mmember is added', () => {
        const mmemberCollection: IMmember[] = [sampleWithRequiredData];
        expectedResult = service.addMmemberToCollectionIfMissing(mmemberCollection, undefined, null);
        expect(expectedResult).toEqual(mmemberCollection);
      });
    });

    describe('compareMmember', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMmember(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMmember(entity1, entity2);
        const compareResult2 = service.compareMmember(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMmember(entity1, entity2);
        const compareResult2 = service.compareMmember(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMmember(entity1, entity2);
        const compareResult2 = service.compareMmember(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
