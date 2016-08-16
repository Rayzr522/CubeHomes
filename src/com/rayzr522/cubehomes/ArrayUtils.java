
package com.rayzr522.cubehomes;

import java.util.List;

public class ArrayUtils {

	public static String concat(Object[] arr, String filler) {

		if (arr.length < 1) { return ""; }

		filler = filler == null ? "" : filler;

		String output = arr[0].toString();

		for (int i = 1; i < arr.length; i++) {

			output += filler + arr[i].toString();

		}

		return output;

	}

	public static String[] names(List<Home> homes) {

		String[] output = new String[homes.size()];

		for (int i = 0; i < homes.size(); i++) {

			output[i] = homes.get(i).getName();

		}

		return output;

	}

}
