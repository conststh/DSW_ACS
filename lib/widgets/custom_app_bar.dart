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
      centerTitle: true,
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
          underline: Container(),
          onChanged: (Locale? newLocale) {
            if (newLocale != null) appState.changeLanguage(newLocale);
          },
          items: [
             DropdownMenuItem(
               value: const Locale('es'),
               child: Image.asset('lib/pngs_idiomas/español.png', width: 32, height: 32),
             ),
             DropdownMenuItem(
               value: const Locale('ca'),
               child: Image.asset('lib/pngs_idiomas/catalan.png', width: 32, height: 32),
             ),
             DropdownMenuItem(
               value: const Locale('en'),
               child: Image.asset('lib/pngs_idiomas/ingles.png', width: 32, height: 32),
             ),
          ],
        ),
        const SizedBox(width: 10),
      ],
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}