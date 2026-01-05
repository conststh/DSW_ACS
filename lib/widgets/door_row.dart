import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/door.dart';
import '../providers/app_state.dart';
import '../l10n/app_localizations.dart';

class DoorRow extends StatelessWidget {
  final Door door;
  final bool showContext; 

  const DoorRow({super.key, required this.door, this.showContext = false});

  @override
  Widget build(BuildContext context) {
    final appState = context.read<AppState>();
    final trans = AppLocalizations(appState.currentLocale);

    bool isOpen = !door.closed;
    bool isPropped = door.state == 'propped';
    bool isLocked = door.state == 'locked';

    // Format ID sense 'D'
    String simpleId = door.id.replaceAll('D', '');
    String doorLabel = trans.translate('door_element');

    return ListTile(
      onTap: () {
        appState.addToRecents(door);
      },
      title: Text(
        showContext 
          ? "$doorLabel $simpleId (${trans.translate(door.from)} ↔ ${trans.translate(door.to)})"
          : "$doorLabel $simpleId",
      ),
      subtitle: Text("${trans.translate('state')}: ${trans.translate(door.state)}"),
      trailing: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          // Porta Física
          IconButton(
            icon: Icon(
              isOpen ? Icons.door_front_door_outlined : Icons.door_front_door,
              color: isOpen ? Colors.green : Colors.grey,
              size: 28,
            ),
            onPressed: () {
              // Si està tancada -> comanda 'open'. Si està oberta -> comanda 'close'
              String action = door.closed ? 'open' : 'close';
              appState.sendDoorAction(door.id, action);
            },
          ),
          
          // Candau
          IconButton(
            icon: Icon(
              isLocked ? Icons.lock : Icons.lock_open,
              color: (isLocked || isPropped) ? Colors.red : Colors.green,
              size: 28,
            ),
            onPressed: () {
              // Si està locked -> 'unlock'. Si està unlocked/propped -> 'lock'
              String action = (door.state == 'locked') ? 'unlock' : 'lock';
              appState.sendDoorAction(door.id, action);
            },
          ),
        ],
      ),
    );
  }
}