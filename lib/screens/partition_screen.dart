import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/app_state.dart';
import '../l10n/app_localizations.dart';
import '../widgets/custom_app_bar.dart';
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
        child: ListView(
          padding: const EdgeInsets.all(16),
          children: appState.hierarchy.keys.map((partitionId) {
            return Card(
              elevation: 4,
              child: ListTile(
                leading: const Icon(Icons.apartment, size: 40, color: Colors.blue),
                title: Text(
                  trans.translate(partitionId),
                  style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
                trailing: const Icon(Icons.arrow_forward_ios),
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
    );
  }
}