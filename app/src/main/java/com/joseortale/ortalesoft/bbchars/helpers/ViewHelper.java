package com.joseortale.ortalesoft.bbchars.helpers;

import com.joseortale.ortalesoft.bbchars.model.Character;

import java.util.ArrayList;
import java.util.List;

public class ViewHelper {
    public static List<Character> filterCharByName(String name, List<Character> list) {
        List<Character> filteredList = new ArrayList<>();

        for (Character character : list) {
            if (character.getName().toLowerCase().contains(name.toLowerCase())) {
                filteredList.add(character);
            }
        }

        return filteredList;
    }

    public static List<Character> filterCharBySeason(Integer season, List<Character> list) {
        List<Character> filteredList = new ArrayList<>();

        for (Character character : list) {
            for (Integer seasonAppearance : character.getAppearance()) {
                if (seasonAppearance.equals(season)) {
                    filteredList.add(character);
                }
            }
        }

        return filteredList;
    }
}
