import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMmemberGameProps } from '../mmember-game-props.model';

@Component({
  selector: 'jhi-mmember-game-props-detail',
  templateUrl: './mmember-game-props-detail.component.html',
})
export class MmemberGamePropsDetailComponent implements OnInit {
  mmemberGameProps: IMmemberGameProps | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mmemberGameProps }) => {
      this.mmemberGameProps = mmemberGameProps;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
