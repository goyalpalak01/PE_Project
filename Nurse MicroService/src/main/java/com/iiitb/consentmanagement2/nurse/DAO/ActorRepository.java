package com.iiitb.consentmanagement2.nurse.DAO;


import com.iiitb.consentmanagement2.nurse.Beans.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    List<Actor> findByEmailIDAndPassword(String email, String password);

    List<Actor> findByEmailID(String email);
}
