package org.example.tests.Integration.crud;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.endpoints.APIConstants;
import org.example.pojos.BookingRespons;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static org.assertj.core.api.Assertions.*;

public class testVerifyCreateBookingPOST01 extends BaseTest {

    @Owner("Promode")
    @TmsLink("https://bugz.atlassian.net/jira/software/projects/REQ/boards/1?selectedIssue=REQ-1")
    @Link(name = "Link to TC", url = "https://bugz.atlassian.net/browse/RBT-4")
    @Issue("JIRA_RBT-4")
    @Description("Verify that POST request is working fine.")
    @Test
    public void testVerifyCreateBookingPOST01(){

        requestSpecification
                .basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response =
                RestAssured
                        .given(requestSpecification)
                        .when().body(payloadManager.createPayloadBookingAsString()).post();


        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);


        //can use 1 out of below 3
        //Default Rest Assured - Validation -
        validatableResponse.body("booking.firstname", Matchers.equalTo("James"));


        BookingRespons bookingResponse = payloadManager.bookingResponseJava(response.asString());

        // AssertJ
        // assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"James");
        assertThat(bookingResponse.getBookingid()).isNotNull();
        assertThat(bookingResponse.getBooking().getFirstname()).isNotNull().isNotBlank();
        assertThat(bookingResponse.getBooking().getFirstname()).isEqualTo("James");


        // TestNG Assertions
        assertActions.verifyStatusCode(response, 200);
        assertActions.verifyResponseBody(bookingResponse.getBooking().getFirstname(), "James", "Verify the First Name");

    }

}
