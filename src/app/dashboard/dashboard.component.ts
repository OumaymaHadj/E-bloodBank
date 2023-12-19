import { HttpParams } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { User } from 'app/model/user';
import { EventService } from 'app/service/event/event.service';
import { UserService } from 'app/service/user/user.service';
import * as Chartist from 'chartist';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  @Input() users:User[];

  @Input() nb_users:number;

  @Input() nb_users_Aplus;
  @Input() nb_users_Bplus;
  @Input() nb_users_Oplus;
  @Input() nb_users_ABplus;

  @Input() nb_users_Amoins;
  @Input() nb_users_Bmoins;
  @Input() nb_users_Omoins;
  @Input() nb_users_ABmoins;
  
  
  @Input() typeSang : string;
  user:User;
  nb_event_janv : number;

  params = new HttpParams()
  .set('sDate', 2022 +'-' + 12 + '-'+ 0o1 + 'T' + 10 + ':' + 10 + ':'+ 41.808 + 'Z' )
  .set('eDate', 2022 +'-' + 12 + '-'+ 31 + 'T' + 10 + ':' + 10 + ':'+ 41.808 + 'Z');
  constructor(private userService: UserService, private eventService : EventService) { }

  getNbUsers() : void {
    this.userService.getNbUsers().subscribe(data => 
      { this.nb_users = data;     
      
      });

  }

  getNbUsersByTypeSang(typeSang : string) {
    this.userService.getNbUsersByTypeSang(typeSang).subscribe(data =>
      { this.nb_users_Aplus = data;

      });
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
  startAnimationForBarChart(chart){
      let seq2: any, delays2: any, durations2: any;

      seq2 = 0;
      delays2 = 80;
      durations2 = 500;
      chart.on('draw', function(data) {
        if(data.type === 'bar'){
            seq2++;
            data.element.animate({
              opacity: {
                begin: seq2 * delays2,
                dur: durations2,
                from: 0,
                to: 1,
                easing: 'ease'
              }
            });
        }
      });

      seq2 = 0;
  };
  ngOnInit() {

    

      /* ----------==========     Daily Sales Chart initialization For Documentation    ==========---------- */

      const dataDailySalesChart: any = {
          labels: ['J', 'F', 'M', 'A', 'M', 'J', 'J', 'A', 'S', 'O', 'N', 'D'],
          series: [
              [ this.nb_users_ABplus, 0, 7, 17, 23, 18, 38]
          ]
      };

     const optionsDailySalesChart: any = {
          lineSmooth: Chartist.Interpolation.cardinal({
              tension: 0
          }),
          low: 0,
          high: 50, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
          chartPadding: { top: 0, right: 0, bottom: 0, left: 0},
      }

      var dailySalesChart = new Chartist.Line('#dailySalesChart', dataDailySalesChart, optionsDailySalesChart);

      this.startAnimationForLineChart(dailySalesChart);


      /* ----------==========     Completed Tasks Chart initialization    ==========---------- */

      const dataCompletedTasksChart: any = {
          labels: ['J', 'F', 'M', 'A', 'M', 'J', 'J', 'A', 'S', 'O', 'N', 'D'],
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



      /* ----------==========     Emails Subscription Chart initialization    ==========---------- */

      var datawebsiteViewsChart = {
        labels: ['J', 'F', 'M', 'A', 'M', 'J', 'J', 'A', 'S', 'O', 'N', 'D'],
        series: [
          [800, 443, 320, 780, 553, 453, 326, 434, 568, 610, 756, 895]

        ]
      };

      var optionswebsiteViewsChart = {
          axisX: {
              showGrid: false
          },
          low: 0,
          high: 1000,
          chartPadding: { top: 0, right: 5, bottom: 0, left: 0}
      };
      var responsiveOptions: any[] = [
        ['screen and (max-width: 640px)', {
          seriesBarDistance: 5,
          axisX: {
            labelInterpolationFnc: function (value) {
              return value[0];
            }
          }
        }]
      ];
      var websiteViewsChart = new Chartist.Bar('#websiteViewsChart', datawebsiteViewsChart, optionswebsiteViewsChart, responsiveOptions);

      //start animation for the Emails Subscription Chart
      this.startAnimationForBarChart(websiteViewsChart);
      
      this.userService.getNbUsersByTypeSang('A_plus').subscribe(data => 
        { this.nb_users_Aplus = data;

        });

      this.userService.getNbUsersByTypeSang('B_plus').subscribe(data => 
        { this.nb_users_Bplus = data;
          
        });

      this.userService.getNbUsersByTypeSang('O_plus').subscribe(data => 
          { this.nb_users_Oplus = data;
            
      });

      this.userService.getNbUsersByTypeSang('AB_plus').subscribe(data => 
          { this.nb_users_ABplus = data;
              
      });

      this.userService.getNbUsersByTypeSang('A_moins').subscribe(data => 
          { this.nb_users_Amoins = data;
                
      });

      this.userService.getNbUsersByTypeSang('B_moins').subscribe(data => 
          { this.nb_users_Bmoins = data;
                  
      });
                
      this.userService.getNbUsersByTypeSang('O_moins').subscribe(data => 
          { this.nb_users_Omoins = data;
                    
      });
                  
      this.userService.getNbUsersByTypeSang('AB_moins').subscribe(data => 
          { this.nb_users_ABmoins = data;
                      
      });
     
        this.getNbUsers();

      
       /* this.eventService.countEventByEvent().subscribe(data => 
          { console.log(data);     
            
          });*/
      }
      
    
  
  
}
