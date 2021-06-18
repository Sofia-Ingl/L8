package client.util;


import shared.data.*;

import java.io.EOFException;
import java.io.IOException;
import java.util.Scanner;


/**
 * Класс, реализующий опрос пользователя и создание экземпляров класса Movie на основе полученной информации.
 */
public class UserElementGetter {

    private Scanner scanner = null;


    public UserElementGetter() {
    }


    /**
     * Интерактивный метод, получающий посредством опроса пользователя значения всех нестатических полей класса Movie.
     * На их основе создает и возвращает новый экземпляр.
     * Обладает встроенными проверками корректности значений каждого поля.
     *
     * @return экземпляр Movie, созданный на основе пользовательского ввода.
     */
    public Movie movieGetter() throws EOFException {
        if (getScanner() != null) {
            return new Movie(nameGetter(), coordinatesGetter(), oscarsCountGetter(), heightOrPalmsCountGetter(),
                    taglineGetter(), genreGetter(), screenwriterGetter());
        } else {
            return null;
        }

    }

    private String nameGetter() throws EOFException {

        String line;
        do {
            if (getScanner() == null || !getScanner().hasNextLine()) throw new EOFException();
            line = getScanner().nextLine().trim();
        } while (line.isEmpty());

        return line.substring(0, 1).toUpperCase() + line.substring(1);
    }

    private Coordinates coordinatesGetter() throws EOFException {
        String[] xAndY;
        boolean exceptions;
        Coordinates coordinates = null;
        do {
            exceptions = false;
            if (getScanner() == null || !getScanner().hasNextLine()) throw new EOFException();
            xAndY = getScanner().nextLine().trim().concat(" ").split(" ", 2);
            try {
                coordinates = new Coordinates(Float.parseFloat(xAndY[0].trim()), Integer.parseInt(xAndY[1].trim()));
                if (coordinates.getX() > 326 || coordinates.getY() > 281) {
                    exceptions = true;
                }
            } catch (NumberFormatException e) {
                exceptions = true;
            }
        } while (exceptions);
        return coordinates;
    }

    private int oscarsCountGetter() throws EOFException {
        int oscars = 0;
        boolean exceptions;
        do {
            exceptions = false;
            try {
                if (getScanner() == null || !getScanner().hasNextLine()) throw new EOFException();
                oscars = Integer.parseInt(getScanner().nextLine().trim());
                if (oscars < 1) {
                    exceptions = true;
                }
            } catch (NumberFormatException e) {
                exceptions = true;
            }
        } while (exceptions);
        return oscars;
    }

    private long heightOrPalmsCountGetter() throws EOFException {
        long palmsOrHeight = 0;
        boolean exceptions;
        do {
            exceptions = false;
            try {
                if (getScanner() == null || !getScanner().hasNextLine()) throw new EOFException();
                palmsOrHeight = Long.parseLong(getScanner().nextLine().trim());
                if (palmsOrHeight < 1) {
                    exceptions = true;
                }
            } catch (NumberFormatException e) {
                exceptions = true;
            }
        } while (exceptions);
        return palmsOrHeight;
    }

    private String taglineGetter() throws EOFException {
        if (getScanner() == null || !getScanner().hasNextLine()) throw new EOFException();
        return getScanner().nextLine().trim();
    }

    private MovieGenre genreGetter() throws EOFException {


        MovieGenre genre = null;
        boolean exceptions;
        do {
            exceptions = false;
            try {
                if (getScanner() == null || !getScanner().hasNextLine()) throw new EOFException();
                genre = MovieGenre.valueOf(getScanner().nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                exceptions = true;
            }
        } while (exceptions);

        return genre;
    }

    private Person screenwriterGetter() throws EOFException {
        return new Person(nameGetter(), heightOrPalmsCountGetter(), eyeColorGetter(), nationalityGetter());
    }

    private Color eyeColorGetter() throws EOFException {
        Color color = null;
        String colorString;
        boolean exceptions;
        do {
            exceptions = false;
            try {

                if (getScanner() == null || !getScanner().hasNextLine()) throw new EOFException();
                colorString = getScanner().nextLine().trim().toUpperCase();
                if (!colorString.equals("")) {
                    color = Color.valueOf(colorString);
                }
            } catch (IllegalArgumentException e) {
                exceptions = true;
            }
        } while (exceptions);

        return color;
    }

    private Country nationalityGetter() throws EOFException {
        Country country = null;
        String nationality;
        boolean exceptions;
        do {
            exceptions = false;
            try {
                if (getScanner() == null || !getScanner().hasNextLine()) throw new EOFException();
                nationality = getScanner().nextLine().trim().toUpperCase();
                if (!nationality.equals("")) {
                    country = Country.valueOf(nationality);
                }
            } catch (IllegalArgumentException e) {
                exceptions = true;
            }
        } while (exceptions);
        return country;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
