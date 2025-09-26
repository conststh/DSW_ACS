package baseNoStates;

public class Unlocked extends DoorState{
    public void close(){
        if(door.isClosed()){
            System.out.println("Door "+ door.getId() + " is already closed");
        }
        else{
            door.close();
        }
    }

    public void open(){
        if(!door.isClosed()){
            System.out.println("Door " + door.getId() + " is already open");
        }
        else{
            door.open();
        }
    }

    public void lock(){
        door.lock(); //Implementar
    }

    public void unlock(){
        System.out.println("Door "+door.getId() + " is already unlocked");
    }

    public void unlockShortly(){
        door.unlockShortly();
    }

    public Unlocked(Door door){
        this.door = door;
    }
}
