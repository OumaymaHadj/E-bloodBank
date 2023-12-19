import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Notifiacation } from '../model/notification';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  baseURL = "http://localhost:8080/api/notification";

  constructor(private httpClient: HttpClient) { }

  saveNotif(notif : Notifiacation){
    return this.httpClient.post(`${this.baseURL}/save`,notif);
  }

}
