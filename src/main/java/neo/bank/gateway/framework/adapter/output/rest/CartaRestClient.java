package neo.bank.gateway.framework.adapter.output.rest;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import neo.bank.gateway.framework.adapter.output.rest.request.CreaCartaClientRequest;
import neo.bank.gateway.framework.adapter.output.rest.request.ImpostaAbilitazionePagamentiOnlineClientRequest;
import neo.bank.gateway.framework.adapter.output.rest.request.ImpostaSogliaPagamentiClientRequest;
import neo.bank.gateway.framework.adapter.output.rest.request.ImpostaStatoCartaClientRequest;

@Path("/carte")
@RegisterRestClient(configKey = "carta-service")
public interface CartaRestClient {

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response creaCarta(@HeaderParam("X-Authenticated-User") String authenticatedUser, CreaCartaClientRequest request);

    @Path("/{numeroCarta}")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response recuperaCartaDaNumeroCarta(@HeaderParam("X-Authenticated-User") String authenticatedUser, @PathParam(value = "numeroCarta") String numeroCarta);

    @Path("/soglia-pagamenti-giornaliera")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaSogliaPagamentiGiornaliera(@HeaderParam("X-Authenticated-User") String authenticatedUser, ImpostaSogliaPagamentiClientRequest request);

    @Path("/soglia-pagamenti-mensile")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaSogliaPagamentiMensile(@HeaderParam("X-Authenticated-User") String authenticatedUser, ImpostaSogliaPagamentiClientRequest request);

    @Path("/abilitazione-pagamenti-online")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaAbilitazionePagamentiOnline(@HeaderParam("X-Authenticated-User") String authenticatedUser, ImpostaAbilitazionePagamentiOnlineClientRequest request);

    @Path("/stato-carta")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaStatoCarta(@HeaderParam("X-Authenticated-User") String authenticatedUser, ImpostaStatoCartaClientRequest request);
    
    @Path("/iban/{iban}")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response recuperaCarteDaIban(@HeaderParam("X-Authenticated-User") String authenticatedUser, @PathParam(value = "iban") String iban);
}
