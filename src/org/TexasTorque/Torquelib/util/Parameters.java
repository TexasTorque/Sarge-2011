package org.TexasTorque.Torquelib.util;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.microedition.io.Connector;

public class Parameters {

    public static Vector constants;
    private String filePath;
    private FileConnection fileConnection = null;
    private BufferedReader fileIO = null;

    /*declare constants here*/
    /**
     * Make a new Parameters loader.
     *
     * @param fPath String path of parameters file.
     */
    public Parameters(String fPath) {
        filePath = fPath;

        constants = new Vector();
    }

    public Parameters() {
        this("file:///ni-rt/startup/params.txt");
    }

    /**
     * Load the parameters file using this syntax:<br><br>
     *
     * nameOfParameter valueOfParameter<br>
     * shooterMotorLeft 2<br><br>
     *
     * Constants listed in the file override hardcoded constants.
     */
    public void load() {
        try {
            fileConnection = (FileConnection) Connector.open(filePath);
            if (fileConnection.exists()) {
                fileIO = new BufferedReader(new InputStreamReader(fileConnection.openInputStream()));
                String line;
                while ((line = fileIO.readLine()) != null) {
                    int pos = line.indexOf(" ");
                    if (pos != -1) {
                        for (int i = 0; i < constants.size(); ++i) {
                            Constant c = (Constant) constants.elementAt(i);
                            if (c.getKey().equals(line.substring(0, pos))) {
                                c.value = Double.parseDouble(line.substring(pos));
                            }
                        }
                    } else {
                        System.out.println("Invalid line");
                    }
                }
                fileConnection.close();
            }
        } catch (IOException e) {
            System.out.println("Messed up reading constants");
        }
    }

    public static class Constant {

        private final String key;
        private double value;

        /**
         * Make a final Constant.
         *
         * @param key Name of value.
         * @param value Value.
         */
        public Constant(String key, double value) {
            this.key = key;
            this.value = value;

            constants.addElement(this);
        }

        public String getKey() {
            return key;
        }

        public double getDouble() {
            return value;
        }
        
        public int getInt() {
            return (int) value;
        }

        public boolean getBoolean() {
            return value == 1;
        }

        public String toString() {
            return key + ": " + value;
        }
    }
}
