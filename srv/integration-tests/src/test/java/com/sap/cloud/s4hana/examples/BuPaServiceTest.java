package com.sap.cloud.s4hana.examples;

import com.sap.cloud.s4hana.examples.commands.BusinessPartnerReadByKeyCommand;
import com.sap.cloud.s4hana.examples.commands.BusinessPartnerReadCommand;
import com.sap.cloud.s4hana.examples.crud.BusinessPartnerRead;
import com.sap.cloud.sdk.testutil.MockUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.*;

import java.net.URL;

import static io.restassured.RestAssured.when;

@RunWith(Arquillian.class)
public class BuPaServiceTest {

    private static final MockUtil mockUtil = new MockUtil();
    public static final String BUPA_ID = "1003764";

    @ArquillianResource
    private URL baseUrl;

    @Deployment
    public static WebArchive createDeployment() {
        return TestUtil.createDeployment(
                BusinessPartnerRead.class,
                org.apache.olingo.odata2.core.servlet.ODataServlet.class
        );
    }


    @BeforeClass
    public static void beforeClass() {
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    @Before
    public void before() {
        RestAssured.baseURI = baseUrl.toExternalForm();
        RestAssured.urlEncodingEnabled = false;
    }

    @Test
    @Ignore
    public void testGetAll() {
        String path = "/odata/v2/BuPaService/BusinessPartner?$format=json";
        when()
                .get(path)
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    @Ignore
    public void testGetSingle() {
        String path = "/odata/v2/BuPaService/BusinessPartner('{id}')?$format=json";
        when()
                .get(path, BUPA_ID)
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
