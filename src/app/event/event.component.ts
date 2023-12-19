import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatCofnirmDialogComponent } from 'app/mat-cofnirm-dialog/mat-cofnirm-dialog.component';
import { Event } from 'app/model/event';
import { Notifiacation } from 'app/model/notification';
import { EventService } from 'app/service/event/event.service';
import { NotificationService } from 'app/service/notification.service';
import * as Chartist from 'chartist';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})

export class EventComponent implements OnInit {

  @Input() date = new Date();
  start_time : string;
  end_time : string;
  @Input() events : Event[];
  minDate = new Date();
  formdata : FormGroup;
  submitted = false

  id: number;
  isAddMode: boolean;
  loading = false;
  selected? : Event ;
  minValue: Date;
  maxValue: Date;
 
  
  @Output() submit = new EventEmitter<Event>();

  eventObj = new Event();
  showAdd !:boolean;
  showUpdate !: boolean;
 
  startTime = new FormControl(new Date());

  notif = new Notifiacation() ;

  constructor(private eventService : EventService,private dialog: MatDialog,
    private snackBar: MatSnackBar, private notificationService : NotificationService) { 
    const minValue = new Date();
    minValue.setHours(6);
    minValue.setMinutes(10);
    minValue.setSeconds(0);
    this.minValue = minValue;

    const maxValue = new Date();
    maxValue.setHours(18);
    maxValue.setMinutes(10);
    minValue.setSeconds(0);
    this.maxValue = maxValue;
  }
  
  ngOnInit() {
    this.formdata = new FormGroup({
      title : new FormControl('', [Validators.required,Validators.pattern('^[a-zA-Z ]*$')]),
      max_nb_participants : new FormControl('', [Validators.required]),
      description : new FormControl('', [Validators.required]),
      date : new FormControl('', [Validators.required]),
      start_time : new FormControl('', [Validators.required]),
      end_time : new FormControl('', [Validators.required]),
      city : new FormControl('', [Validators.required]),
      country : new FormControl('', [Validators.required,Validators.pattern('^(^[a-zA-Z ]*(\s)*)*[0-9_\s]*$')]),
   });

      /* ----------==========     Completed Events Chart initialization    ==========---------- */

      const dataCompletedTasksChart: any = {
        labels: ['12p', '3p', '6p', '9p', '12p', '3a', '6a', '9a'],
        series: [
            [230, 750, 450, 300, 280, 240, 200, 190]
        ]
      };
    
      const optionsCompletedTasksChart: any = {
          lineSmooth: Chartist.Interpolation.cardinal({
              tension: 0
          }),
          low: 0,
          high: 1000, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
          chartPadding: { top: 0, right: 0, bottom: 0, left: 0}
      }
    
      var completedTasksChart = new Chartist.Line('#completedTasksChart', dataCompletedTasksChart, optionsCompletedTasksChart);
    
      // start animation for the Completed Tasks Chart - Line Chart
      this.startAnimationForLineChart(completedTasksChart);
      
   this.getAllEvents();
   this.clickAddEvent();
  }

  clickAddEvent(){
    this.formdata.reset();
    this.showAdd = true;
    this.showUpdate = false;
  }

  onSubmit(){

    this.submitted = true;

        // stop here if form is invalid
        if (this.formdata.invalid) {
            return;
        }
        console.warn(this.formdata.value);

       /* const d = new Date(this.formdata.controls['date'].value);
        const month = d.getMonth()+1;
        console.warn(month);
        const year = d.getFullYear();
        console.warn(year);
        const day = d.getDate();
        console.warn(day);
        
        const dd = day+'-'+month+'-'+year;
        console.warn(dd);
        this.formdata.controls['date'].setValue(dd); */
        this.eventService.saveEvent(this.formdata.value).subscribe((result)=>{
          console.warn('result',result)
          this.snackBar.open('Event created successfully', 'Fechar', {
            duration: 2000,
          });
        })
        this.getAllEvents();
  }

  getAllEvents() : void {
   
    this.eventService.getAllEvents().subscribe(data => 
      {
        for(let evnt in data) {
          data[evnt].start_time = data[evnt].start_time.substr(0,data[evnt].start_time.length-1);
        }
        this.events = data;
      });
  }

  onReset() {
    this.submitted = false;
    this.formdata.reset();
  }

  clicked(id , event){
    if(id===0){
      this.onSelect(event);
    }else if(id===1){
      this.deleteEvent(event);

    }else if(id===2){
      this.editEvent(event);
  }
}
  onSelect(event){
    //console.log(event);
    this.selected= event;
    console.log(this.selected);

    const dialogRef = this.dialog.open(MatCofnirmDialogComponent,{
      data:{
        message: 'Want to send an invitation for this event ?',
        buttonText: {
          ok: 'Yes',
          cancel: 'No'
        }
      }
    });
    
    //const snack = this.snackBar.open('Snack bar open before dialog');
    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        //this.notif.notification_type = "I";
        this.notif.event = this.selected ;
        //this.notif.source = "Admin";
        
        this.notificationService.saveNotif(this.notif).subscribe((result)=>{
          this.snackBar.open('Invitation sent successfully', 'Fechar', {
            duration: 2000,
          });
        });
       }
      }
    );
  }

  public checkError = (controlName: string, errorName: string) => {
    return this.formdata.controls[controlName].hasError(errorName);
  }

  deleteEvent(id:any){

  const dialogRef = this.dialog.open(MatCofnirmDialogComponent,{
    data:{
      message: 'Are you sure want to delete?',
      buttonText: {
        ok: 'Yes',
        cancel: 'No'
      }
    }
  });
  
  //const snack = this.snackBar.open('Snack bar open before dialog');
  dialogRef.afterClosed().subscribe((confirmed) => {
    if (confirmed) {
     // snack.dismiss();
     //const a = document.createElement('a');
     //a.click();
     //a.remove();
     //snack.dismiss();s
     this.eventService.deleteEvent(id).subscribe((result)=>{
       this.getAllEvents();
     });
     this.snackBar.open('Event deleted successfully', 'Fechar', {
        duration: 2000,
      });
    }
  });

  }

 /* selectEvent(){
    this.selected = event ; 
    this.submit.emit(this.formdata.getRawValue());
    this.formdata.reset();
  }*/

  updateEvent(){

    this.eventObj.title = this.formdata.value.title;
    this.eventObj.description = this.formdata.value.description;
    this.eventObj.date = this.formdata.value.date;
    this.eventObj.max_nb_participants = this.formdata.value.max_nb_participants;
    this.eventObj.city = this.formdata.value.city;
    this.eventObj.country = this.formdata.value.country;
    this.eventObj.start_time = this.formdata.value.startTime;
    this.eventObj.end_time = this.formdata.value.endTime;

    this.eventService.updateEvent(this.eventObj).subscribe((result)=>{
      alert("Updated Successfully");
      this.getAllEvents();
    })
  }

  editEvent(event : any){

    this.showAdd = false;
    this.showUpdate = true;

    this.eventObj.id = event.id;
    this.formdata.controls['title'].setValue(event.title);
    this.formdata.controls['description'].setValue(event.description);
    this.formdata.controls['date'].setValue(event.date);
    this.formdata.controls['max_nb_participants'].setValue(event.max_nb_participants);
    this.formdata.controls['city'].setValue(event.city);
    this.formdata.controls['country'].setValue(event.country);


    let date  = new Date(event.start_time.substr(0,event.start_time.length-1));
    this.start_time = date.getHours() + ':'+ date.getMinutes();

    date = new Date(event.end_time.substr(0,event.end_time.length-1));
    this.end_time = date.getHours() + ':'+ date.getMinutes();
}


  startAnimationForLineChart(chart){
    let seq: any, delays: any, durations: any;
    seq = 0;
    delays = 80;
    durations = 500;

    chart.on('draw', function(data) {
      if(data.type === 'line' || data.type === 'area') {
        data.element.animate({
          d: {
            begin: 600,
            dur: 700,
            from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
            to: data.path.clone().stringify(),
            easing: Chartist.Svg.Easing.easeOutQuint
          }
        });
      } else if(data.type === 'point') {
            seq++;
            data.element.animate({
              opacity: {
                begin: seq * delays,
                dur: durations,
                from: 0,
                to: 1,
                easing: 'ease'
              }
            });
        }
    });

    seq = 0;
};

}
