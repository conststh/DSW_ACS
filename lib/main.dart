import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'providers/app_state.dart';
import 'screens/partition_screen.dart';

void main() {
  runApp(
    ChangeNotifierProvider(
      create: (_) => AppState()..loadData(),
      child: const MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    final appState = Provider.of<AppState>(context);

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'ACS Client',
      locale: appState.currentLocale,
      localizationsDelegates: const [
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
      ],
      supportedLocales: const [
        Locale('es'),
        Locale('ca'),
        Locale('en'),
      ],
      theme: ThemeData(
        useMaterial3: true,
        colorSchemeSeed: Colors.blue,
      ),
      home: const PartitionScreen(),
    );
  }
}