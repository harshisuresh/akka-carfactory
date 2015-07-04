package models.actors;

import models.domain.CoachWork;

import java.util.Random;
import java.util.UUID;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class CreateCoachworkActor extends AbstractPartCreateActor<CoachWork> {
    private final Random random = new Random();

    public CreateCoachworkActor(){
    }

    @Override
    Class<CoachWork> getReference() {
        return CoachWork.class;
    }

    @Override
    CoachWork createPart() {
        return new CoachWork("Coach-"+UUID.randomUUID().toString(), random.nextBoolean());
    }

    @Override
    String receiveActorName() {
        return "akka://carfactory/user/coachworkFilter";
    }
}
