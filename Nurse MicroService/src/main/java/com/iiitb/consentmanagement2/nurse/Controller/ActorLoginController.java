package com.iiitb.consentmanagement2.nurse.Controller;

import com.iiitb.consentmanagement2.nurse.Beans.ROLE;
import com.iiitb.consentmanagement2.nurse.HelperClasses.ActorLogin;
import com.iiitb.consentmanagement2.nurse.Services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.Response;

import java.time.LocalTime;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ActorLoginController {
    ActorService actorService;

    @Autowired
    public ActorLoginController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping(path = "/actorlogin", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Response authenticateActor(@RequestBody ActorLogin actorLogin) {
        System.out.println("[CLASS] Inside ActorLoginController: authenticateActor() ");
        System.out.println("Email: " + actorLogin.getEmail());
        System.out.println("Password: " + actorLogin.getPassword());

        ROLE userRole = actorService.actorAuthentication(actorLogin.getEmail(), actorLogin.getPassword());
        String setLoginTime = "LOGIN_TIME_NOT_SET"; // time is not yet set

        if (userRole == ROLE.ROLE_INVALID)
            return Response.status(401).build();

        if (userRole != ROLE.ROLE_INVALID) {
            System.out.println("Going to set login time of logged in actor");
            setLoginTime = actorService.setActorLoginTime(actorLogin.getEmail(), LocalTime.now());

        }
        if (!setLoginTime.equals("SUCCESSFULLY_SET_LOGIN_TIME"))
            return Response.status(401).build();

        System.out.println("Actor authenticated successfully and login time is set");

        return Response
                .status(Response.Status.OK)
                .entity(userRole)
                .build();
    }
}
