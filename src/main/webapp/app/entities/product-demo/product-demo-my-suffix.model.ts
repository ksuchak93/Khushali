import { BaseEntity } from './../../shared';

export const enum WeightUnit {
    'GRAMS',
    'KG'
}

export class ProductDemoMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public prodcutId?: string,
        public name?: string,
        public details?: string,
        public fetures?: string,
        public size?: string,
        public unitWeight?: number,
        public uintInSotck?: number,
        public unitAvailable?: number,
        public unitBlocked?: number,
        public unitShipped?: number,
        public reorderLevel?: number,
        public weightUinit?: WeightUnit,
        public productCategory?: BaseEntity,
        public salesOrders?: BaseEntity[],
    ) {
    }
}
