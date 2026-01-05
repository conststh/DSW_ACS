import 'package:flutter/material.dart';
import '../models/door.dart';
import '../widgets/custom_app_bar.dart';
import '../widgets/door_row.dart';

class ShortcutListScreen extends StatelessWidget {
  final List<Door> doors;
  final String titleKey;

  const ShortcutListScreen({super.key, required this.doors, required this.titleKey});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(titleKey: titleKey),
      body: doors.isEmpty
          ? const Center(child: Text("..."))
          : ListView.builder(
              itemCount: doors.length,
              itemBuilder: (context, index) => DoorRow(door: doors[index], showContext: true),
            ),
    );
  }
}