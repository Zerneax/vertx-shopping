export class Client {
  public name: string;

  constructor(lastName_: string, firstName_: string) {
    this.name = `${firstName_} ${lastName_}`;
  }
}
