package client.bundles;

import java.util.ListResourceBundle;

public class gui_sk_SK extends ListResourceBundle {
    private static final Object[][] contents = {

            {"CommandSuccess", "Príkaz bol úspešne dokončený"},
            {"AddError", "Pri pridávaní filmu do databázy došlo k chybe"},
            {"AddIfMaxError", "Nepodarilo sa pridať maximálne prvok"},
            {"ScriptError", "Pri vykonávaní chyby skriptu došlo k"},
            {"RegisterError", "Nepodarilo sa zaregistrovať"},
            {"UserInsertionError", "Užívateľ s rovnakým názvom už existuje v databáze!"},
            {"WrongNameOrPassError", "Neplatné užívateľské meno alebo heslo!"},
            {"LoginError", "Nepodarilo sa prihlásiť k svojmu účtu"},
            {"RemoveError", "Chyba pri mazaní prvok"},
            {"RemoveGreaterError", "Chyba pri odstraňovaní veľkých prvkov"},
            {"RemoveByScreenwriterError", "Chyba pri mazaní scenáristu prvku"},
            {"UpdateError", "Na aktualizáciu položky došlo k chybe"},
            {"HelpMessage", "FAQ: \n" +
                    "V dolnej časti okna sú tlačidlá interakciu s databázou." +
                    "Aby bolo možné vykonať odstránenie alebo upgrade, musíte vybrať riadok tabuľky." +
                    "Príkaz \"Jasný\" odstraňuje z databázy iba tie objekty, ktoré patria k tebe. \n" +
                    "V \"Vizualizácia \" nájdete vizuálnu reprezentáciu databázových objektov."},
            {"InfoText", "Informácie o zbierke: \n Typ uložených predmetov: %s \n je počet objektov: %s \n dobe inicializácia: %s \n"},
            {"idColumn", "ID"},
            {"nameColumn", "Názov"},
            {"xColumn", "X"},
            {"yColumn", "Y"},
            {"creationDateColumn", "Vytvoril"},
            {"oscarsColumn", "Oscarov"},
            {"gPalmsColumn", "Palm vetva"},
            {"taglineColumn", "Značky"},
            {"genreColumn", "Žáner"},
            {"scrNameColumn", "Script"},
            {"scrHeightColumn", "Rast"},
            {"eyeColorColumn", "Farba očí"},
            {"nationalityColumn", "Štátna"},
            {"ownerColumn", "Majiteľ"},
            {"visualTab", "Vizualizácia"},
            {"tableTab", "Tabuľka"},
            {"addButton", "Pridať"},
            {"addIfMaxButton", "Pridajte, ak je maximum"},
            {"clearButton", "Jasný"},
            {"executeScriptButton", "Vykonať skript"},
            {"gPalmsFilterButton", "Nechajte filmy s veľkým počtom palmy"},
            {"helpButton", "Pomoc"},
            {"historyButton", "História"},
            {"infoButton", "Informácie"},
            {"removeByScreenwriterButton", "Odobrať scenárista"},
            {"removeByIdButton", "Odstrániť podľa id"},
            {"removeGreaterButton", "Odstránenie veľkých"},
            {"updateButton", "Obnoviť položka"},
            {"refreshButton", "Obnoviť"},
            {"ConnectionError", "Chyba spojenia"},
            {"Disconnected", "Spojenie nie je nadviazané ..."},
            {"Connected", "Spojenie naviazané"},
            {"usernameLabel", "Vstup"},
            {"passwordLabel", "Heslo"},
            {"registerCheckBox", "Registrácia"},
            {"signInButton", "Confirm"},
            {"WrongArgsError", "Nie všetky argumenty sú platné. Skontrolujte správnosť zadaných údajov"},
            {"ChoiceError", "Objekt nie je vybrané!"},
            {"History", "História (od najnovšie):"},
            {"add", "Pridať"},
            {"add_if_max", "Pridajte, ak je maximum"},
            {"clear", "Jasný"},
            {"execute_script", "Spustiť skript"},
            {"filter_greater_than_golden_palm_count", "Nechajte filmy s veľkým počtom palmy"},
            {"help", "Pomoc"},
            {"history", "História"},
            {"info", "Informácie"},
            {"remove_all_by_screenwriter", "Odobrať scenárista"},
            {"remove_by_id", "Odstrániť podľa id"},
            {"remove_greater", "Odstránenie veľkých"},
            {"update", "Obnoviť položka"},
            {"refresh", "Obnoviť"},
            {"AccessError", "Snažím sa aktualizovať prvok, ktorý nepatrí k užívateľovi!"},
            {"Error", "Chyba!"},
            {"Info", "Informácie"},
            {"NewTrial", "Znovu zadanie príkazu"},
            {"MovieDisplay", "Meno: %s \nPočet Oscars: %d \nPočet palmy: %d \nKategória: %s \nScenár: %s"}


    };


    @Override
    public Object[][] getContents() {
        return contents;
    }
}

