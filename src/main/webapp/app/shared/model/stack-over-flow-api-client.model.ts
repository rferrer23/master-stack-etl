import { Moment } from 'moment';
import { IExtra } from 'app/shared/model/extra.model';

export interface IStackOverFlowApiClient {
    id?: number;
    users?: string;
    firstPeriod?: Moment;
    lastPeriod?: Moment;
    tags?: string;
    nextSendTime?: Moment;
    active?: boolean;
    secret?: string;
    extras?: IExtra[];
}

export class StackOverFlowApiClient implements IStackOverFlowApiClient {
    constructor(
        public id?: number,
        public users?: string,
        public firstPeriod?: Moment,
        public lastPeriod?: Moment,
        public tags?: string,
        public nextSendTime?: Moment,
        public active?: boolean,
        public secret?: string,
        public extras?: IExtra[]
    ) {
        this.active = this.active || false;
    }
}
