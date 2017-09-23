import { BaseEntity } from './../../shared';

export class ProductCategoryMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public categoryId?: string,
        public name?: string,
        public description?: string,
    ) {
    }
}
