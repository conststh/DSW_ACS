import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/door.dart';
import '../providers/app_state.dart';
import '../widgets/custom_app_bar.dart';
import '../widgets/door_row.dart';

// Definimos un enum para identificar qué tipo de lista queremos mostrar
enum ShortcutType { recent, propped }

class ShortcutListScreen extends StatelessWidget {
  final ShortcutType type;
  final String titleKey;

  const ShortcutListScreen({
    super.key, 
    required this.type, 
    required this.titleKey
  });

  @override
  Widget build(BuildContext context) {
    final appState = context.watch<AppState>();
    
    // Determinamos dinámicamente qué lista usar
    List<Door> doors;
    if (type == ShortcutType.recent) {
      doors = appState.recentDoors;
    } else {
      doors = appState.proppedDoors;
    }

    return Scaffold(
      appBar: CustomAppBar(titleKey: titleKey),
      body: doors.isEmpty
          ? const Center(child: Text("..."))
          : ListView.builder(
              itemCount: doors.length,
              // Usamos showContext: true para permitir acciones sobre estas puertas
              itemBuilder: (context, index) => DoorRow(door: doors[index], showContext: true),
            ),
    );
  }
}