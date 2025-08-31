import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class CreateMapping {

    //start wiremock server
    //create stub
    // test through RA

    public void createStub() {
        stubFor(WireMock.get(urlEqualTo("/api/services"))
                .willReturn(aResponse().withStatus(200)
                .withBody("This is sample response")
                        .withHeader("content-type", "application/json")
                ));
    }

    @Test
    public void verifyStub() {
        createStub();
        RestAssured.baseURI = "http://localhost:8080";

        Response response = RestAssured.given()
                .log().all()
                .when()
                .get("/api/services")
                        .then().log().all().extract().response();

        System.out.println(response.statusCode());
        //System.out.println(response.body().prettyPrint());

    }
}
