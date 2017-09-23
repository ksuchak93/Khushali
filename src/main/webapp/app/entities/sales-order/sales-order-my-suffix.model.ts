import { BaseEntity } from './../../shared';

export const enum OrderStatus {
    'DRAFT',
    'FULLFILLED',
    'CONFIRMED',
    'CANCELLED'
}

export class SalesOrderMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public orderNumber?: string,
        public orderDate?: any,
        public shipDate?: any,
        public status?: OrderStatus,
        public salesOrderProducts?: BaseEntity[],
        public customer?: BaseEntity,
        public shipment?: BaseEntity,
    ) {
    }
}
