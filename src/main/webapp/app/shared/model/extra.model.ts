export interface IExtra {
    id?: number;
    key?: string;
    value?: any;
}

export class Extra implements IExtra {
    constructor(public id?: number, public key?: string, public value?: any) {}
}
