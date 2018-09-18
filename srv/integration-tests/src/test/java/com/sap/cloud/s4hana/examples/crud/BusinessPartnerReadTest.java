package com.sap.cloud.s4hana.examples.crud;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import com.sap.cloud.sdk.cloudplatform.servlet.RequestContextExecutor;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.service.prov.api.operations.Read;
import com.sap.cloud.sdk.service.prov.api.request.QueryRequest;
import com.sap.cloud.sdk.service.prov.api.request.ReadRequest;
import com.sap.cloud.sdk.service.prov.api.response.QueryResponse;
import com.sap.cloud.sdk.service.prov.api.response.impl.QueryResponseImpl;
import com.sap.cloud.sdk.service.prov.api.response.impl.ReadResponseImpl;
import com.sap.cloud.sdk.testutil.MockUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BusinessPartnerReadTest {
    private static final MockUtil mockUtil = new MockUtil();

    @Mock
    private QueryRequest queryRequest;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ReadRequest readRequest;

    private BusinessPartnerRead businessPartnerRead;

    public static final String BUPA_ID = "1003764";

    @BeforeClass
    public static void beforeClass() {
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    @Before
    public void before() {
        businessPartnerRead = new BusinessPartnerRead();
    }

    @Test
    public void testQueryCustomers() throws Exception {
        new RequestContextExecutor().execute(( ) -> {
            QueryResponseImpl queryResponse = (QueryResponseImpl) businessPartnerRead
                    .queryCustomers(queryRequest);

            List<?> result = queryResponse.getPojoData();
            assertThat(not(result.isEmpty()));
        });
    }

    @Test
    public void testreadSingleBusinessPartnerTest() throws Exception {
        new RequestContextExecutor().execute(( ) -> {
            when(readRequest.getKeys().get("BusinessPartner")).thenReturn(BUPA_ID);

            businessPartnerRead.readSingleBusinessPartnerByKey(readRequest);
            ReadResponseImpl readResponse = (ReadResponseImpl) businessPartnerRead
                    .readSingleBusinessPartnerByKey(readRequest);

            BusinessPartner result = (BusinessPartner) readResponse.getData();
            assertThat(result.getBusinessPartner()).isEqualTo(BUPA_ID);
        });
    }
}
