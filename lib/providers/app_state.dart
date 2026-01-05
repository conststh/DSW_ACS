import 'package:flutter/material.dart';
import '../models/door.dart';
import '../services/api_service.dart';
import 'dart:async';

class AppState extends ChangeNotifier {
  final ApiService _apiService = ApiService();
  Timer? _refreshTimer; // Timer per al refresc automàtic
  
  List<Door> _doors = [];
  final List<Door> _recentDoors = [];
  Locale _currentLocale = const Locale('es');

  // Mapa de Jerarquia
  final Map<String, List<String>> hierarchy = {
    'basement': ['parking', 'stairs'],
    'ground_floor': ['hall', 'room 1', 'room 2', 'stairs'],
    'floor1': ['room 3', 'corridor', 'IT', 'stairs'],
  };

  AppState() {
    // Quan s'inicia l'app, arrenquem el polling
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

  // --- ACCIONS ---
  //Actualitzar cada segon
  void _startPolling() { 
    _refreshTimer = Timer.periodic(const Duration(seconds: 1), (timer) {
      loadData();
    });
  }

  //Aturar el timer quan l'app es tanca
  @override
  void dispose() {
    _refreshTimer?.cancel();
    super.dispose();
  }

  // Enviar acció i refrescar immediatament
  Future<void> sendDoorAction(String doorId, String action) async {
    await _apiService.sendCommand(doorId, action);
    loadData(); // Actualitzem la UI al moment sense esperar al timer
  }

  void changeLanguage(Locale locale) {
    _currentLocale = locale;
    notifyListeners();
  }

  Future<void> loadData() async {
    final doorsJson = await _apiService.fetchAllDoors();
    // Només notifiquem si hi ha canvis
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