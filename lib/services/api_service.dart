import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';

class ApiService {
  //Per a Windows Desktop
  static const String _baseUrl = 'http://localhost:8080';

  Future<List<dynamic>> fetchAllDoors() async {
    try {
      final response = await http.get(Uri.parse('$_baseUrl/refresh'));
      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        return data['doors']; 
      }
      return [];
    } catch (e) {
      print("Error fetching doors: $e");
      return [];
    }
  }

  Future<void> sendCommand(String doorId, String action) async {
    // Format de data estricte que espera Java: yyyy-MM-dd'T'HH:mm
    final now = DateTime.now();
    final formatter = DateFormat("yyyy-MM-dd'T'HH:mm");
    final timeStr = formatter.format(now);
    
    // Credencial "admin" per a la demo
    const credential = "11343"; 

    // Construcció de la URL segons WebServer.java
    final String url = '$_baseUrl/reader/credential/$credential/action/$action/time/$timeStr/target/$doorId';
    
    try {
      print("Sending command: $url"); // Log per depurar
      await http.get(Uri.parse(url));
    } catch (e) {
      print("Error sending command: $e");
    }
  }
}