import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-mat-cofnirm-dialog',
  templateUrl: './mat-cofnirm-dialog.component.html',
  styleUrls: ['./mat-cofnirm-dialog.component.scss']
})
export class MatCofnirmDialogComponent   {

  id : any;
  message: string = "Are you sure?"
  confirmButtonText = "Yes"
  cancelButtonText = "Cancel"

  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<MatCofnirmDialogComponent>, 
) {

    if(data){
      this.message = data.message || this.message;
      if (data.buttonText) {
        this.confirmButtonText = data.buttonText.ok || this.confirmButtonText;
        this.cancelButtonText = data.buttonText.cancel || this.cancelButtonText;
      }
    }
    this.dialogRef.updateSize('300vw','300vw')
  }
  ngOnInit(): void {
   // throw new Error('Method not implemented.');
  }

  onConfirmClick(): void {
      this.dialogRef.close(true);
  }
  close() : void {
    this.dialogRef.close(false);
  }
}


