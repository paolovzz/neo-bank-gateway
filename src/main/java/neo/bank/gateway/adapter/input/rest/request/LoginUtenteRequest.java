package neo.bank.gateway.adapter.input.rest.request;

import lombok.Value;

@Value
public class LoginUtenteRequest {

    private String username;
    private String password;
    
}
