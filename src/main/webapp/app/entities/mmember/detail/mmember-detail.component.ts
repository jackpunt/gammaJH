import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMmember } from '../mmember.model';

@Component({
  selector: 'jhi-mmember-detail',
  templateUrl: './mmember-detail.component.html',
})
export class MmemberDetailComponent implements OnInit {
  mmember: IMmember | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mmember }) => {
      this.mmember = mmember;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
