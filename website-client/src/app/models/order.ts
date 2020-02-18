export class Order {
  public id: string;
  public client: string;
  public product: string;
  public statut: string;

  constructor(id_: string, client_: string, product_: string, statut_: string) {
    this.id = id_;
    this.client = client_;
    this.product = product_;
    this.statut = statut_;
  }
}
