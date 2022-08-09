import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRoleGroup } from '../role-group.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../role-group.test-samples';

import { RoleGroupService } from './role-group.service';

const requireRestSample: IRoleGroup = {
  ...sampleWithRequiredData,
};

describe('RoleGroup Service', () => {
  let service: RoleGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: IRoleGroup | IRoleGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RoleGroupService);
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

    it('should create a RoleGroup', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const roleGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(roleGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RoleGroup', () => {
      const roleGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(roleGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RoleGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RoleGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RoleGroup', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRoleGroupToCollectionIfMissing', () => {
      it('should add a RoleGroup to an empty array', () => {
        const roleGroup: IRoleGroup = sampleWithRequiredData;
        expectedResult = service.addRoleGroupToCollectionIfMissing([], roleGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(roleGroup);
      });

      it('should not add a RoleGroup to an array that contains it', () => {
        const roleGroup: IRoleGroup = sampleWithRequiredData;
        const roleGroupCollection: IRoleGroup[] = [
          {
            ...roleGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRoleGroupToCollectionIfMissing(roleGroupCollection, roleGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RoleGroup to an array that doesn't contain it", () => {
        const roleGroup: IRoleGroup = sampleWithRequiredData;
        const roleGroupCollection: IRoleGroup[] = [sampleWithPartialData];
        expectedResult = service.addRoleGroupToCollectionIfMissing(roleGroupCollection, roleGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(roleGroup);
      });

      it('should add only unique RoleGroup to an array', () => {
        const roleGroupArray: IRoleGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const roleGroupCollection: IRoleGroup[] = [sampleWithRequiredData];
        expectedResult = service.addRoleGroupToCollectionIfMissing(roleGroupCollection, ...roleGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const roleGroup: IRoleGroup = sampleWithRequiredData;
        const roleGroup2: IRoleGroup = sampleWithPartialData;
        expectedResult = service.addRoleGroupToCollectionIfMissing([], roleGroup, roleGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(roleGroup);
        expect(expectedResult).toContain(roleGroup2);
      });

      it('should accept null and undefined values', () => {
        const roleGroup: IRoleGroup = sampleWithRequiredData;
        expectedResult = service.addRoleGroupToCollectionIfMissing([], null, roleGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(roleGroup);
      });

      it('should return initial array if no RoleGroup is added', () => {
        const roleGroupCollection: IRoleGroup[] = [sampleWithRequiredData];
        expectedResult = service.addRoleGroupToCollectionIfMissing(roleGroupCollection, undefined, null);
        expect(expectedResult).toEqual(roleGroupCollection);
      });
    });

    describe('compareRoleGroup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRoleGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRoleGroup(entity1, entity2);
        const compareResult2 = service.compareRoleGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRoleGroup(entity1, entity2);
        const compareResult2 = service.compareRoleGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRoleGroup(entity1, entity2);
        const compareResult2 = service.compareRoleGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
