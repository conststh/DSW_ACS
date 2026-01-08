import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/door.dart';
import '../providers/app_state.dart';
import '../l10n/app_localizations.dart';
import '../widgets/custom_app_bar.dart';
import '../widgets/door_row.dart';
import '../widgets/global_actions_bar.dart';

class SpacesScreen extends StatelessWidget {
  final String partitionId;

  const SpacesScreen({super.key, required this.partitionId});

  @override
  Widget build(BuildContext context) {
    final appState = context.watch<AppState>();
    final trans = AppLocalizations(appState.currentLocale);
    final spaces = appState.hierarchy[partitionId] ?? [];

    return Scaffold(
      appBar: CustomAppBar(titleKey: partitionId),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              itemCount: spaces.length,
              itemBuilder: (context, index) {
                final spaceId = spaces[index];
                final doors = appState.getDoorsForSpace(spaceId);
                
                // Cálculo de estado compuesto (grupo de puertas)
                final isLocked = appState.isGroupLocked(doors);

                return Card(
                  margin: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                  child: ExpansionTile(
                    title: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(trans.translate(spaceId), 
                          style: const TextStyle(fontWeight: FontWeight.bold)),
                        
                        // Candado de Área
                        IconButton(
                           icon: Icon(
                             isLocked ? Icons.lock : Icons.lock_open,
                             color: isLocked ? Colors.red : Colors.green,
                             size: 20,
                           ),
                           onPressed: () {
                             _handleGroupLockAction(context, appState, trans, doors, isLocked);
                           },
                        )
                      ],
                    ),
                    leading: const Icon(Icons.room),
                    children: doors.isEmpty
                        ? [Padding(padding: const EdgeInsets.all(16), child: Text(trans.translate('no_doors')))]
                        : doors.map((d) => DoorRow(door: d)).toList(),
                  ),
                );
              },
            ),
          ),
          const GlobalActionsBar(),
        ],
      ),
    );
  }

  void _handleGroupLockAction(
      BuildContext context, 
      AppState appState, 
      AppLocalizations trans, 
      List<Door> doors, 
      bool isCurrentlyLocked) {
    
    if (isCurrentlyLocked) {
      appState.sendBatchAction(doors, 'unlock');
    } else {
      if (appState.hasOpenDoors(doors)) {
        showDialog(
          context: context,
          builder: (ctx) => AlertDialog(
            title: Text(trans.translate('action_denied')),
            content: Text(trans.translate('error_doors_open_lock')),
            actions: [
              TextButton(
                onPressed: () => Navigator.pop(ctx),
                child: const Text("OK"),
              )
            ],
          ),
        );
      } else {
        appState.sendBatchAction(doors, 'lock');
      }
    }
  }
}