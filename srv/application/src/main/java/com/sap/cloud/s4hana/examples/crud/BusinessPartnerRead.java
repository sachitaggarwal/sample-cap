package com.sap.cloud.s4hana.examples.crud;


import com.sap.cloud.s4hana.examples.commands.BusinessPartnerReadByKeyCommand;
import com.sap.cloud.s4hana.examples.commands.BusinessPartnerReadCommand;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;
import com.sap.cloud.sdk.service.prov.api.operations.Query;
import com.sap.cloud.sdk.service.prov.api.operations.Read;
import com.sap.cloud.sdk.service.prov.api.request.QueryRequest;
import com.sap.cloud.sdk.service.prov.api.request.ReadRequest;
import com.sap.cloud.sdk.service.prov.api.response.QueryResponse;
import com.sap.cloud.sdk.service.prov.api.response.ReadResponse;

import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;


public class BusinessPartnerRead {

    private static final Logger logger = CloudLoggerFactory.getLogger(BusinessPartnerRead.class);

    private final BusinessPartnerService service = new DefaultBusinessPartnerService();

    @Read(serviceName = "BuPaService", entity = "BusinessPartner")
    public ReadResponse readSingleBusinessPartnerByKey(ReadRequest readRequest) {
        logger.debug("Received the following keys: {} ", readRequest.getKeys().entrySet().stream()
                .map(x -> x.getKey() + ":" + x.getValue())
                .collect(Collectors.joining(" | ")));

        String id = String.valueOf(readRequest.getKeys().get("BusinessPartner"));

        BusinessPartner partner =
                new BusinessPartnerReadByKeyCommand(new ErpConfigContext(), id, service).execute();

        ReadResponse readResponse = ReadResponse.setSuccess().setData(partner).response();

        return readResponse;
    }

    @Query(serviceName = "BuPaService", entity = "BusinessPartner")
    public QueryResponse queryCustomers(QueryRequest queryRequest) {

        List<BusinessPartner> businessPartners =
                new BusinessPartnerReadCommand(
                        new ErpConfigContext(),
                        queryRequest.getTopOptionValue(),
                        queryRequest.getSkipOptionValue(),
                        queryRequest.getSelectProperties(),
                        service
                ).execute();

        QueryResponse queryResponse = QueryResponse.setSuccess().setData(businessPartners).response();
        return queryResponse;
    }
}
