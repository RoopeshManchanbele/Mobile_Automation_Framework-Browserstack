package org.roopesh.pageTestData;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.roopesh.BasePage;
import org.roopesh.helpers.json.TestData;
import org.roopesh.helpers.json.TestDataHelper;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Getter
@Data
public class LoginData extends BasePage {

    private final String mobileNumber;
    private final String otp;
    private final String pin;
    private final String clientID;

    public LoginData() {
        TestData testData = TestDataHelper.getJSONData(this);
        Map<String, String> userData = testData.getTestData().get("userData");
        mobileNumber = userData.get("mobileNumber");
        otp = userData.get("otp");
        pin = userData.get("pin");
        clientID = userData.get("clientID");
    }

}
