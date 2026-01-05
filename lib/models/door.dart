class Door {
  final String id;
  final String state; // "locked", "unlocked", "propped"
  final bool closed;
  final String from;
  final String to;

  Door({
    required this.id, 
    required this.state, 
    required this.closed, 
    required this.from,
    required this.to
  });

  factory Door.fromJson(Map<String, dynamic> json) {
    return Door(
      id: json['id'] ?? 'unknown',
      state: json['state'] ?? 'unknown',
      closed: json['closed'] ?? true,
      // Llegim exactament el que Java envia (WebServer.java -> Door.java -> toJson)
      from: json['from'] ?? 'unknown',
      to: json['to'] ?? 'unknown',
    );
  }
}