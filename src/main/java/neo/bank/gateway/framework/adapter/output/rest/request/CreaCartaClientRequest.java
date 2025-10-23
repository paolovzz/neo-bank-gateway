package neo.bank.gateway.framework.adapter.output.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreaCartaClientRequest {
    

    private String username;
    private String iban;
}
