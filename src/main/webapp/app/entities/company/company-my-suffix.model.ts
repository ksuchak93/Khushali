import { BaseEntity } from './../../shared';

export class CompanyMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public companyName?: string,
        public addresses?: BaseEntity[],
        public companyCategories?: BaseEntity[],
    ) {
    }
}
