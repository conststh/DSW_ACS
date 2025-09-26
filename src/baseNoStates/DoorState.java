package baseNoStates;

public abstract class DoorState {
    public abstract void open();
    public abstract void close();
    public abstract void lock();
    public abstract void unlock();
    public abstract void unlockShortly();

    public DoorState(Door door){ this.door = door; } //Imagino que esto va asi

    protected Door door;
}
