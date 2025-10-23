package neo.bank.gateway.framework.adapter.input.rest.request;

import lombok.Value;

@Value
public class CreaCartaRequest {
    private String iban;
}
