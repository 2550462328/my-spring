package cn.zhanghui.myspring.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: StringUtils.java
 * @Description: Spring的String工具类
 * @author: ZhangHui
 * @date: 2019年10月28日 下午3:22:00
 */

public abstract class StringUtils {
	private static final String FOLDER_SEPARATOR = "/";
	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";
	private static final String TOP_PATH = "..";
	private static final String CURRENT_PATH = ".";
	private static final char EXTENSION_SEPARATOR = '.';

	public static boolean isEmpty(Object str) {
		return (str == null) || ("".equals(str));
	}

	public static boolean hasLength(CharSequence str) {
		return (str != null) && (str.length() > 0);
	}

	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsWhitespace(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsWhitespace(String str) {
		return containsWhitespace(str);
	}

	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(0)))) {
			sb.deleteCharAt(0);
		}
		while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(sb.length() - 1)))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		int len = str.length();
		StringBuilder sb = new StringBuilder(str.length());
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (!Character.isWhitespace(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(0)))) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	public static String trimTrailingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(sb.length() - 1)))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String trimLeadingCharacter(String str, char leadingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while ((sb.length() > 0) && (sb.charAt(0) == leadingCharacter)) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	public static String trimTrailingCharacter(String str, char trailingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while ((sb.length() > 0) && (sb.charAt(sb.length() - 1) == trailingCharacter)) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if ((str == null) || (prefix == null)) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if ((str == null) || (suffix == null)) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}
		String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}

	public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
		for (int j = 0; j < substring.length(); j++) {
			int i = index + j;
			if ((i >= str.length()) || (str.charAt(i) != substring.charAt(j))) {
				return false;
			}
		}
		return true;
	}

	public static int countOccurrencesOf(String str, String sub) {
		if ((str == null) || (sub == null) || (str.length() == 0) || (sub.length() == 0)) {
			return 0;
		}
		int count = 0;
		int pos = 0;
		int idx;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			count++;
			pos = idx + sub.length();
		}
		return count;
	}

	public static String replace(String inString, String oldPattern, String newPattern) {
		if ((!hasLength(inString)) || (!hasLength(oldPattern)) || (newPattern == null)) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int index = inString.indexOf(oldPattern);

		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));

		return sb.toString();
	}

	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	public static String deleteAny(String inString, String charsToDelete) {
		if ((!hasLength(inString)) || (!hasLength(charsToDelete))) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String quote(String str) {
		return str != null ? "'" + str + "'" : null;
	}

	public static Object quoteIfString(Object obj) {
		return (obj instanceof String) ? quote((String) obj) : obj;
	}

	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	public static String[] commaDelimitedListToStringArray(String str) {
		return delimitedListToStringArray(str, ",");
	}

	public static String[] delimitedListToStringArray(String str, String delimiter) {
		return delimitedListToStringArray(str, delimiter, null);
	}

	private static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {

		if (str == null) {
			return new String[0];
		}
		if (delimiter == null) {
			return new String[] { str };
		}

		List<String> result = new ArrayList<>();
		if (delimiter.isEmpty()) {
			for (int i = 0; i < str.length(); i++) {
				result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
			}
		} else {
			int pos = 0;
			int delPos;
			while ((delPos = str.indexOf(delimiter, pos)) != -1) {
				result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
				pos = delPos + delimiter.length();
			}
			if (str.length() > 0 && pos <= str.length()) {
				// Add rest of String, but not in case of empty input.
				result.add(deleteAny(str.substring(pos), charsToDelete));
			}
		}
		return toStringArray(result);
	}
	
	public static String[] toStringArray(Collection<String> collection) {
		return collection.toArray(new String[0]);
	}
}
