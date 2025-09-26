package baseNoStates;

public abstract class DoorState {
    public abstract open();
    public abstract close();
    public abstract lock();
    public abstract unlock();
    public abstract unlockShortly();
}
