import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/app_state.dart';
import '../l10n/app_localizations.dart';
import '../screens/shortcut_list_screen.dart';

class GlobalActionsBar extends StatelessWidget {
  const GlobalActionsBar({super.key});

  @override
  Widget build(BuildContext context) {
    final appState = context.watch<AppState>();
    final trans = AppLocalizations(appState.currentLocale);

    return Container(
      color: Colors.grey[200],
      padding: const EdgeInsets.symmetric(vertical: 10),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          ElevatedButton.icon(
            icon: const Icon(Icons.access_time),
            label: Text(trans.translate('recent')),
            onPressed: () => _goToShortcuts(context, ShortcutType.recent, 'recent'),
          ),
          ElevatedButton.icon(
            icon: const Icon(Icons.warning_amber),
            label: Text(trans.translate('propped')),
            style: ElevatedButton.styleFrom(foregroundColor: Colors.red),
            onPressed: () => _goToShortcuts(context, ShortcutType.propped, 'propped'),
          ),
        ],
      ),
    );
  }

  void _goToShortcuts(BuildContext context, ShortcutType type, String title) {
    Navigator.push(context, MaterialPageRoute(
        builder: (_) => ShortcutListScreen(type: type, titleKey: title)));
  }
}