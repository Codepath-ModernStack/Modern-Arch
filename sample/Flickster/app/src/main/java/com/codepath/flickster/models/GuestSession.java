package com.codepath.flickster.models;

import org.json.JSONException;
import org.json.JSONObject;

public class GuestSession {
    boolean success;
    String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public boolean isSuccess() {
        return success;
    }

    public GuestSession(JSONObject jsonObject) throws JSONException {
        this.success = jsonObject.getBoolean("success");
        this.sessionId = jsonObject.getString("guest_session_id");
    }
}
