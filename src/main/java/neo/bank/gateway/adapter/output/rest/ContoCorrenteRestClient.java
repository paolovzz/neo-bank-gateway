package neo.bank.gateway.adapter.output.rest;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import neo.bank.gateway.adapter.input.rest.request.CreaContoCorrenteRequest;

@Path("/cc")
@RegisterRestClient(configKey = "conto-corrente-service")
public interface ContoCorrenteRestClient {

    @Path("/iban/{iban}")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response recuperaContoCorrenteDaIban(@PathParam(value = "iban") String iban);


    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response creaContoCorrente(CreaContoCorrenteRequest cmd);
}
