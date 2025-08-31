import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class GetMapping {

    public void createStub() {
        stubFor(get(urlEqualTo("/v1/players"))
                .willReturn(aResponse().withStatus(200)
                        .withBodyFile("employee.json")
                        .withHeader("content-type", "application/json")));
    }

    @Test
    public void verifyStub() {
        createStub();
        RestAssured.baseURI = "http://localhost:8080";

       Response response =  RestAssured.given().log().all()
                .when()
                .get("v1/players");

        System.out.println(response.getStatusCode());
        response.prettyPrint();
    }
}
