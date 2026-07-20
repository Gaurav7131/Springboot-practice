package com.example.firstspringbootex;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "featureToggle")
public class CustomFeatureEndpoint {

    private Map<String, Boolean> features = new HashMap<>();

    public CustomFeatureEndpoint() {
        // Adding some default data for demonstration
        features.put("newUI", true);
        features.put("betaTesting", false);
    }

    // Maps to HTTP GET request
    @ReadOperation
    public Map<String, Boolean> getAllFeatures() {
        return features;
    }

    // Maps to HTTP POST request
    @WriteOperation
    public void updateFeature(String featureName, boolean isEnabled) {
        features.put(featureName, isEnabled);
    }

    // Maps to HTTP DELETE request
    @DeleteOperation
    public void deleteFeature(String featureName) {
        features.remove(featureName);
    }
}