package neo.bank.gateway.framework.adapter.input.rest.request;

import lombok.Value;

@Value
public class ImpostaSogliaBonificoRequest {
    private String iban;
    private int nuovaSoglia;
}
