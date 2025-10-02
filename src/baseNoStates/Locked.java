package baseNoStates;

import java.security.cert.Extension;

public class Locked extends DoorState{
    public void close(){
        System.out.println("Door " + door.getId() + " is already closed.");
    }

    public void open(){
        System.out.println("Can't open door " + door.getId() + " is locked.");
    }

    public void lock(){
        System.out.println("Door " + door.getId() + " is already locked");
    }

    public void unlock(){
        door.unlock(); //TODO
    }

    public void unlockShortly(){
        door.unlockShortly(); //TODO
    }

    public Locked(Door door){
      super(door);
    }
}
