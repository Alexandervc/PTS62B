/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;
import org.apache.commons.io.IOUtils;

/**
 * Util for CLOB.
 *
 * @author Alexander
 */
public class ClobUtil {

    /**
     * Convert the given clob to a String.
     *
     * @param clob The clob to convert.
     * @return The converted String.
     * @throws SQLException
     * @throws IOException
     */
    public static String convertClobToString(Clob clob) throws SQLException, IOException {
        InputStream in = clob.getAsciiStream();
        StringWriter w = new StringWriter();
        IOUtils.copy(in, w);
        return w.toString();
    }
}
