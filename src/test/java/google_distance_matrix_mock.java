import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class google_distance_matrix_mock {
//    @Rule
//    public WireMockRule wm = new WireMockRule(WireMockConfiguration.wireMockConfig().port(8081).httpsPort(2424).needClientAuth(false).trustAllProxyTargets(true).usingFilesUnderDirectory("" +
//            "src/test/resources"));
WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.
        options().httpsPort(2424)
                .needClientAuth(false)
                .httpDisabled(true)
                .trustAllProxyTargets(true) // trust all certs
);
    public void setUp(){
        wireMockServer.start();}

    public void createStub(){

        wireMockServer.stubFor(get(urlPathEqualTo("/maps/api/distancematrix/json"))
                .withQueryParam("origins", equalTo("Seattle"))
                .withQueryParam("destinations", equalTo("San Francisco"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("direction.json")));

    }

    @Test
    public void testDirection(){
        setUp();
        createStub();
        //RestAssured.baseURI = "http://localhost:8081";
        RestAssured.useRelaxedHTTPSValidation();
        Response response =  RestAssured.given().log().all()
                .when().baseUri("https://localhost:2424/maps/api/distancematrix/json").queryParam("origins","Seattle")
                .queryParam("destinations","San Francisco")
                .get();

        System.out.println(response.getStatusCode());
        response.prettyPrint();
    }
}
