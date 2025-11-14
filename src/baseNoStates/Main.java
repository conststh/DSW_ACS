package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

public class Main {
  static void main(String[] args) {
    DirectoryDoors.makeDoors();
    DirectoryUsers.makeUsers();
    new WebServer();
  }
}
