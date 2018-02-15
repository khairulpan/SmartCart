package com.ashrafazmi.smartcart;

/**
 * Created by ashraf on 08/11/2017.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://hybridlocker.000webhostapp.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String fullname, String user, String email, String code, Response.Listener<String> listener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("fullname", fullname);
        params.put("user", user);
        params.put("email", email);
        params.put("code", code);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
