package com.instand.andrew.tumblr_android.API.BusinessLogic;

import com.github.scribejava.apis.TumblrApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;

import java.util.Scanner;

public class Auth {
    static String consumer_key = "B61NLyvSwHOtf2CRVLHkwXl0MTXlnJMb6hrn0OSzkjKCMazQp5";
    static String secret_key = "P60AoZyKj6cZpQygtbL1U02zF0skZBP721MEnHTHUZ7foXk89v";
    private static final String PROTECTED_RESOURCE_URL = "http://api.tumblr.com/v2/user/info";

    public void auth() {
        OAuthService service = new ServiceBuilder()
                .provider(TumblrApi.class)
                .apiKey(consumer_key)
                .apiSecret(secret_key)
                .callback("http://tumblrapp/")
                .build();
        Scanner in = new Scanner(System.in);
        Token requestToken = service.getRequestToken();

        System.out.println(service.getAuthorizationUrl(requestToken));

        Verifier verifier = new Verifier(in.nextLine());


        Token accessToken = service.getAccessToken(requestToken,
                verifier);

        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, service);
        service.signRequest(accessToken, request);
        Response response = request.send();

        System.out.println(response.getBody());

    }
}
