package com.sap.cloud.s4hana.examples.commands;


import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;

import java.net.URI;
import java.util.List;

import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerField;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerFluentHelper;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerSelectable;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.service.prov.api.request.OrderByExpression;
import com.sap.cloud.sdk.testutil.MockUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BusinessPartnerReadCommandTest {

    private MockUtil mockUtil;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private BusinessPartnerService service;

    @Mock
    private BusinessPartnerFluentHelper helper;

    @Mock
    private BusinessPartner alice;

    @Before
    public void before() {
        mockUtil = new MockUtil();
        mockUtil.mockDefaults();
        mockUtil.mockDestination("ErpQueryEndpoint", URI.create(""));
    }

    private OngoingStubbing<List<BusinessPartner>> mockService() throws ODataException  {
        return when(service
                .getAllBusinessPartner()
                .orderBy(BusinessPartner.BUSINESS_PARTNER, Order.DESC)
                .select(any(BusinessPartnerSelectable.class))
                .execute(any(ErpConfigContext.class)));
    }

    @Test
    public void testGetAll() throws ODataException {
        mockService()
                .thenReturn(Lists.newArrayList(alice));

        List<String> selected = Lists.newArrayList("BusinessPartner", "FirstName", "LastName");
        final List<BusinessPartner> result =
                new BusinessPartnerReadCommand(
                        new ErpConfigContext(),
                    0,
                    0,
                    selected,
                    service)
                .execute();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(alice);
    }


}
