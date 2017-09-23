import { BaseEntity } from './../../shared';

export class ContactMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public title?: string,
        public department?: string,
        public email?: string,
        public officePhone?: string,
        public mobile?: string,
        public homePhone?: string,
        public fax?: string,
        public whatsAppId?: string,
        public company?: BaseEntity,
        public contactCategory?: BaseEntity,
        public addresses?: BaseEntity[],
    ) {
    }
}
