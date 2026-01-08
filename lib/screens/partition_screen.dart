import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/door.dart'; // Import necesario para List<Door>
import '../providers/app_state.dart';
import '../l10n/app_localizations.dart';
import '../widgets/custom_app_bar.dart';
import '../widgets/global_actions_bar.dart';
import 'spaces_screen.dart';

class PartitionScreen extends StatelessWidget {
  const PartitionScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final appState = context.watch<AppState>();
    final trans = AppLocalizations(appState.currentLocale);

    return Scaffold(
      appBar: const CustomAppBar(titleKey: 'title', isHome: true),
      body: RefreshIndicator(
        onRefresh: () => appState.loadData(),
        child: Column(
          children: [
            Expanded(
              child: ListView(
                padding: const EdgeInsets.all(16),
                children: appState.hierarchy.keys.map((partitionId) {
                  // Obtenemos las puertas y calculamos estado al vuelo
                  final doors = appState.getDoorsForPartition(partitionId);
                  final isLocked = appState.isGroupLocked(doors);

                  return Card(
                    elevation: 4,
                    child: ListTile(
                      leading: const Icon(Icons.apartment, size: 40, color: Colors.blue),
                      title: Text(
                        trans.translate(partitionId),
                        style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                      ),
                      trailing: Row(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          // Botón de Candado Dinámico
                          IconButton(
                            icon: Icon(
                              isLocked ? Icons.lock : Icons.lock_open,
                              color: isLocked ? Colors.red : Colors.green
                            ),
                            tooltip: isLocked ? trans.translate('unlock') : trans.translate('lock'),
                            onPressed: () {
                              _handleGroupLockAction(context, appState, trans, doors, isLocked);
                            },
                          ),
                          const Icon(Icons.arrow_forward_ios),
                        ],
                      ),
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (_) => SpacesScreen(partitionId: partitionId),
                          ),
                        );
                      },
                    ),
                  );
                }).toList(),
              ),
            ),
            const GlobalActionsBar(),
          ],
        ),
      ),
    );
  }

  // Lógica centralizada de validación y alerta
  void _handleGroupLockAction(
      BuildContext context, 
      AppState appState, 
      AppLocalizations trans, 
      List<Door> doors, 
      bool isCurrentlyLocked) {
    
    if (isCurrentlyLocked) {
      // Si está bloqueado, siempre podemos desbloquear
      appState.sendBatchAction(doors, 'unlock');
    } else {
      // Si queremos bloquear, primero validamos si hay puertas abiertas
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
              ),
              ElevatedButton(
                onPressed: () {
                  appState.sendBatchAction(doors, 'close');
                  Navigator.pop(ctx);
                },
                child: Text(trans.translate('close_all')),
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