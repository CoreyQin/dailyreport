function Base64Util() {

	
	/*var map = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"; // Base64从0到63的对应编码字符集

	Base64Util.prototype.unicodeToByte = function(str) // 将Unicode字符串转换为UCS-16编码的字节数组
	{
		var result = [];
		for (var i = 0; i < str.length; i++)
			result.push(str.charCodeAt(i) >> 8, str.charCodeAt(i) & 0xff);
		return result;
	}

	Base64Util.prototype.byteToUnicode = function(arr) // 将UCS-16编码的字节数组转换为Unicode字符串
	{
		var result = "";
		for (var i = 0; i < arr.length; i += 2)
			result += String.fromCharCode((arr[i] << 8) + arr[i + 1]);
		return result;
	}

	Base64Util.prototype.encodeBase64 = function(str) {
		var buffer, result = "", flag = 0; // flag表示在字节数组剩余的个数
		var arr = this.unicodeToByte(str);
		flag = arr.length % 3;
		if (flag == 1)
			arr.push(0, 0);
		else if (flag == 2)
			arr.push(0);
		for (var i = 0; i < arr.length; i += 3) // 此时arr.length一定能被3整除
		{
			buffer = (arr[i] << 16) + (arr[i + 1] << 8) + arr[i + 2];
			result += map.charAt(buffer >> 18)
					+ map.charAt(buffer >> 12 & 0x3f)
					+ map.charAt(buffer >> 6 & 0x3f)
					+ map.charAt(buffer & 0x3f);
		}
		if (flag == 1)
			resultresult = result.replace(/AA$/g, "==");
		else if (flag == 2)
			resultresult = result.replace(/A$/g, "=");
		return result;
	}

	Base64Util.prototype.decodeBase64 = function(str) {
		// 逆向映射数字索引和Base64编码字符集（简单Hash）
		var s = "var base64={";
		for (var i = 0; i < 64; i++)
			s += "\"" + map.charAt(i) + "\":" + i + ",";
		s += "\"=\":0};"; // 将"="字符对应的编码定义为0，相当于将=字符转换为A字符
		eval(s);
		var buffer, result = [];
		for (i = 0; i < str.length; i += 4) // 由于包含Base64末尾包含1个或2个=字符，故str.length一定能被4整除
		{
			buffer = (base64[str.charAt(i)] << 18)
					+ (base64[str.charAt(i + 1)] << 12)
					+ (base64[str.charAt(i + 2)] << 6)
					+ base64[str.charAt(i + 3)];
			result.push(buffer >> 16, buffer >> 8 & 0xff, buffer & 0xff);
		}
		if (/==$/g.test(str)) // 如解码为字符串可不做该处理
		{
			result.pop();
			result.pop();
		} else if (/=$/g.test(str))
			result.pop();
		return this.byteToUnicode(result);
	}*/

	Base64Util.prototype.utf16to8 = function(str) {
		var out, i, len, c;
		out = "";
		len = str.length;
		for (i = 0; i < len; i++) {
			c = str.charCodeAt(i);
			if ((c >= 0x0001) && (c <= 0x007F)) {
				out += str.charAt(i);
			} else if (c > 0x07FF) {
				out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
				out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
				out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
			} else {
				out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
				out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
			}
		}
		return out;
	}

	Base64Util.prototype.utf8to16 = function(str) {
		var out, i, len, c;
		var char2, char3;
		out = "";
		len = str.length;
		i = 0;
		while (i < len) {
			c = str.charCodeAt(i++);
			switch (c >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				// 0xxxxxxx
				out += str.charAt(i - 1);
				break;
			case 12:
			case 13:
				// 110x xxxx 10xx xxxx
				char2 = str.charCodeAt(i++);
				out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
				break;
			case 14:
				// 1110 xxxx 10xx xxxx 10xx xxxx
				char2 = str.charCodeAt(i++);
				char3 = str.charCodeAt(i++);
				out += String.fromCharCode(((c & 0x0F) << 12)
						| ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
				break;
			}
		}
		return out;
	}

	var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	var base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62,
			-1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1,
			-1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
			15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1,
			26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
			43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
	
	Base64Util.prototype.base64encode = function (str) {
		var out, i, len;
		var c1, c2, c3;
		len = str.length;
		i = 0;
		out = "";
		while (i < len) {
			c1 = str.charCodeAt(i++) & 0xff;
			if (i == len) {
				out += base64EncodeChars.charAt(c1 >> 2);
				out += base64EncodeChars.charAt((c1 & 0x3) << 4);
				out += "==";
				break;
			}
			c2 = str.charCodeAt(i++);
			if (i == len) {
				out += base64EncodeChars.charAt(c1 >> 2);
				out += base64EncodeChars.charAt(((c1 & 0x3) << 4)
						| ((c2 & 0xF0) >> 4));
				out += base64EncodeChars.charAt((c2 & 0xF) << 2);
				out += "=";
				break;
			}
			c3 = str.charCodeAt(i++);
			out += base64EncodeChars.charAt(c1 >> 2);
			out += base64EncodeChars.charAt(((c1 & 0x3) << 4)
					| ((c2 & 0xF0) >> 4));
			out += base64EncodeChars.charAt(((c2 & 0xF) << 2)
					| ((c3 & 0xC0) >> 6));
			out += base64EncodeChars.charAt(c3 & 0x3F);
		}
		return out;
	}
	
	Base64Util.prototype.base64decode = function (str) {
		var c1, c2, c3, c4;
		var i, len, out;
		len = str.length;
		i = 0;
		out = "";
		while (i < len) {
			/* c1 */
			do {
				c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
			} while (i < len && c1 == -1);
			if (c1 == -1)
				break;
			/* c2 */
			do {
				c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
			} while (i < len && c2 == -1);
			if (c2 == -1)
				break;
			out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
			/* c3 */
			do {
				c3 = str.charCodeAt(i++) & 0xff;
				if (c3 == 61)
					return out;
				c3 = base64DecodeChars[c3];
			} while (i < len && c3 == -1);
			if (c3 == -1)
				break;
			out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
			/* c4 */
			do {
				c4 = str.charCodeAt(i++) & 0xff;
				if (c4 == 61)
					return out;
				c4 = base64DecodeChars[c4];
			} while (i < len && c4 == -1);
			if (c4 == -1)
				break;
			out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
		}
		return out;
	}
}

var base64Util = new Base64Util();
