/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.Arrays;

/**
 * Provides functionality regarding arrays.
 * @author jesblo
 */
public class ArrayHelper {
    
    /**
     * Instantiates the ArrayHelper class.
     */
    private ArrayHelper() {
        // Empty private constructor for the utility class.
    }
    
    /**
    * Combines two arrays.
    * @param <T> The type.
    * @param first The first array.
    * @param second The second array.
    * @return The combined array.
    */
   public static <T> T[] concat(T[] first, T[] second) {
       T[] result = Arrays.copyOf(first, first.length + second.length);
       System.arraycopy(second, 0, result, first.length, second.length);
       return result;
   }
}