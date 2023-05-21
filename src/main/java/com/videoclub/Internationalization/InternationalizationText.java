package com.videoclub.Internationalization;

import java.util.Locale;
import java.util.ResourceBundle;

public class InternationalizationText {
	public static String language = "en";
	private static String SYSTEM_MESSAGES = "SystemMessages";
	private static ResourceBundle res = ResourceBundle.getBundle(SYSTEM_MESSAGES, Locale.forLanguageTag(InternationalizationText.language));
	
	/** Function to change the language
	 * @param newLang New Language
	 */
	public static void setLanguage(String newLang) {
		language = newLang;
		res = ResourceBundle.getBundle(SYSTEM_MESSAGES, Locale.forLanguageTag(InternationalizationText.language));
	}
	
	/**Function to retrieve the word or phrase in the language.
	 * @param a String
	 * @return The string in the language
	 */
	public static String getString(String a) {
		return res.getString(a);
	}

}
