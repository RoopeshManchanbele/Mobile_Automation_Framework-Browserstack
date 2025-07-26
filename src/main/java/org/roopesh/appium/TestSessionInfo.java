package org.roopesh.appium;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestSessionInfo {

    private String sessionId;
    private String platform;
    private String deviceName;
    private String testName;

    // Constructor to initialize session details
    public TestSessionInfo(String sessionId, String platform, String deviceName, String testName) {
        this.sessionId = sessionId;
        this.platform = platform;
        this.deviceName = deviceName;
        this.testName = testName;
    }

}
