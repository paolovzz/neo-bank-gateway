package neo.bank.gateway.adapter.output.rest;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import neo.bank.gateway.adapter.input.rest.request.CreaContoCorrenteRequest;
import neo.bank.gateway.adapter.output.rest.request.ImpostaSogliaBonificoClientRequest;
import neo.bank.gateway.adapter.output.rest.request.InviaBonificoClientRequest;

@Path("/cc")
@RegisterRestClient(configKey = "conto-corrente-service")
public interface ContoCorrenteRestClient {

    @Path("/{iban}")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response recuperaContoCorrenteDaIban(@PathParam(value = "iban") String iban);


    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response creaContoCorrente(CreaContoCorrenteRequest cmd);

    @Path("/soglia-bonifico-giornaliera")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaSogliaBonificoGiornaliera(ImpostaSogliaBonificoClientRequest request);

    @Path("/soglia-bonifico-mensile")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaSogliaBonificoMensile(ImpostaSogliaBonificoClientRequest request);


    @Path("/predisponi-bonifico")
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response predisponiBonifico(InviaBonificoClientRequest request);
    
}
