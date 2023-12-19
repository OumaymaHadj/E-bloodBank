import { Event } from "./event";

export class Notifiacation{
    id : number;
    createdAt : string;
    event : Event;
    public set eventId(id : number){
        this.event.id = id ;
    }
    
}