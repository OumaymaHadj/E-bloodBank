import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatCofnirmDialogComponent } from './mat-cofnirm-dialog.component';

describe('MatCofnirmDialogComponent', () => {
  let component: MatCofnirmDialogComponent;
  let fixture: ComponentFixture<MatCofnirmDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MatCofnirmDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MatCofnirmDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
