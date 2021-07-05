package spring.java.io.shop.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.security.auth.x500.X500Principal;

public class StringUtil {

	private StringUtil() {

	}

	public static String findAndReplace(String orig, String sub, String rep) {
		StringBuffer out = new StringBuffer();
		int index = 0;
		int oldIndex = index;
		while (index != -1) {
			index = orig.indexOf(sub, index);
			if (index != -1) {
				out.append(orig.substring(oldIndex, index));
				index += sub.length();
				oldIndex = index;
				out.append(rep);
			} else {
				out.append(orig.substring(oldIndex));
			}
		}
		return out.toString();
	}

	public static String findAndReplaceAll(String orig, Map sub) {
		Iterator i = sub.keySet().iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			String value = (String) sub.get(key);
			if (value == null) {
				continue;
			}
			orig = findAndReplace(orig, key, value);
		}
		return orig;
	}

	public static String extractCaller(Throwable t, String me) {
		try {
			if (t == null || me == null) {
				return "";
			}
			StringWriter swriter = new StringWriter(512);
			PrintWriter pwriter = new PrintWriter(swriter);
			t.printStackTrace(pwriter);
			pwriter.close();
			String st = swriter.toString();

			int index = st.indexOf(me);
			int nextIndex = st.indexOf(me, index + 1);
			while (nextIndex >= 0) {
				index = nextIndex;
				nextIndex = st.indexOf(me, index + 1);
			}

			index = st.indexOf("at", index) + 3;
			nextIndex = st.indexOf('\n', index);

			while (st.charAt(nextIndex) == '\r' || st.charAt(nextIndex) == '\r')
				nextIndex--;

			return st.substring(index, nextIndex + 1).trim();
		} catch (Exception e) {
			return "(Method not proceeded; exception logged.)";
		}
	}

	public static boolean safePath(String p) {
		return !p.startsWith("/") && !p.startsWith("../") && !p.endsWith("/..") && p.indexOf("/../") == -1;
	}

	public static String readFile(File file, String encoding) throws IOException {
		RandomAccessFile in = new RandomAccessFile(file, "r");
		long length = in.length();
		if (length <= 0) {
			in.close();
			return "";
		}

		if (length > (Integer.MAX_VALUE)) {
			in.close();
			throw new IOException("File too large");
		}
		byte[] contents = new byte[(int) length];
		in.readFully(contents);
		in.close();

		if (encoding == null) {
			return new String(contents, (byte) 0);
		} else {
			return new String(contents, encoding);
		}

	}

	public static byte[] readInputStream(InputStream input) throws IOException {
		if (input == null) {
			return new byte[0];
		}
		BufferedInputStream in;
		if (input instanceof BufferedInputStream) {
			in = (BufferedInputStream) input;
		} else {
			in = new BufferedInputStream(input);
		}
		LinkedList chunkList = new LinkedList();
		int totalLength = 0;
		while (true) {
			RISChunk chunk = new RISChunk();
			chunk.length = in.read(chunk.data);
			if (chunk.length == -1) {
				break;
			}
			if (chunk.length > 0) {
				chunkList.add(chunk);
				totalLength += chunk.length;
			}
		}
		// to array
		byte[] contents = new byte[totalLength];
		int index = 0;
		for (Iterator listi = chunkList.iterator(); listi.hasNext();) {
			RISChunk chunk = (RISChunk) listi.next();
			System.arraycopy(chunk.data, 0, contents, index, chunk.length);
			index += chunk.length;
		}
		return contents;
	}

	private static class RISChunk {
		final byte[] data = new byte[4096];
		int length;
	}

	public static String unescapeBackSlashedCharacters(String s) {
		StringBuffer sb = new StringBuffer(s.length() * 2);
		char buf[] = new char[s.length()];
		char c;

		s.getChars(0, s.length(), buf, 0);
		for (int i = 0; i < buf.length; i++) {
			if (buf[i] != '\\' || i == buf.length - 1) {
				sb.append(buf[i]);
				continue;
			}
			switch (buf[++i]) {
			case 'a':
				c = '\007';
				break;
			case 'b':
				c = '\010';
				break;
			case 't':
				c = '\011';
				break;
			case 'n':
				c = '\012';
				break;
			case 'v':
				c = '\013';
				break;
			case 'f':
				c = '\014';
				break;
			case 'r':
				c = '\015';
				break;
			case 'e':
				c = '\033';
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7': {
				int b = 0;
				for (int cnt = 0; cnt < 3; cnt++) {
					if (i == buf.length) {
						break;
					}
					char ch = buf[i++];
					if (ch < '0' || ch > '7') {
						i--;
						break;
					}
					b = (b << 3) | (ch - '0');
				}
				i--;
				c = (char) b;
				break;
			}
			default:
				c = buf[i];
				break;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	public static boolean isAscii(String s) {
		if (s == null) {
			return false;
		}
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] < 0x20 || chars[i] >= 0x7F) {
				return false;
			}
		}
		return true;
	}

	public static String toSQLHExString(String s) {
		int length = s.length();
		if (length == 0) {
			return "''";
		}
		StringBuffer retval = new StringBuffer(length * 2 + 3);
		retval.append("0x");
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			if (c > 0xff)
				c = 0x3f;
			if (c < 0x10)
				retval.append('0');
			retval.append(Integer.toHexString(c));
		}
		return retval.toString();

	}

	public static boolean contains(String container, String contained) {
		if (container == null || contained == null) {
			return false;
		}
		if (contained.equals(""))
			return true;

		char firstChar = contained.charAt(0);
		int containedLength = contained.length();

		int maxlen = container.length() - contained.length();

		int start = -1;
		while (start < maxlen) {
			start = container.indexOf(firstChar, start + 1);
			if (start < 0) {
				break;
			}
			if (container.regionMatches(start, contained, 0, containedLength)) {
				return true;
			}
		}
		return false;

	}

	public static String deleteCharacters(String string, String delChars) {
		int strlen = string.length();
		int delCount = searchString(string, delChars);
		if (delCount == 0) {
			return string;
		}

		StringBuffer buff = new StringBuffer(strlen + delCount);
		for (int i = 0; i < strlen; i++) {
			char c = string.charAt(i);
			if (delChars.indexOf(c) == -1)
				buff.append(c);
		}
		return buff.toString();
	}

	public static String replaceCharacters(String string, String fromChars, String toChar) {
		int strlen = string.length();
		int replaceCount = searchString(string, fromChars);
		if (replaceCount == 0)
			return string;

		StringBuffer buff = new StringBuffer(strlen + replaceCount);
		for (int i = 0; i < strlen; i++) {
			char c = string.charAt(i);
			if (fromChars.indexOf(c) >= 0)
				buff.append(toChar);
			else
				buff.append(c);
		}
		return buff.toString();
	}

	private static int searchString(String string, String search) {
		int strlen = string.length();
		int searchCount = 0;
		for (int i = 0; i < strlen; i++) {
			if (search.indexOf(string.charAt(i)) >= 0)
				searchCount++;
		}
		return searchCount;
	}

	public static String restrictLength(String string, int restrict) {
		if (string.length() > restrict) {
			return string.substring(0, restrict - 3) + "...";
		} else {
			return string;
		}
	}

	public static String cutOutHtmlComment(String string) {
		return cutOutString(string, "<!--", "-->");
	}

	public static String cutOutString(String string, String startStr, String endStr) {
		int startLength = startStr.length();
		int endLength = endStr.length();
		StringBuffer buf = new StringBuffer();

		int startPos = string.indexOf(startStr);
		if (startPos == -1)
			return string;

		while (startPos >= 0) {
			buf.append(string.substring(0, startPos));
			string = string.substring(startPos + startLength);
			int endPos = string.indexOf(endStr);
			if (endPos == -1) {
				return buf.toString();
			}
			string = string.substring(endPos + endLength);
			startPos = string.indexOf(startStr);
		}
		buf.append(string);
		return buf.toString();
	}

	public static String SJIS(String s) {
		try {
			byte[] bytes = s.getBytes("ISO-8859-1");
			return new String(bytes, "Shift_JIS");
		} catch (Exception e) {
			throw new InternalError("Error not supported");
		}
	}

	public static Date getDate(TimeZone timezone, String strDate) {
		Calendar calendar = Calendar.getInstance(timezone);
		try {
			int year = Integer.parseInt(strDate.substring(0, 4));
			int month = Integer.parseInt(strDate.substring(4, 6));
			int day = Integer.parseInt(strDate.substring(6, 8));
			int hour = Integer.parseInt(strDate.substring(8, 10));
			int min = Integer.parseInt(strDate.substring(10, 12));
			int sec = Integer.parseInt(strDate.substring(12, 14));

			calendar.set(year, month - 1, day, hour, min, sec);
		} catch (NumberFormatException e) {
			return null;
		} catch (IndexOutOfBoundsException ee) {
			return null;
		}
		return calendar.getTime();
	}

	public static Date getDate(String timezone, String strDate) {
		return getDate(TimeZone.getTimeZone(timezone), strDate);
	}

	// getting ids done

	public static String getCanonicalDN(String dn) {
		String canonical;
		try {
			canonical = new X500Principal(dn).getName(X500Principal.CANONICAL);
		} catch (Exception e) {
			canonical = dn.trim();
		}
		return canonical;
	}

	public static String toHex(String str) throws IllegalArgumentException {
		if (str == null) {
			throw new IllegalArgumentException("String was null");
		}

		if (str.length() == 0) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		char[] cbuf = str.toCharArray();
		for (int idx = 0; idx < cbuf.length; idx++) {
			sb.append("0x" + Integer.toHexString(cbuf[idx]) + " ");

		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}

	// hex
	private static final char _hexcodes[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };
	private static final int _shifts[] = { 28, 24, 20, 16, 12, 8, 4, 0 };

	public static String toHexCode(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			for (int j = 0; j < 2; j++) {
				sb.append(_hexcodes[(bytes[i] >> _shifts[j + 6]) & 15]);
			}
		}
		return sb.toString();
	}

	public static byte[] toHexArray(String str) {
		if (str == null || str.trim().length() != 32) {
			throw new IllegalStateException("Hex String was too short. [" + str + "]");
		}
		byte[] bytes = new byte[16];
		for (int kidx = 0, bidx = 0; kidx < str.length(); kidx += 2, bidx++)
			bytes[bidx] = (byte) Integer.parseInt(str.substring(kidx, kidx + 2), 16);

		return bytes;

	}

	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isEmpty(String str, boolean trim) {
		if (str == null) {
			return true;
		}
		return trim ? str.trim().length() == 0 : str.length() == 0;
	}

	public static boolean isEmpty(String str) {
		return isEmpty(str, false);
	}

	public static void replace(String infilename, String outfilename, String target, String replace) throws Exception {
		String[] lines = FileUtil.getLines(infilename);
		for (int j = 0; j < lines.length; j++) {
			lines[j] = lines[j].replaceAll(target, replace);

		}
		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(outfilename)));
		for (int j = 0; j < lines.length; j++) {
			output.print(lines[j]);
		}
		output.close();
	}

	public static void replace(String infilename, String outfilename, List<Map> replaceItems) throws Exception {
		String[] lines = FileUtil.getLines(infilename);
		for (int j = 0; j < lines.length; j++) {
			for (int i = 0; i < replaceItems.size(); i++) {
				String target = replaceItems.get(i).get("target").toString();
				String replace = replaceItems.get(i).get("replace").toString();
				lines[j] = lines[j].replace(target, replace);
			}
		}
		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(outfilename)));
		for (int j = 0; j < lines.length; j++) {
			output.println(lines[j]);
		}
		output.close();
	}

	public static String urlEncode(String str) {
		try {
			if (str != null) {
				return URLEncoder.encode(str, "utf-8");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static boolean isNumber(String str) {
		if (str.trim().equals("")) {
			return false;
		} else {
			for (char c : str.toCharArray()) {
				if (!Character.isDigit(c))
					return false;
			}
		}
		return true;
	}

	public static boolean isNumberUsingRegex(String str) {
		return str.matches("-?\\\\d+(\\\\.\\\\d+)?");
	}

	public static boolean isBoolean(String str) {
		if (str == null)
			return false;
		else {
			String value = str.toLowerCase().trim();
			return "true".equals(value) || "false".equals(value);
		}
	}

	public static boolean isListNumber(String[] arrayStr) {
		boolean check = true;
		if (arrayStr.length > 0) {
			for (int i = 0; i < arrayStr.length; i++) {
				check = false;
				break;
			}
		} else {
			check = false;
		}
		return check;
	}

	public static boolean containSpecialCharater(String str) {
		String specialChars = "<>%*+[]?|\\\\=";
		boolean check = false;
		if (str != null && !str.equals("")) {
			for (int i = 0; i < specialChars.length(); i++) {
				if (str.indexOf(specialChars.charAt(i)) > -1) {
					check = true;
					break;
				}
			}
		}
		return check;
	}

	public static boolean checkValidData(String[] data) throws IOException {
		String[] listData = new String[data.length];
		for (int i = 0; i < data.length; i++) {
			listData[i] = data[i].trim();
		}
		boolean check = false;
		switch (listData.length) {
		case 3:
			String name = listData[0] + listData[1];
			check = !StringUtil.containSpecialCharater(name) && EmailUtil.isEmailFormat(listData[2])
					&& name.length() <= 64 && listData[2].length() <= 256;
			break;
		case 4:
			String name1 = listData[0] + listData[1] + listData[2];
			check = !StringUtil.containSpecialCharater(name1) && EmailUtil.isEmailFormat(listData[3])
					&& name1.length() <= 96 && listData[3].length() <= 255;
			break;
		case 5:
			String name2 = listData[0] + listData[1] + listData[2];
			check = !StringUtil.containSpecialCharater(name2) && EmailUtil.isEmailFormat(listData[3])
					&& name2.length() <= 96 && listData[3].length() <= 255 && listData[4].length() <= 128;
			break;
		default:
			check = false;
			break;
		}
		return check;
	}
	 public static boolean checkValidUserData(String[] data) throws IOException {
	        String[] listData = new String[data.length];
	        //Trim all element in this array
	        for (int i = 0; i < data.length; i++){
	            listData[i] = data[i].trim();
	        }
	        boolean check = false;
	        switch (listData.length){
	            case 4: 
	                String name = listData[0]+listData[1];
	                check = !StringUtil.containSpecialCharater(name) && EmailUtil.isEmailFormat(listData[2]) && name.length()<=64 && listData[2].length()<=32 && listData[2].length()>=6 && listData[3].length()<=255;
	                break;
	            case 5: 
	                String name1 = listData[0]+listData[1]+listData[2];
	                check = !StringUtil.containSpecialCharater(name1) && EmailUtil.isEmailFormat(listData[4]) && name1.length() <= 96  && listData[3].length()<=32 && listData[3].length()>=6 && listData[4].length()<=255;
	                break;
	            case 6: 
	                String name2 = listData[0]+listData[1]+listData[2];
	                check = !StringUtil.containSpecialCharater(name2) && EmailUtil.isEmailFormat(listData[4]) && name2.length() <= 96  && listData[3].length()<=32 && listData[3].length()>=6 && listData[4].length()<=255 && listData[5].length()<=128;
	                break;
	            default:
	                check = false;
	                break;
	        }
	       return check;
	    }
	}