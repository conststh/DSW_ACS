package baseNoStates;

public class DirectoryAreas {
  public static Area getAreaById(string id){
    return root.findAreaById(id);
  }
  public ArrayList<Door> getDoorsGivingAccess(Area area){
    //List of doors that give access to some space of this area
  }
  public ArrayList<Space> getSpaces(Area area){
    //List of spaces of this area, for the user to get all the spaces they can be, so as to authorize a request
  }
}
