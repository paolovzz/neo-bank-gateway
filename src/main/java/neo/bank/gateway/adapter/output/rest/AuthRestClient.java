package neo.bank.gateway.adapter.output.rest;


import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import neo.bank.gateway.adapter.input.rest.request.LoginUtenteRequest;
import neo.bank.gateway.adapter.input.rest.request.RegistraUtenteRequest;

@Path("/auth")
@RegisterRestClient(configKey = "auth-service")
public interface AuthRestClient {

    @POST
    @Path("/registra-utente")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registraUtente(@RequestBody RegistraUtenteRequest request);
    

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@RequestBody LoginUtenteRequest request);

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(@HeaderParam("Authorization") String authorizationHeader);

}
