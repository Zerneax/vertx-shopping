import { Injectable } from '@angular/core';
import { Client } from '../models/client';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private client: Client = undefined;

  constructor() { }

  public setClient(lastName_: string, firstName_: string): void {
    this.client = new Client(lastName_, firstName_);
  }

  public getClient(): Client {
    return this.client;
  }

  public isLogged(): boolean {
    return this.client ? true : false;
  }
}
