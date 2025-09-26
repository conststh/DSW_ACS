package baseNoStates;

import java.security.cert.Extension;

public class Locked extends DoorState{
    public void close(){
        System.out.println("Door " + door.getId() + " is already locked.")
    }

    public void open(){
        System.out.println("Door " + door.getId() + " is locked.")
    }

    public void lock(){
        System.out.println("Door " + door.getId() + " is already locked")
    }

    public void unlock(){
        door.unlock(); //implementar
    }

    public void unlockShortly(){
        door.unlockShortly(); //implementar
    }

    public Locked(Door door){
        this.door = door;
    }
}
