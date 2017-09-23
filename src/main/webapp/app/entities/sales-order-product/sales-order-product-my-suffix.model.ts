import { BaseEntity } from './../../shared';

export class SalesOrderProductMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public quantity?: string,
        public unitPrice?: number,
        public discount?: number,
        public total?: number,
        public fulfilled?: boolean,
        public shippedQuantity?: number,
        public shipDate?: any,
        public salesOrder?: BaseEntity,
        public product?: BaseEntity,
    ) {
        this.fulfilled = false;
    }
}
