import 'package:flutter/material.dart';
import '../models/door.dart';
import '../services/api_service.dart';
import 'dart:async';

class AppState extends ChangeNotifier {
  final ApiService _apiService = ApiService();
  Timer? _refreshTimer;
  
  List<Door> _doors = [];
  final List<Door> _recentDoors = [];
  Locale _currentLocale = const Locale('es');

  final Map<String, List<String>> hierarchy = {
    'basement': ['parking', 'stairs'],
    'ground_floor': ['hall', 'room 1', 'room 2', 'stairs'],
    'floor1': ['room 3', 'corridor', 'IT', 'stairs'],
  };

  AppState() {
    _startPolling();
  }

  // --- GETTERS ---
  List<Door> get doors => _doors;
  List<Door> get recentDoors => _recentDoors;
  Locale get currentLocale => _currentLocale;
  List<Door> get proppedDoors => _doors.where((d) => d.state == 'propped').toList();

  List<Door> getDoorsForSpace(String spaceId) {
    return _doors.where((d) => d.from == spaceId || d.to == spaceId).toList();
  }

  List<Door> getDoorsForPartition(String partitionId) {
    List<String> spaces = hierarchy[partitionId] ?? [];
    Set<Door> partitionDoors = {};
    for (var space in spaces) {
      partitionDoors.addAll(getDoorsForSpace(space));
    }
    return partitionDoors.toList();
  }

  // --- LÓGICA DE ESTADO COMPUESTO (candado de grupo) ---
  bool isGroupLocked(List<Door> groupDoors) {
    if (groupDoors.isEmpty) return false;
    return groupDoors.every((d) => d.state == 'locked');
  }

  bool hasOpenDoors(List<Door> groupDoors) {
    return groupDoors.any((d) => !d.closed);
  }

  // --- ACCIONES ---
  void _startPolling() { 
    _refreshTimer = Timer.periodic(const Duration(seconds: 1), (timer) {
      loadData();
    });
  }

  @override
  void dispose() {
    _refreshTimer?.cancel();
    super.dispose();
  }

  Future<void> sendDoorAction(String doorId, String action) async {
    await _apiService.sendCommand(doorId, action);
    loadData();
  }

  Future<void> sendBatchAction(List<Door> targetDoors, String action) async {
    for (var door in targetDoors) {
       await _apiService.sendCommand(door.id, action);
    }
    loadData();
  }

  void changeLanguage(Locale locale) {
    _currentLocale = locale;
    notifyListeners();
  }

  Future<void> loadData() async {
    final doorsJson = await _apiService.fetchAllDoors();
    _doors = doorsJson.map((json) => Door.fromJson(json)).toList();
    notifyListeners();
  }

  void addToRecents(Door door) {
    _recentDoors.removeWhere((d) => d.id == door.id);
    _recentDoors.insert(0, door);
    if (_recentDoors.length > 10) _recentDoors.removeLast();
    notifyListeners();
  }
}