package com.sap.cloud.s4hana.examples.commands;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerField;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerFluentHelper;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;
import com.sap.cloud.sdk.service.prov.api.request.OrderByExpression;
import org.slf4j.Logger;

import java.util.List;

public class BusinessPartnerReadCommand extends ErpCommand<List<BusinessPartner>> {

   
    private final int top;
    private final int skip;
    private final BusinessPartnerField<?>[] selectedProperties;
    private final ErpConfigContext erpConfigContext;
    private final BusinessPartnerService service;

    private static final Logger logger = CloudLoggerFactory
            .getLogger(BusinessPartnerReadCommand.class);

    public BusinessPartnerReadCommand(
            ErpConfigContext erpConfigContext,
            int top,
            int skip,
            List<String> properties,
            BusinessPartnerService service) {
        super(BusinessPartnerReadCommand.class, erpConfigContext);
        this.erpConfigContext = erpConfigContext;
        this.top = top;
        this.skip = skip;
        selectedProperties = properties.stream().
                map(property -> new BusinessPartnerField<>(property))
                .toArray(BusinessPartnerField<?>[]::new);

        this.service = service;
    }

    @Override
    protected List<BusinessPartner> run() throws ODataException {

        BusinessPartnerFluentHelper fluentHelper = service
            .getAllBusinessPartner()
            .orderBy(BusinessPartner.BUSINESS_PARTNER, Order.DESC)
            .select(selectedProperties);

        if (skip > 0) {
            fluentHelper.skip(skip);
        }

        if (top > 0) {
            fluentHelper.top(top);
        }

        return fluentHelper.execute(erpConfigContext);
    }
}

