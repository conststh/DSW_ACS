package baseNoStates;

public class Unlocked extends DoorState{
    public void close(){
        door.close();
    }

    public void open(){
        door.open();
    }

    public void lock(){
        door.lock(); //Implementar
    }

    public void unlock(){
        System.out.println("Door "+door.getId() + " is already unlocked");
    }

    public void unlockShortly(){
        door.unlockShortly(); //Se puede abrir temporalmente si esta totalmente abierta?
    }

    public Unlocked(Door door){
        super(door);
    }
}
