package baseNoStates;

public class UnlockedShortly extends DoorState{
    public void close(){
        door.close();
    }

    public void open(){
        door.open()
    }

    public void lock(){
        //Bloquear
    }

    public void unlock(){
        door.unlock()
    }

    public void unlockShortly(){
        //Desbloquear con tiempo
    }

    public UnlockedShortly(Door door){
        super(door);
    }
}
