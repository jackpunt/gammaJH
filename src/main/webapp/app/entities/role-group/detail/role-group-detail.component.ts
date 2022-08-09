import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoleGroup } from '../role-group.model';

@Component({
  selector: 'jhi-role-group-detail',
  templateUrl: './role-group-detail.component.html',
})
export class RoleGroupDetailComponent implements OnInit {
  roleGroup: IRoleGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleGroup }) => {
      this.roleGroup = roleGroup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
