package models.actors;

import models.domain.Wheel;

import java.util.Random;
import java.util.UUID;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class CreateWheelActor extends AbstractPartCreateActor<Wheel> {
    private final Random random = new Random();

    public CreateWheelActor(){
    }

    @Override
    Class<Wheel> getReference() {
        return Wheel.class;
    }

    @Override
    Wheel createPart() {
        return new Wheel("Wheel-"+UUID.randomUUID().toString(), random.nextBoolean());
    }

    @Override
    String receiveActorName() {
        return "akka://carfactory/user/wheelFilter";
    }
}
