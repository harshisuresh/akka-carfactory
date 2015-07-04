package models.actors;

import models.domain.Engine;

import java.util.Random;
import java.util.UUID;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class CreateEngineActor extends AbstractPartCreateActor<Engine> {
    private final Random random = new Random();

    public CreateEngineActor(){
    }

    @Override
    Class<Engine> getReference() {
        return Engine.class;
    }

    @Override
    Engine createPart() {
        return new Engine("Engine-"+UUID.randomUUID().toString(), random.nextBoolean());
    }

    @Override
    String receiveActorName() {
        return "akka://carfactory/user/engineFilter";
    }
}
