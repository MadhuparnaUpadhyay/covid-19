package live.combatemic.app.Common;

import java.util.Scanner;

public class Version {

    public static int compare(String str1, String str2) {
        try (Scanner s1 = new Scanner(str1);
             Scanner s2 = new Scanner(str2);) {
            s1.useDelimiter("\\.");
            s2.useDelimiter("\\.");

            while (s1.hasNextInt() && s2.hasNextInt()) {
                int v1 = s1.nextInt();
                int v2 = s2.nextInt();
                if (v1 < v2) {
                    return -1;
                } else if (v1 > v2) {
                    return 1;
                }
            }

            if (s1.hasNextInt() && s1.nextInt() != 0)
                return 1; //str1 has an additional lower-level version number
            if (s2.hasNextInt() && s2.nextInt() != 0)
                return -1; //str2 has an additional lower-level version

            return 0;
        } // end of try-with-resources
    }
}
