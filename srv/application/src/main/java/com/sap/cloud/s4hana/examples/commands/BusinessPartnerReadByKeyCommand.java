package com.sap.cloud.s4hana.examples.commands;


import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerByKeyFluentHelper;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

public class BusinessPartnerReadByKeyCommand extends ErpCommand<BusinessPartner> {

    private final ErpConfigContext erpConfigContext;
    private String businessPartner;
    private final BusinessPartnerService service;

    public BusinessPartnerReadByKeyCommand(
            ErpConfigContext erpConfigContext,
            String businessPartner,
            BusinessPartnerService service ) {
        super(BusinessPartnerReadByKeyCommand.class, erpConfigContext);
        this.businessPartner = businessPartner;
        this.erpConfigContext = erpConfigContext;
        this.service = service;
    }

    @Override
    protected BusinessPartner run() throws ODataException {
        return service
            .getBusinessPartnerByKey(businessPartner)
            .execute(erpConfigContext);
    }
}
