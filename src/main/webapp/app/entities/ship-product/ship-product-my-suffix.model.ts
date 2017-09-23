import { BaseEntity } from './../../shared';

export class ShipProductMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public quantity?: string,
        public netWeight?: number,
        public grossWeight?: number,
        public packege?: string,
        public shipment?: BaseEntity,
        public salesOrderProduct?: BaseEntity,
    ) {
    }
}
