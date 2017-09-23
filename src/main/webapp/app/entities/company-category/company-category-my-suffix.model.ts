import { BaseEntity } from './../../shared';

export class CompanyCategoryMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public categoryId?: string,
        public name?: string,
        public description?: string,
    ) {
    }
}
