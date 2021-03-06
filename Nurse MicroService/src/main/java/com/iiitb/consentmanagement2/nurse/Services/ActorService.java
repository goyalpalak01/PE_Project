package com.iiitb.consentmanagement2.nurse.Services;

import com.iiitb.consentmanagement2.nurse.Beans.Actor;
import com.iiitb.consentmanagement2.nurse.Beans.ROLE;
import com.iiitb.consentmanagement2.nurse.DAO.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.time.LocalTime;
import java.util.List;

@Named
public class ActorService {
    ActorRepository actorLoginRepo;

    @Autowired
    public ActorService(ActorRepository actorLoginRepo) {
        this.actorLoginRepo = actorLoginRepo;
    }

    public ROLE actorAuthentication(String email, String password) {
        System.out.println("[CLASS-METHOD] Inside ActorLoginService-actorAuthentication");
        List<Actor> actor = null;

        actor = actorLoginRepo.findByEmailIDAndPassword(email, password);

        if (actor.size() == 0) {
            System.out.println("[IF-COND] Inside actor authentication service, Size is 0 ");
            return ROLE.ROLE_INVALID;   // login failed
        }
        System.out.println("Username of actor is: " + actor.get(0).getFullName());

        if (actor.get(0).getEmailID().equals(email) && actor.get(0).getPassword().equals(password)) {

            System.out.println("[IF-COND] Inside email and password match");

            return actor.get(0).getRole(); // login success
        }
        return ROLE.ROLE_INVALID;   //login failed
    }


    public ROLE getActorRole(String email) {
        System.out.println("[ActorService]: Inside getActorRole() ");
        List<Actor> actor = null;

        actor = actorLoginRepo.findByEmailID(email);
        if (actor.size() == 0)
            return ROLE.ROLE_INVALID;
        return actor.get(0).getRole();

    }

    public LocalTime getActorEndTime(String email) {
        List<Actor> actor = null;
        actor = actorLoginRepo.findByEmailID(email);        // emailid is unique.
        if (actor.size() == 0)
            return LocalTime.MIN;   // Invalid localdatetime

        return actor.get(0).getEndTime();
    }

    public LocalTime getActorLoginTime(String email) {
        List<Actor> actor = null;
        actor = actorLoginRepo.findByEmailID(email);        // emailid is unique.

        if (actor.size() == 0)
            return LocalTime.MIN;   // Invalid localdatetime because invalid login time

        return actor.get(0).getLoginTime();
    }

    public String getActorID(String email) {
        List<Actor> actor = null;
        actor = actorLoginRepo.findByEmailID(email);        // emailid is unique.

        if (actor.size() == 0)
            return "NO_ID_FOUND";   // Invalid localdatetime because invalid login time

        return actor.get(0).getActorID();
    }

    public String setActorLoginTime(String email, LocalTime loginTime) {
        // actor email is also unique field
        System.out.println("Setting login time of actor logged in");

        List<Actor> actor = null;
        actor = actorLoginRepo.findByEmailID(email);        // emailid is unique.

        if (actor.size() == 0)
            return "NO_ACTOR_FOUND";   // Invalid localdatetime because invalid login time
        else {
            actor.get(0).setLoginTime(loginTime);

            actorLoginRepo.save(actor.get(0));
            System.out.println("After saving login time successfully -- inside setactor login time");
        }
        return "SUCCESSFULLY_SET_LOGIN_TIME";
    }
}
