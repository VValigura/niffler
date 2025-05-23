package guru.qa.niffler.model.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenJson(@JsonProperty("access_token") String accessToken,
                        @JsonProperty("scope") String scope,
                        @JsonProperty("id_token") String idToken,
                        @JsonProperty("token_type") String tokenType,
                        @JsonProperty("expires_in") int expiresIn)

                         {



}
