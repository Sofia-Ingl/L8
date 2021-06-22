package client.bundles;

import java.util.ListResourceBundle;

public class gui_sq_AL extends ListResourceBundle {
    private static final Object[][] contents = {

            {"CommandSuccess", "Komandës përfunduar me sukses"},
            {"AddError", "Ndodhi një gabim gjatë shtimit të një film në bazën e të dhënave"},
            {"AddIfMaxError", "Dështova të shtuar elementin maksimale"},
            {"ScriptError", "Gjatë ekzekutimit të një gabimi script ka ndodhur"},
            {"RegisterError", "Dështova të regjistrohen"},
            {"UserInsertionError", "Një përdorues me të njëjtin emër ekziston tashmë në bazën e të dhënave!"},
            {"WrongNameOrPassError", "Emër përdoruesi ose fjalëkalim i pavlefshëm!"},
            {"LoginError", "Dështoi për të hyni në llogarinë tuaj"},
            {"RemoveError", "Gabimet fshirjes element"},
            {"RemoveGreaterError", "Gabimet ndërsa heqjes elemente të mëdha"},
            {"RemoveByScreenwriterError", "Gabimet ndërsa fshirjes skenarist e elementit"},
            {"UpdateError", "Ndodhi një gabim gjatë përditësimit pika"},
            {"HelpMessage", "FAQ: \n" +
                    "Në fund të dritares ka butonat ndërveprim me bazën e të dhënave." +
                    "Për të kryer largimin apo përmirësuar, ju duhet të zgjidhni rresht tryezë." +
                    "E \"I pastër\" largon nga bazën e të dhënave vetëm ato objekte që i përkasin për ju. \n" +
                    "Në \"Vizualizimi\" ju do të gjeni një përfaqësim pamor të objekteve të bazës së të dhënave."},
            {"InfoText", "Informacion në lidhje me mbledhjen: \nLloji të objekteve të ruajtura: %s \nNumrin e objekteve: %s \nKohë initialization: %s"},
            {"idColumn", "ID"},
            {"nameColumn", "Emri"},
            {"xColumn", "X"},
            {"yColumn", "Y"},
            {"creationDateColumn", "krijuar"},
            {"oscarsColumn", "Oscars"},
            {"gPalmsColumn", "Palma"},
            {"taglineColumn", "Tags"},
            {"genreColumn", "Zhanër"},
            {"scrNameColumn", "Script"},
            {"scrHeightColumn", "Rritjes"},
            {"eyeColorColumn", "Ngjyra e syve"},
            {"nationalityColumn", "Kombësia"},
            {"ownerColumn", "Pronari"},
            {"visualTab", "Vizuelizimin"},
            {"tableTab", "Tabela"},
            {"addButton", "Dhëna"},
            {"addIfMaxButton", "Shtoni nëse është maksimumi"},
            {"clearButton", "I pastër"},
            {"executeScriptButton", "Ekzekutoni shkrimit"},
            {"gPalmsFilterButton", "Lini filmat me një numër të madh të degëve të palmës"},
            {"helpButton", "Ndihmë"},
            {"historyButton", "Histori"},
            {"infoButton", "Informacion"},
            {"removeByScreenwriterButton", "Hapi skenarist i"},
            {"removeByIdButton", "I pastër nga ID"},
            {"removeGreaterButton", "Hapi mëdha"},
            {"updateButton", "Artikullin e azhurnuar"},
            {"refreshButton", "Rinovoj"},
            {"ConnectionError", "gabime Lidhja"},
            {"Disconnected", "lidhja nuk është vendosur ..."},
            {"Connected", "lidhjes vendosur"},
            {"usernameLabel", "Hyrje"},
            {"passwordLabel", "Fjalëkalimin"},
            {"registerCheckBox", "Regjistrimi"},
            {"signInButton", "Konfirmoj"},
            {"WrongArgsError", "Jo të gjitha argumentet janë të vlefshme. Kontrolloni saktësinë e të dhënave të futura"},
            {"ChoiceError", "Objekti nuk është zgjedhur!"},
            {"History", "Historia (New të Vjetër):"},
            {"add", "Dhëna"},
            {"add_if_max", "Shtoni nëse është maksimumi"},
            {"clear", "I pastër"},
            {"execute_script", "Ekzekutoni shkrimit"},
            {"filter_greater_than_golden_palm_count", "Lini filmat me një numër të madh të degëve të palmës"},
            {"help", "Ndihmë"},
            {"history", "Histori"},
            {"info", "Informacion"},
            {"remove_all_by_screenwriter", "Hapi skenarist i"},
            {"remove_by_id", "I pastër nga ID"},
            {"remove_greater", "Hapi mëdha"},
            {"update", "Artikullin e azhurnuar"},
            {"refresh", "Rinovoj"},
            {"AccessError", "Duke u përpjekur për të rinovuar një element që nuk i përket përdoruesit!"},
            {"Error", "Gabim!"},
            {"Info", "Informacion"},
            {"NewTrial", "Shkruani komandën përsëri"},
            {"MovieDisplay", "Emri: %s \nNumri i Oskar: %d \nNumri i palma: %d \nCategory: %s \nScript: %s"}

    };


    @Override
    public Object[][] getContents() {
        return contents;
    }
}
