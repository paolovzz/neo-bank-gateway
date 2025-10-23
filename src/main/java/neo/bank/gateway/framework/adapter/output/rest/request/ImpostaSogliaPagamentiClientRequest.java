package neo.bank.gateway.framework.adapter.output.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImpostaSogliaPagamentiClientRequest {
    private String numeroCarta;
    private String iban;
    private String usernameCliente;
    private int nuovaSoglia;
}
