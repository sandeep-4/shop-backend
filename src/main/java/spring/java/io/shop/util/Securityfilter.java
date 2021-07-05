package spring.java.io.shop.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.sql.rowset.FilteredRowSet;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.filter.DelegatingFilterProxy;

public class Securityfilter extends DelegatingFilterProxy {

	static class FilteredRequest extends HttpServletRequestWrapper {

		public FilteredRequest(HttpServletRequest request) {
			super(request);
		}

		// remove chars unwanted
		public String sanitize(String input) {
			if (input != null && !input.equals("")) {
				input = input.replaceAll("([\\\\ud840\\\\udbff\\\\udc00\\\\udfff\\\\ud800])", "");

			}
			return input;
		}

		// spcial char to hex
		private static String toUniCodeEscapeString(String str) {
			if (!StringUtil.containSpecialCharater(str)) {
				return str;
			}

			StringBuilder buf = new StringBuilder();
			int len = str.length();
			char ch;
			for (int i = 0; i < len; i++) {
				ch = str.charAt(i);

				if (!StringUtil.containSpecialCharater(String.valueOf(ch))) {
					buf.append(ch);
					continue;
				}

				switch (ch) {
				case '\\':
					buf.append("\\\\");
					break;
				case '\t':
					buf.append("\\t");
					break;
				case '\n':
					buf.append("\\n");
					break;
				case '\r':
					buf.append("\\r");
					break;

				default:

					buf.append('\\');
					buf.append('u');
					buf.append(toHex((ch >> 12) & 0xF));
					buf.append(toHex((ch >> 8) & 0xF));
					buf.append(toHex((ch >> 4) & 0xF));
					buf.append(toHex((ch >> 0) & 0xF));
				}

			}
			return buf.toString();
		}

		private static char toHex(int nibble) {
			return hexDigit[(nibble & 0xF)];
		}

		private static char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
				'f'

		};

		private String cleanXSS(String input) {
			if (input == null) {
				return input;
			}
			String value = FilteredRequest.toUniCodeEscapeString(input);

			value = StringEscapeUtils.escapeSql(value);
			return value;
		}

		@Override
		public String getParameter(String name) {
			String value = super.getParameter(name);
			if (value == null) {
				return null;
			}
			value = sanitize(value);
			return cleanXSS(value);
		}

		@Override
		public String[] getParameterValues(String name) {
			String values[] = super.getParameterValues(name);
			if (values == null) {
				return null;
			}
			int count = values.length;
			String[] encodedValues = new String[count];
			for (int i = 0; i < count; i++) {
				String v = sanitize(values[i]);
				encodedValues[i] = cleanXSS(v);
			}
			return encodedValues;
		}

		@Override
		public String getHeader(String name) {
			String value = super.getHeader(name);
			if (value == null) {
				return null;
			}
			return value;
		}

	}

	static class FilteredResponse extends HttpServletResponseWrapper {

		private static final String CONTENT_TYPE = "Content-Type";

		public FilteredResponse(HttpServletResponse response) {
			super(response);
		}

		private void setContentTypeOptions(String contentType) {
			if (isJsonContentType(contentType)) {
				doSetXContentTypeOptions();
			}

		}

		private void doSetXContentTypeOptions() {
			setHeader("X-Frame-Options", "SAMEORIGIN");
		}

		private boolean iscontentType(String headerName) {
			return headerName != null && headerName.equals(CONTENT_TYPE);
		}

		@Override
		public void setHeader(String name, String value) {
			if (iscontentType(name)) {
				setContentTypeOptions(value);
			}
			super.setHeader(name, value);
		}

		@Override
		public void addHeader(String name, String value) {
			if (iscontentType(name)) {
				setContentTypeOptions(value);
			}
			super.addHeader(name, value);
		}

		@Override
		public void setContentType(String type) {
			super.setContentType(type);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
			throws ServletException, IOException {

//        if (null == request.getCharacterEncoding()) {
//            request.setCharacterEncoding(encoding);
//        }

		// response.setContentType("text/html; charset=UTF-8");
		// response.setCharacterEncoding("UTF-8");

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

//      httpServletResponse.setHeader("X-XSS-Protection", "1; mode=block");
//      httpServletResponse.setHeader("X-Content-Type-Options", "nosniff");

		filter.doFilter(new FilteredRequest(httpServletRequest), response);
//		filter.doFilter(request, response);
	}

	@Override
	public void destroy() {
//		super.destroy();
	}

	static boolean isJsonContentType(String contentType) {
		if (contentType == null) {
			return false;
		} else {
			return contentType.matches("^application/json.*") || contentType.matches("^text/json.*");
		}
	}
}
