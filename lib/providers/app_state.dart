import 'package:flutter/material.dart';
import '../models/door.dart';
import '../services/api_service.dart';
import 'dart:async';

class AppState extends ChangeNotifier {
  final ApiService _apiService = ApiService();
  Timer? _refreshTimer;
  
  List<Door> _doors = [];
  List<Door> _recentDoors = [];
  Locale _currentLocale = const Locale('es');
  
  bool _recentsInitialized = false; // Para hardcodear 1 vez

  final Map<String, List<String>> hierarchy = {
    'basement': ['parking', 'stairs basement'],
    'ground_floor': ['hall', 'room 1', 'room 2', 'stairs ground'],
    'floor1': ['room 3', 'corridor', 'IT', 'stairs floor 1'],
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

  // --- LÓGICA DE ESTADO COMPUESTO ---
  bool isGroupLocked(List<Door> groupDoors) {
    if (groupDoors.isEmpty) return false;
    return groupDoors.every((d) => d.state == 'locked');
  }

  bool hasOpenDoors(List<Door> groupDoors) {
    return groupDoors.any((d) => !d.closed);
  }

  // --- ACCIONES ---
  void _startPolling() {
    loadData();
    // Refresh cada 3 segundos
    _refreshTimer = Timer.periodic(const Duration(seconds: 3), (timer) {
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
    try {
      final doorsJson = await _apiService.fetchAllDoors();
      final List<Door> newDoors = doorsJson.map((json) => Door.fromJson(json)).toList();
      
      _doors = newDoors;
      // Inicialización de Recientes
      if (!_recentsInitialized && _doors.isNotEmpty) {
        _initializeHardcodedRecents();
        _recentsInitialized = true;
      }

      // Sincronización de Recientes
      if (_recentDoors.isNotEmpty) {
        _recentDoors = _recentDoors.map((recent) {
          // Buscamos la versión actualizada de esta puerta en la nueva lista
          return _doors.firstWhere(
            (d) => d.id == recent.id, 
            orElse: () => recent // Si por alguna razón desapareció, mantenemos la vieja
          );
        }).toList();
      }

      notifyListeners();
    } catch (e) {
      debugPrint("Error loading data: $e");
    }
  }

  void _initializeHardcodedRecents() {
    final targetIds = ['D1', 'D2', 'D6', 'D4'];
    
    // Filtramos de la lista principal las puertas que coinciden
    final initialRecents = _doors.where((d) => targetIds.contains(d.id)).toList();
    
    // Las añadimos a recientes
    for (var id in targetIds) {
      try {
        var door = initialRecents.firstWhere((d) => d.id == id);
        _recentDoors.add(door); 
      } catch (e) {
        // La puerta no existe en el backend
      }
    }
  }

  void addToRecents(Door door) {
    _recentDoors.removeWhere((d) => d.id == door.id);
    _recentDoors.insert(0, door);
    if (_recentDoors.length > 5) _recentDoors.removeLast();
    notifyListeners();
  }
}