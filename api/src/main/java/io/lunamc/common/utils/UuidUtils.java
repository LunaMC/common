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

package io.lunamc.common.utils;

import java.util.UUID;

public class UuidUtils {

    private static final int HEX_RADIX = 16;
    private static final char DELIMITER = '-';

    private UuidUtils() {
        throw new UnsupportedOperationException(getClass().getSimpleName() + " is a utility class and should not be constructed");
    }

    public static String normalize(String uuid) {
        return parseUuid(uuid).toString().toLowerCase();
    }

    /**
     * Parse a {@link UUID} from a {@link String}. Supports both concatenated ({@code aaaaaaaabbbbccccddddeeeeeeeeeeee})
     * and delimited (@code aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee} uuids.
     *
     * @param name The string to be parsed
     * @throws IllegalArgumentException When an invalid uuid is provided
     * @return A {@link UUID}
     */
    public static UUID parseUuid(String name) {
        try {
            if (name.length() == 32)
                return parseConcatenatedUuid(name);
            else if (name.length() == 36)
                return parseDelimitedUuid(name);
            else
                throw new IllegalArgumentException("Invalid UUID string: " + name);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid UUID string: " + name, e);
        }
    }

    private static UUID parseDelimitedUuid(String name) {
        if (name.charAt(8) != DELIMITER || name.charAt(13) != DELIMITER || name.charAt(18) != DELIMITER || name.charAt(23) != DELIMITER)
            throw new IllegalArgumentException("Invalid UUID string: " + name);
        long mostSigBits = Long.parseLong(name.substring(0, 8), HEX_RADIX);
        mostSigBits <<= 16;
        mostSigBits |= Long.parseLong(name.substring(9, 13), HEX_RADIX);
        mostSigBits <<= 16;
        mostSigBits |= Long.parseLong(name.substring(14, 18), HEX_RADIX);

        long leastSigBits = Long.parseLong(name.substring(19, 23), HEX_RADIX);
        leastSigBits <<= 48;
        leastSigBits |= Long.parseLong(name.substring(24), HEX_RADIX);

        return new UUID(mostSigBits, leastSigBits);
    }

    private static UUID parseConcatenatedUuid(String name) {
        long mostSigBits = Long.parseLong(name.substring(0, 8), HEX_RADIX);
        mostSigBits <<= 16;
        mostSigBits |= Long.parseLong(name.substring(8, 12), HEX_RADIX);
        mostSigBits <<= 16;
        mostSigBits |= Long.parseLong(name.substring(12, 16), HEX_RADIX);

        long leastSigBits = Long.parseLong(name.substring(16, 20), HEX_RADIX);
        leastSigBits <<= 48;
        leastSigBits |= Long.parseLong(name.substring(20, 32), HEX_RADIX);

        return new UUID(mostSigBits, leastSigBits);
    }
}
