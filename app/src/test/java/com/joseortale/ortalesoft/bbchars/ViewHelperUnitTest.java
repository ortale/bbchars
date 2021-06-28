package com.joseortale.ortalesoft.bbchars;

import com.joseortale.ortalesoft.bbchars.helpers.ViewHelper;
import com.joseortale.ortalesoft.bbchars.model.Character;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ViewHelperUnitTest {
    private List<Character> characters;

    @Before
    public void initialise() {
        characters = new ArrayList<>();

        List<Integer> appearance = new ArrayList<>();
        appearance.add(1);
        appearance.add(2);
        appearance.add(3);

        Character character = new Character();
        character.setId(1);
        character.setName("Jose");
        character.setAppearance(appearance);
        characters.add(character);

        appearance = new ArrayList<>();
        appearance.add(1);

        character = new Character();
        character.setId(2);
        character.setName("Rodolfo");
        character.setAppearance(appearance);
        characters.add(character);

        appearance = new ArrayList<>();
        appearance.add(2);
        appearance.add(3);
        appearance.add(4);

        character = new Character();
        character.setId(3);
        character.setName("Freitas");
        character.setAppearance(appearance);
        characters.add(character);

        appearance = new ArrayList<>();

        character = new Character();
        character.setId(3);
        character.setName("Ortale");
        character.setAppearance(appearance);
        characters.add(character);
    }

    @Test
    public void haveCharByName() {
        String name = "Jose";

        List<Character> charactersTest = ViewHelper.filterCharByName(name, characters);

        assertFalse(charactersTest.isEmpty());
    }

    @Test
    public void haveCharBySeason() {
        Integer season = 1;

        List<Character> charactersTest = ViewHelper.filterCharBySeason(season, characters);
        List<Character> finalChar = new ArrayList<>();

        for (Character character : charactersTest) {
            for (Integer seasonAppearance : character.getAppearance()) {
                if (seasonAppearance.equals(season)) {
                    finalChar.add(character);
                }
            }
        }

        assertFalse(finalChar.isEmpty());
    }
}