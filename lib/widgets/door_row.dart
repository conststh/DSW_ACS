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
          IconButton(
            icon: Icon(
              isOpen ? Icons.door_front_door_outlined : Icons.door_front_door,
              color: isOpen ? Colors.green : Colors.grey,
              size: 28,
            ),
            onPressed: () {
              if (door.closed) {
                // Intentar abrir
                if (isLocked) {
                  showDialog(
                    context: context,
                    builder: (ctx) => AlertDialog(
                      title: Text(trans.translate('action_denied')),
                      content: Text(trans.translate('error_open_lock')),
                      actions: [
                        TextButton(
                          onPressed: () => Navigator.pop(ctx),
                          child: const Text("OK"),
                        ),
                        ElevatedButton(
                          onPressed: () {
                            appState.sendDoorAction(door.id, 'unlock');
                            Navigator.pop(ctx);
                          },
                          child: Text(trans.translate('unlock')),
                        )
                      ],
                    ),
                  );
                  return;
                }
                appState.sendDoorAction(door.id, 'open');
              } else {
                // Cerrar siempre permitido
                appState.sendDoorAction(door.id, 'close');
              }
            },
          ),
          
          // Candado
          IconButton(
            icon: Icon(
              isLocked ? Icons.lock : Icons.lock_open,
              color: (isLocked || isPropped) ? Colors.red : Colors.green,
              size: 28,
            ),
            onPressed: () {
              String action = (door.state == 'locked') ? 'unlock' : 'lock';
              if (action == 'lock' && !door.closed) {
                showDialog(
                    context: context,
                    builder: (ctx) => AlertDialog(
                      title: Text(trans.translate('action_denied')),
                      content: Text(trans.translate('error_lock_open')),
                      actions: [
                        TextButton(
                          onPressed: () => Navigator.pop(ctx),
                          child: const Text("OK"),
                        ),
                        ElevatedButton(
                          onPressed: () {
                            appState.sendDoorAction(door.id, 'close');
                            Navigator.pop(ctx);
                          },
                          child: Text(trans.translate('close')),
                        )
                      ],
                    ),
                  );
                return;
              }
              appState.sendDoorAction(door.id, action);
            },
          ),
        ],
      ),
    );
  }
}