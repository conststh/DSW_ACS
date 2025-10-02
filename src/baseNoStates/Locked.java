package baseNoStates;

import java.security.cert.Extension;

//No entiendo lo de las acciones.

public class Locked extends DoorState{
    public void close(){
      //TODO
        System.out.println("Door " + door.getId() + " is locked.");
    }

    public void open(){
      //TODO
        System.out.println("Door " + door.getId() + " is locked.");
    }

    public void lock(){
      //TODO
        System.out.println("Door " + door.getId() + " is already locked");
    }

    public void unlock(){
        door.unlock(); //TODO
    }

    public void unlockShortly(){
        door.unlockShortly(); //TODO
    }

    public Locked(Door door){
      //TODO

    }
}
