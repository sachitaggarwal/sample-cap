using API_BUSINESS_PARTNER as bp from './API_BUSINESS_PARTNER';

service BuPaService {
  @cds.persistence.skip
  entity BusinessPartner as projection on bp.A_BusinessPartnerType{
          BusinessPartner,
          LastName,
          FirstName,
          BusinessPartnerCategory
         };
}
