import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Event } from 'app/model/event';

@Injectable({
  providedIn: 'root'
})

 
export class EventService {

  sDate : string="";
  eDate : string="";
 
  baseURL = "http://localhost:8080/api/event";
  constructor(private httpClient: HttpClient) {
   }

  saveEvent(event : Event){
    return this.httpClient.post(`${this.baseURL}/save`,event);
  }

  getAllEvents() {
    
    return this.httpClient.get<Event[]>(`${this.baseURL}/events`);
  }

  deleteEvent(id : any){
    
    return this.httpClient.delete(`${this.baseURL}/delete/${id}`)
  }

  getById(id : any){
    return this.httpClient.get<Event>(`${this.baseURL}/${id}`)
  }

  updateEvent(event : Event){
    return this.httpClient.post<Event>(`${this.baseURL}/update`,event);
  }

/*  getNbEvent(sDate : string , eDate : string) : Observable<any>{
    return this.httpClient.get<number>(`${this.baseURL}/countByDate`,sDate,eDate);
  }

  countEventByEvent() {

   let params = new HttpParams()
    .set('sDate', 2022 +'-' + 12 + '-'+ 0o1 + 'T' + 10 + ':' + 10 + ':'+ 41.808 + 'Z' )
    .set('eDate', 2022 +'-' + 12 + '-'+ 31 + 'T' + 10 + ':' + 10 + ':'+ 41.808 + 'Z' );

    console.log(params);
    console.log(`${this.baseURL}/countByDate`)
    return this.httpClient.get<number>(`${this.baseURL}/countByDate`);
  }*/
}
