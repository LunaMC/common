/*
 *  Copyright 2017 LunaMC.io
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.lunamc.common.text;

public enum ComponentColor {

    BLACK("black", '0'),
    DARK_BLUE("dark_blue", '1'),
    DARK_GREEN("dark_green", '2'),
    DARK_CYAN("dark_cyan", '3'),
    DARK_RED("dark_red", '4'),
    DARK_PURPLE("dark_purple", '5'),
    GOLD("gold", '6'),
    GRAY("gray", '7'),
    DARK_GRAY("dark_gray", '8'),
    BLUE("blue", '9'),
    BRIGHT_GREEN("green", 'a'),
    CYAN("cyan", 'b'),
    RED("red", 'c'),
    PINK("light_purple", 'd'),
    YELLOW("yellow", 'e'),
    WHITE("white", 'f');

    public static final char LEGACY_COLOR_CHAR = '§';

    private final String name;
    private final char code;
    private final String legacyValue;

    ComponentColor(String name, char code) {
        this.name = name;
        this.code = code;

        legacyValue = new String(new char[] { LEGACY_COLOR_CHAR, code });
    }

    public String getName() {
        return name;
    }

    public char getCode() {
        return code;
    }

    @Override
    public String toString() {
        return legacyValue;
    }
}
