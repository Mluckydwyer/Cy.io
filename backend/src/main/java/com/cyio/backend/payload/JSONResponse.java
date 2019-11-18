package com.cyio.backend.payload;


import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Map;

public class JSONResponse {
    private boolean success;
    private Map<String, String> json;

    public JSONResponse(boolean success, Map<String, String> json) {
        this.success = success;
        this.json = json;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, String> getJson() {
        return json;
    }

    public void setJson(Map<String, String> json) {
        this.json = json;
    }
}