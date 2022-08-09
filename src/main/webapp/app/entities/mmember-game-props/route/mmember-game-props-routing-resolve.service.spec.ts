import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IMmemberGameProps } from '../mmember-game-props.model';
import { MmemberGamePropsService } from '../service/mmember-game-props.service';

import { MmemberGamePropsRoutingResolveService } from './mmember-game-props-routing-resolve.service';

describe('MmemberGameProps routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MmemberGamePropsRoutingResolveService;
  let service: MmemberGamePropsService;
  let resultMmemberGameProps: IMmemberGameProps | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(MmemberGamePropsRoutingResolveService);
    service = TestBed.inject(MmemberGamePropsService);
    resultMmemberGameProps = undefined;
  });

  describe('resolve', () => {
    it('should return IMmemberGameProps returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMmemberGameProps = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMmemberGameProps).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMmemberGameProps = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMmemberGameProps).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IMmemberGameProps>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMmemberGameProps = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMmemberGameProps).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
