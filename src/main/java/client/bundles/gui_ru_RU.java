package client.bundles;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.util.ListResourceBundle;

public class gui_ru_RU extends ListResourceBundle {
    private static final Object[][] contents = {

            {"CommandSuccess","Команда выполнена успешно"},
            //{"AddSuccess", "Элемент добавлен в коллекцию!"},
            {"AddError","Возникла ошибка при добавлении фильма в базу данных"},
            {"AddIfMaxError", "Ошибка добавления максимального элемента"},
            //{"AddIfMaxSuccess", "Максимальный элемент успешно добавлен"},
            //{"AddIfMaxNotSuccess", "Элемент не был максимальным, в коллекцию не добавлен"},
            {"ScriptError", "Во время выполнения скрипта произошла ошибка"},
            {"RegisterError", "Ошибка при регистрации"},
            {"RegisterError", "Ошибка при входе в учетную запись"},
            {"RemoveError", "Ошибка при удалении элемента"},
            {"RemoveGreaterError", "Ошибка при удалении больших элементов"},
            {"RemoveByScreenwriterError", "Ошибка при удалении элемента по сценаристу"},
            {"UpdateError", "Ошибка при обновлении элемента"},

            {"HelpMessage", "Справка:\n" +
                    "Внизу окна расположены кнопки взаимодействия с базой. " +
                    "Для выполнения действий по удалению или обновлению необходимо выбрать строку таблицы. " +
                    "Команда \"Очистить\" удаляет из БД лишь те объекты, что принадлежат вам.\n" +
                    "В разделе \"Визуализация\" вы найдете визуальное представление объектов БД. " +
                    "Двойной клик по объекту даст возможность его отредактировать."},
            //{"InfoMessage", "Информация о коллекции: \n Дата доступа: {0} \n Тип: {1} \n Размер: {2}"},

            {"idColumn", "Идентификатор"},
            {"nameColumn", "Имя"},
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

            {"addButton", "Добавить элемент"},
            {"addIfMaxButton", "Добавить макс. элемент"},
            {"clearButton", "Очистить"},
            {"executeScriptButton", "Исполнить скипт"},
            {"gPalmsFilterButton", "Оставить фильмы с большим числом пальмовых ветвей"},
            {"helpButton", "Помощь"},
            {"historyButton", "История"},
            {"infoButton", "Информация"},
            {"removeByScreenwriterButton", "Удалить по сценаристу"},
            {"removeByIdButton", "Удалить по id"},
            {"removeGreaterButton", "Удалить большие"},
            {"updateButton", "Обновить элемент"},

            {"ConnectionError", "Ошибка соединения"},
            {"Disconnected", "Соединение не установлено..."},
            {"Connected", "Соединение установлено"},

            {"usernameLabel", "Логин"},
            {"passwordLabel", "Пароль"},
            {"registerCheckBox", "Регистрация"},
            {"signInButton", "Подтвердить"},

    };

/*
<Button fx:id="addButton" onMouseEntered="#buttonHighlighted" onMouseExited="#buttonNormalized" mnemonicParsing="false" onAction="#addButtonOnAction" prefHeight="25.0" prefWidth="112.0" style="-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;" text="Add" />
                        <Button fx:id="addIfMaxButton" onMouseEntered="#buttonHighlighted" onMouseExited="#buttonNormalized" mnemonicParsing="false" onAction="#addIfMaxButtonOnAction" prefHeight="25.0" prefWidth="112.0" style="-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;" text="AddIfMax" />
                        <Button fx:id="clearButton" onMouseEntered="#buttonHighlighted" onMouseExited="#buttonNormalized" mnemonicParsing="false" prefHeight="25.0" prefWidth="112.0" onAction="#clearButtonOnAction" style="-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;" text="Clear" />
                        <Button fx:id="executeScriptButton" onMouseEntered="#buttonHighlighted" onMouseExited="#buttonNormalized" mnemonicParsing="false" prefHeight="25.0" prefWidth="112.0" style="-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;" text="ExecuteScript" />
                        <Button fx:id="gPalmsFilterButton" onMouseEntered="#buttonHighlighted" onMouseExited="#buttonNormalized" mnemonicParsing="false" prefHeight="25.0" prefWidth="112.0" style="-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;" text="GPalmsFilter" />
                        <Button fx:id="helpButton" onMouseEntered="#buttonHighlighted" onMouseExited="#buttonNormalized" mnemonicParsing="false" prefHeight="25.0" prefWidth="112.0" style="-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;" text="Help" />
                        <Button fx:id="historyButton" onMouseEntered="#buttonHighlighted" onMouseExited="#buttonNormalized" mnemonicParsing="false" prefHeight="25.0" prefWidth="112.0" style="-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;" text="History" />
                        <Button fx:id="infoButton" onMouseEntered="#buttonHighlighted" onMouseExited="#buttonNormalized" mnemonicParsing="false" prefHeight="25.0" prefWidth="112.0" style="-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;" text="Info" />
                        <Button fx:id="removeByScreenwriterButton" onMouseEntered="#buttonHighlighted" onMouseExited="#buttonNormalized" onAction="#removeByScreenwriterButtonOnAction" mnemonicParsing="false" prefHeight="25.0" prefWidth="112.0" style="-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;" text="RemoveByScreenwriter" />
                        <Button fx:id="removeByIdButton" onMouseEntered="#buttonHighlighted" onMouseExited="#buttonNormalized" onAction="#removeByIdButtonOnAction" mnemonicParsing="false" prefHeight="25.0" prefWidth="112.0" style="-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;" text="Remove" />
                        <Button fx:id="removeGreaterButton" onMouseEntered="#buttonHighlighted" onMouseExited="#buttonNormalized" onAction="#removeGreaterButtonOnAction" mnemonicParsing="false" prefHeight="25.0" prefWidth="112.0" style="-fx-border-width: 1; -fx-border-color: #777571;-fx-background-color: #FCF8F2;" text="RemoveGreater" />
                        <Button fx:id="updateButton"
 */

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
