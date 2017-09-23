import { BaseEntity } from './../../shared';

export class AddressMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public addressLineOne?: string,
        public addressLineTwo?: string,
        public city?: string,
        public state?: string,
        public code?: string,
        public country?: BaseEntity,
    ) {
    }
}
