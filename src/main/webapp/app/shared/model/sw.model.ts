export interface ISw {
  id?: number;
  text?: string;
}

export class Sw implements ISw {
  constructor(public id?: number, public text?: string) {}
}
