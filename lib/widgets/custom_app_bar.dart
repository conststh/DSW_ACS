import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/app_state.dart';
import '../l10n/app_localizations.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String titleKey;
  final bool isHome; // Per saber si amaguem la fletxa

  const CustomAppBar({super.key, required this.titleKey, this.isHome = false});

  @override
  Widget build(BuildContext context) {
    final appState = context.watch<AppState>();
    final trans = AppLocalizations(appState.currentLocale);

    return AppBar(
      leadingWidth: 100, // Espai extra per a dos botons
      leading: Row(
        children: [
          if (!isHome) // Requisit: Primer fletxa
            IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: () => Navigator.pop(context),
            ),
          // Requisit: Després la casa (torna a l'inici)
          IconButton(
            icon: const Icon(Icons.home),
            onPressed: () {
              Navigator.popUntil(context, (route) => route.isFirst);
            },
          ),
        ],
      ),
      title: Text(trans.translate(titleKey)),
      actions: [
        // Requisit: Selector d'idioma (banderes)
        DropdownButton<Locale>(
          value: appState.currentLocale,
          icon: const Icon(Icons.language),
          underline: Container(),
          onChanged: (Locale? newLocale) {
            if (newLocale != null) appState.changeLanguage(newLocale);
          },
          items: const [
             DropdownMenuItem(value: Locale('es'), child: Text('ESP')),
             DropdownMenuItem(value: Locale('ca'), child: Text('CAT')),
             DropdownMenuItem(value: Locale('en'), child: Text('ENG')),
          ],
        ),
        const SizedBox(width: 10),
      ],
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}