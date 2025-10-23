package neo.bank.gateway.framework.adapter.input.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImpostaStatoCartaRequest {
    private String numeroCarta;
    private String iban;
    private boolean statoCarta;
}
