package client.bundles;

import java.util.ListResourceBundle;

public class gui_ru_RU extends ListResourceBundle {
    private static final Object[][] contents = {

            {"CommandSuccess","Команда выполнена успешно"},
            {"AddError","Возникла ошибка при добавлении фильма в базу данных"},
            {"AddIfMaxError", "Ошибка добавления максимального элемента"},
            {"ScriptError", "Во время выполнения скрипта произошла ошибка"},
            {"RegisterError", "Ошибка при регистрации"},
            {"UserInsertionError", "Пользователь с таким именем уже есть в базе данных!"},
            {"WrongNameOrPassError", "Неверное имя или пароль!"},
            {"LoginError", "Ошибка при входе в учетную запись"},
            {"RemoveError", "Ошибка при удалении элемента"},
            {"RemoveGreaterError", "Ошибка при удалении больших элементов"},
            {"RemoveByScreenwriterError", "Ошибка при удалении элемента по сценаристу"},
            {"UpdateError", "Ошибка при обновлении элемента"},
            {"HelpMessage", "Справка:\n" +
                    "Внизу окна расположены кнопки взаимодействия с базой. " +
                    "Для выполнения действий по удалению или обновлению необходимо выбрать строку таблицы. " +
                    "Команда \"Очистить\" удаляет из БД лишь те объекты, что принадлежат вам.\n" +
                    "В разделе \"Визуализация\" вы найдете визуальное представление объектов БД."},
            {"InfoText", "Информация о коллекции:\nТип хранимых объектов: %s \nКоличество объектов: %s \nВремя инициализации: %s"},
            {"idColumn", "Идентификатор"},
            {"nameColumn", "Название"},
            {"xColumn", "X"},
            {"yColumn", "Y"},
            {"creationDateColumn", "Дата создания"},
            {"oscarsColumn", "Оскары"},
            {"gPalmsColumn", "Пальмовые ветви"},
            {"taglineColumn", "Теги"},
            {"genreColumn", "Жанры"},
            {"scrNameColumn", "Сценарист"},
            {"scrHeightColumn", "Рост"},
            {"eyeColorColumn", "Цвет глаз"},
            {"nationalityColumn", "Национальность"},
            {"ownerColumn", "Владелец"},
            {"visualTab", "Визуализация"},
            {"tableTab","Таблица"},
            {"addButton", "Добавить"},
            {"addIfMaxButton", "Добавить макс. элемент"},
            {"clearButton", "Очистить"},
            {"executeScriptButton", "Исполнить скрипт"},
            {"gPalmsFilterButton", "Оставить фильмы с большим числом пальмовых ветвей"},
            {"helpButton", "Помощь"},
            {"historyButton", "История"},
            {"infoButton", "Информация"},
            {"removeByScreenwriterButton", "Удалить по сценаристу"},
            {"removeByIdButton", "Удалить по id"},
            {"removeGreaterButton", "Удалить большие"},
            {"updateButton", "Обновить элемент"},
            {"refreshButton", "Обновить"},
            {"ConnectionError", "Ошибка соединения"},
            {"Disconnected", "Соединение не установлено..."},
            {"Connected", "Соединение установлено"},
            {"usernameLabel", "Логин"},
            {"passwordLabel", "Пароль"},
            {"registerCheckBox", "Регистрация"},
            {"signInButton", "Подтвердить"},
            {"WrongArgsError", "Не все аргументы допустимы. Проверьте корректность введенных данных"},
            {"ChoiceError", "Объект не выбран!"},
            {"History", "История (от новых к старым):"},
            {"add", "Добавить"},
            {"add_if_max", "Добавить макс. элемент"},
            {"clear", "Очистить"},
            {"execute_script", "Исполнить скрипт"},
            {"filter_greater_than_golden_palm_count", "Оставить фильмы с большим числом пальмовых ветвей"},
            {"help", "Помощь"},
            {"history", "История"},
            {"info", "Информация"},
            {"remove_all_by_screenwriter", "Удалить по сценаристу"},
            {"remove_by_id", "Удалить по id"},
            {"remove_greater", "Удалить большие"},
            {"update", "Обновить элемент"},
            {"refresh", "Обновить"},
            {"AccessError", "Попытка обновить элемент, не принадлежащий пользователю!"},
            {"Error", "Ошибка!"},
            {"Info", "Информация"},
            {"NewTrial", "Пожалуйста, введите команду снова"},
            {"MovieDisplay", "Название: %s \nЧисло оскаров: %d \nЧисло пальмовых ветвей: %d \nЖанр: %s \nСценарист: %s"}


    };


    @Override
    public Object[][] getContents() {
        return contents;
    }
}
