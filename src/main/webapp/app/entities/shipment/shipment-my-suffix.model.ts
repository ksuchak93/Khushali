import { BaseEntity } from './../../shared';

export class ShipmentMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public shipmentNo?: string,
        public bookingNo?: string,
        public etd?: any,
        public shipperCompany?: BaseEntity,
        public shipProducts?: BaseEntity[],
        public salesOrders?: BaseEntity[],
        public shipShipmentStatus?: BaseEntity,
    ) {
    }
}
