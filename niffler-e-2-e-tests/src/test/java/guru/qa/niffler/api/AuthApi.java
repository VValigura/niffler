package guru.qa.niffler.api;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.niffler.model.oauth.TokenJson;
import retrofit2.Call;
import retrofit2.http.*;

public interface AuthApi {



    //---------------------------------------
    //http://127.0.0.1:9000/oauth2/authorize
    // ?response_type=code
    // &client_id=client
    // &scope=openid
    // &redirect_uri=http://127.0.0.1:3000/authorized
    // &code_challenge=b2sNRnnSfvG0S9DdHBYgd-qYI9JK1YNaNcVq1YmM3gU
    // &code_challenge_method=S256


    @GET("oauth2/authorize")
    Call<Void> preRequest(@Query("response_type") String responseType,
                          @Query("client_id") String clientId,
                          @Query("scope") String scope,
                          @Query(value = "redirect_uri", encoded = true) String redirectUri,
                          @Query("code_challenge") String codeChallenge,
                          @Query("code_challenge_method") String codeChallengeMethod);

    //response - 302 Found -> http://127.0.0.1:9000/login
    //set-cookie: JSESSIONID=443BE908CBD1748A576463D712266558

    //Auto-redirect

    //http://127.0.0.1:9000/login
    //200 OK
    //set-cookie: XSRF-TOKEN=a06b0946-dac7-43cb-bdaf-5e93a90f99d8

    @POST("login")
    @FormUrlEncoded
    Call<Void> login(@Field("_csrf") String csrf,
                            @Field("username") String username,
                            @Field("password") String password);

    //---------------------------------------
    //POST http://127.0.0.1:9000/login
    //JSESSIONID=7AE48B10A948F35469E3D4342979221B

    //URL ENCODED _csrf a06b0946-dac7-43cb-bdaf-5e93a90f99d8
    //URL ENCODED username Vova5
    //URL ENCODED password Vova59767

    //response - 302 Found -> http://127.0.0.1:9000/oauth2/authorize
    // ?response_type=code
    // &client_id=client
    // &scope=openid
    // &redirect_uri=http://127.0.0.1:3000/authorized
    // &code_challenge=b2sNRnnSfvG0S9DdHBYgd-qYI9JK1YNaNcVq1YmM3gU
    // &code_challenge_method=S256
    // &continue

    //set-cookie: JSESSIONID=7AE48B10A948F35469E3D4342979221B; Path=/
    //set-cookie: XSRF-TOKEN=; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:10 GMT; Path=/

    //Auto-redirect
    //cookie: JSESSIONID=7AE48B10A948F35469E3D4342979221B
    //response - 302 Found -> http://127.0.0.1:3000/authorized
    //?code=ocGMnKjn7_HwteqpPhHuK0B6Vh5mv8PV_kIuRB0j-Z7QCsLBDWmciS_n-y1pOFkuTG80dI-xWoo4kxLAHzYS--fA6QdvbE5k7ShO1tDAlJ0n3P7E8XvS9C3YrVqcJ4k8

    //Auto-redirect
    //JSESSIONID=7AE48B10A948F35469E3D4342979221B
    //200 OK


    @POST("oauth2/token")
    @FormUrlEncoded
    Call<TokenJson>  token (@Field("code") String code,
                               @Field(value = "redirect_uri", encoded = true ) String redirectUri,
                               @Field("code_verifier") String codeVerifier,
                               @Field("grant_type") String grantType,
                               @Field("client_id") String clientId);
    //---------------------------------------
    //POST http://127.0.0.1:9000/oauth2/token
    //URL ENCODED code: ocGMnKjn7_HwteqpPhHuK0B6Vh5mv8PV_kIuRB0j-Z7QCsLBDWmciS_n-y1pOFkuTG80dI-xWoo4kxLAHzYS--fA6QdvbE5k7ShO1tDAlJ0n3P7E8XvS9C3YrVqcJ4k8
    //URL ENCODED redirect_uri: http://127.0.0.1:3000/authorized
    //URL ENCODED code_verifier: p557mignilcTW3LOtLKg8nUa0QXyW56fvg5jXa0Pfsg
    //URL ENCODED grant_type: authorization_code
    //URL ENCODED client_id: client
    //200 OK
    //{
    //    "access_token": "eyJraWQiOiJhZDJjMzEyMC01MmRjLTQ3NzktODk5Ny1iZjMwYmEyYTFmNTgiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJWb3ZhNSIsImF1ZCI6ImNsaWVudCIsIm5iZiI6MTc0NzczNTU5MSwic2NvcGUiOlsib3BlbmlkIl0sImlzcyI6Imh0dHA6Ly8xMjcuMC4wLjE6OTAwMCIsImV4cCI6MTc0NzczNTg5MSwiaWF0IjoxNzQ3NzM1NTkxLCJqdGkiOiIwZDllNjAwYy1jN2FhLTQzMjktOGI2Mi1mODIxMDFjNzc3MWYifQ.gvD008T9YbL9I_nur5VZfazNH-B292VU-WMPYJqL-6GzInG7OSUrXk4fHL17X1RZgUstnCCdhwfWo2iRe9Ts8CdTksUUFhpqCEj7SyWk1LWZ-xlrqdvB1bHsFAw95-x50IiMOkl8nynWdLkOFMl9q1KGmREHlk6yXwSBgupQJVQgKR45Pb9c91eBBwFaYZMYnQFj7Gbgfy1197_IbGj10x8jrATvstAJyyAhohldlufJ0x-6hbzu0Flz6ENUskxSNXPxD0W2hNoHdwxMFE9f0UN6tAcxvI9jkPQFBSAtxwJieNe_2z9pe0_2nNiwmweaXaKWmv5SlFaowEN946kt6A",
    //    "scope": "openid",
    //    "id_token": "eyJraWQiOiJhZDJjMzEyMC01MmRjLTQ3NzktODk5Ny1iZjMwYmEyYTFmNTgiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJWb3ZhNSIsImF1ZCI6ImNsaWVudCIsImF6cCI6ImNsaWVudCIsImF1dGhfdGltZSI6MTc0NzczNTU5MCwiaXNzIjoiaHR0cDovLzEyNy4wLjAuMTo5MDAwIiwiZXhwIjoxNzQ3NzM3MzkxLCJpYXQiOjE3NDc3MzU1OTEsImp0aSI6IjUyZjU4NTQxLWE5ODAtNGQ2OS1hYmQxLWQ1Y2Q5ZDRhOGI5ZSIsInNpZCI6IlNmb0EwdFZMSU41NEtvNTF6NUlIa1JfRTd6UW1NanVvREp6T0p3VzI5U1UifQ.N7IELjd9JOmC7M8yM70u5BBjOzh6uxHUT6fY66EGqc_tJedoCRe1k2jGGG2zquNE2n4rryo5dGH9wg5ikot8vIZ0WtWsmqel4M33JRACcMtvywcYoYnENlTy_eEQv1EbPQ2AFFfvwSI_GNa1Rqac3Fr06j714nxW9Ena31lsG6h5OQSwALhd425Nu4mwFS_JQ_gS6zd2C9GRbbzFBPbxHxc0OL8ZoOnoxObiiUNDyLR_jGeKVxP4RX5Dy01DiXyc3kg_auSNdRJO3y_sh85NBsjobhgl897eboEchKgAEwvLOiouOao2EBGJmcL20XXmCV565yS4gCkF1sNdIAyVhw",
    //    "token_type": "Bearer",
    //    "expires_in": 300
    //}







}
