import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/app_state.dart';
import '../l10n/app_localizations.dart';
import '../widgets/custom_app_bar.dart';
import '../widgets/door_row.dart';
import 'shortcut_list_screen.dart';

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

                return Card(
                  margin: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                  child: ExpansionTile(
                    title: Text(trans.translate(spaceId), 
                      style: const TextStyle(fontWeight: FontWeight.bold)),
                    leading: const Icon(Icons.room),
                    children: doors.isEmpty
                        ? [Padding(padding: const EdgeInsets.all(16), child: Text(trans.translate('no_doors')))]
                        : doors.map((d) => DoorRow(door: d)).toList(),
                  ),
                );
              },
            ),
          ),
          // Botons Inferiors (Extra requeriment)
          Container(
            color: Colors.grey[200],
            padding: const EdgeInsets.symmetric(vertical: 10),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                ElevatedButton.icon(
                  icon: const Icon(Icons.access_time), // Rellotge
                  label: Text(trans.translate('recent')),
                  onPressed: () => _goToShortcuts(context, appState.recentDoors, 'recent'),
                ),
                ElevatedButton.icon(
                  icon: const Icon(Icons.warning_amber), // Propped
                  label: Text(trans.translate('propped')),
                  style: ElevatedButton.styleFrom(foregroundColor: Colors.red),
                  onPressed: () => _goToShortcuts(context, appState.proppedDoors, 'propped'),
                ),
              ],
            ),
          )
        ],
      ),
    );
  }

  void _goToShortcuts(BuildContext context, var doors, String title) {
    Navigator.push(context, MaterialPageRoute(
      builder: (_) => ShortcutListScreen(doors: doors, titleKey: title)));
  }
}