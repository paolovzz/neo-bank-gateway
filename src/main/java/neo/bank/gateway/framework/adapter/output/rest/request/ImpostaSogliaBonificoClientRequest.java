package neo.bank.gateway.framework.adapter.output.rest.request;

import lombok.Value;

@Value
public class ImpostaSogliaBonificoClientRequest {
    private String iban;
    private String usernameCliente;
    private int nuovaSoglia;
}
