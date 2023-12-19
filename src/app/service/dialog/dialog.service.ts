import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog'; 
import { MatCofnirmDialogComponent } from 'app/mat-cofnirm-dialog/mat-cofnirm-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(private dialog : MatDialog) { }

  openConfirmDialog(){
    this.dialog.open(MatCofnirmDialogComponent,{
      width : '390px',
      disableClose : true
    });
  }
}
