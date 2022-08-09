import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroupAuthority } from '../group-authority.model';

@Component({
  selector: 'jhi-group-authority-detail',
  templateUrl: './group-authority-detail.component.html',
})
export class GroupAuthorityDetailComponent implements OnInit {
  groupAuthority: IGroupAuthority | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groupAuthority }) => {
      this.groupAuthority = groupAuthority;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
