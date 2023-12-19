import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { User } from 'app/model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  
  baseURL = "http://localhost:8080/api/user";

  constructor(private httpClient: HttpClient
  ) { }

  getAllUsers(): Observable<User[]>{
    
   return this.httpClient.get<User[]>(`${this.baseURL}/users`);
  }

  getUserById(id : number){
    
    return this.httpClient.get<User>(`${this.baseURL}/${id}`);
  }
  deleteUser(id : any){
    
    return this.httpClient.delete(`${this.baseURL}/delete/${id}`)
  }

  getNbUsers(){
    return this.httpClient.get<number>(`${this.baseURL}/nbUsers`);
    
  }

  getNbUsersByTypeSang(typeSang : string){
    return this.httpClient.get<number>(`${this.baseURL}/countByTypeSang/${typeSang}`);
    
    
  }
}
