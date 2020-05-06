// THIS CLASS IS COPIED FROM
// https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-06/src/main/java/org/tsdes/intro/exercises/quizgame/ejb/DefaultDataInitializerEjb.java

package no.kristiania.exam.pg5100.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;


@Service
public class DefaultDataInitializerService {


    @PostConstruct
    public void initialize() {

    }



    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }
}
