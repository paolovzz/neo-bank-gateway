package neo.bank.gateway.adapter.input.rest.request;

import lombok.Value;

@Value
public class ImpostaSogliaBonificoRequest {
    private String iban;
    private int nuovaSoglia;
}
