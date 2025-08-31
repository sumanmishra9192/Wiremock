import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockRuleTest {

    @Rule
    public WireMockRule wm = new WireMockRule(WireMockConfiguration.wireMockConfig().port(8081));

    @Before
    public void setUp() {
        wm.start();
        createStub();
    }

    @After
    public void tearDown() {
        wm.stop();
    }


    public void createStub() {
        stubFor(get(urlEqualTo("/v1/services"))
                .willReturn(aResponse().withStatus(200)
                        .withBody("{\n" +
                                "  \"id\" : 1,\n" +
                                "  \"name\" : \"Rahul\",\n" +
                                "  \"designation\" : \"software engineer\"\n" +
                                "}")
                        .withHeader("content-type","application/json")));
    }

    @Test
    public void verifyStub() {
        RestAssured.baseURI = "http://localhost:8081";

        Response response = RestAssured.given().log().all()
                .when().get("/v1/services");

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        response.prettyPrint();
    }
}
