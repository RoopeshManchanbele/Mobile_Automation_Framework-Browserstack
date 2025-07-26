package org.roopesh.helpers.json;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Setter
@Getter
@Data
public class TestData {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestData.class);

    private Map<String, Map<String, String>> testData;

}
