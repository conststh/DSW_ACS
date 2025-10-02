package baseNoStates;

public class UnlockedShortly extends DoorState{
  public UnlockedShortly(Door door){
    //TODO
  }

  @Override
  public String getStateName() {
    return "unlockedShortly";
  }

  @Override
  public void close(){
    //TODO
    door.close();
  }

  @Override
  public void open(){
    //TODO
  }

  @Override
  public void lock(){
    //TODO
      //Bloquear
  }

  @Override
  public void unlock(){
    //TODO
  }

  @Override
  public void unlockShortly(){
    //TODO
      //Desbloquear con tiempo
  }
}
