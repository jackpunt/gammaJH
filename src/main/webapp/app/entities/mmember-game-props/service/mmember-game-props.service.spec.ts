import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMmemberGameProps } from '../mmember-game-props.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../mmember-game-props.test-samples';

import { MmemberGamePropsService } from './mmember-game-props.service';

const requireRestSample: IMmemberGameProps = {
  ...sampleWithRequiredData,
};

describe('MmemberGameProps Service', () => {
  let service: MmemberGamePropsService;
  let httpMock: HttpTestingController;
  let expectedResult: IMmemberGameProps | IMmemberGameProps[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MmemberGamePropsService);
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

    it('should create a MmemberGameProps', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const mmemberGameProps = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mmemberGameProps).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MmemberGameProps', () => {
      const mmemberGameProps = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mmemberGameProps).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MmemberGameProps', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MmemberGameProps', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MmemberGameProps', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMmemberGamePropsToCollectionIfMissing', () => {
      it('should add a MmemberGameProps to an empty array', () => {
        const mmemberGameProps: IMmemberGameProps = sampleWithRequiredData;
        expectedResult = service.addMmemberGamePropsToCollectionIfMissing([], mmemberGameProps);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mmemberGameProps);
      });

      it('should not add a MmemberGameProps to an array that contains it', () => {
        const mmemberGameProps: IMmemberGameProps = sampleWithRequiredData;
        const mmemberGamePropsCollection: IMmemberGameProps[] = [
          {
            ...mmemberGameProps,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMmemberGamePropsToCollectionIfMissing(mmemberGamePropsCollection, mmemberGameProps);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MmemberGameProps to an array that doesn't contain it", () => {
        const mmemberGameProps: IMmemberGameProps = sampleWithRequiredData;
        const mmemberGamePropsCollection: IMmemberGameProps[] = [sampleWithPartialData];
        expectedResult = service.addMmemberGamePropsToCollectionIfMissing(mmemberGamePropsCollection, mmemberGameProps);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mmemberGameProps);
      });

      it('should add only unique MmemberGameProps to an array', () => {
        const mmemberGamePropsArray: IMmemberGameProps[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mmemberGamePropsCollection: IMmemberGameProps[] = [sampleWithRequiredData];
        expectedResult = service.addMmemberGamePropsToCollectionIfMissing(mmemberGamePropsCollection, ...mmemberGamePropsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mmemberGameProps: IMmemberGameProps = sampleWithRequiredData;
        const mmemberGameProps2: IMmemberGameProps = sampleWithPartialData;
        expectedResult = service.addMmemberGamePropsToCollectionIfMissing([], mmemberGameProps, mmemberGameProps2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mmemberGameProps);
        expect(expectedResult).toContain(mmemberGameProps2);
      });

      it('should accept null and undefined values', () => {
        const mmemberGameProps: IMmemberGameProps = sampleWithRequiredData;
        expectedResult = service.addMmemberGamePropsToCollectionIfMissing([], null, mmemberGameProps, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mmemberGameProps);
      });

      it('should return initial array if no MmemberGameProps is added', () => {
        const mmemberGamePropsCollection: IMmemberGameProps[] = [sampleWithRequiredData];
        expectedResult = service.addMmemberGamePropsToCollectionIfMissing(mmemberGamePropsCollection, undefined, null);
        expect(expectedResult).toEqual(mmemberGamePropsCollection);
      });
    });

    describe('compareMmemberGameProps', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMmemberGameProps(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMmemberGameProps(entity1, entity2);
        const compareResult2 = service.compareMmemberGameProps(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMmemberGameProps(entity1, entity2);
        const compareResult2 = service.compareMmemberGameProps(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMmemberGameProps(entity1, entity2);
        const compareResult2 = service.compareMmemberGameProps(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
