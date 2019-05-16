import { Moment } from 'moment';

export interface IZhaopin {
  id?: number;
  zwmc?: string;
  gsmc?: string;
  gzdd?: string;
  xzLow?: number;
  xzHeight?: number;
  ptime?: Moment;
  href?: string;
  cluster?: number;
}

export class Zhaopin implements IZhaopin {
  constructor(
    public id?: number,
    public zwmc?: string,
    public gsmc?: string,
    public gzdd?: string,
    public xzLow?: number,
    public xzHeight?: number,
    public ptime?: Moment,
    public href?: string,
    public cluster?: number
  ) {}
}
