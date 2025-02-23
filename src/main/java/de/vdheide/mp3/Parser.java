//de.vdheide.mp3: Access MP3 properties, ID3 and ID3v2 tags
//Copyright (C) 1999-2004 Jens Vonderheide <jens@vdheide.de>

/*
 * This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.vdheide.mp3;

/**
 * Class used to parse a byte array and extract text or binary information It
 * maintained a pointer so that parse operations are always performed on the
 * first byte after end of the last operation. All parsing is done until a 0x00
 * (or 0x00 0x00 for Unicode) is encountered. Byte position is then placed on
 * first byte after this terminator.
 */
class Parser {
	/**
	 * Create new instance, use complete array
	 * 
	 * @param in Array to parse
	 * @param encoding True: First byte of input contains encoding. False:
	 *            Encoding is set to ISO-8859-1
	 */
	public Parser(byte[] in, boolean encoding) {
		this(in, encoding, 0, in.length - 1);
	}

	/**
	 * Create new instance, use part of array
	 * 
	 * @param in Array to parse
	 * @param encoding True: First byte of input contains encoding. False:
	 *            Encoding is set to ISO-8859-1
	 * @param start Offset of first byte to parse
	 * @param stop Offset of last byte to parse
	 */
	public Parser(byte[] in, boolean encoding, int start, int stop) {
		this.in = in;
		this.pos = start;
		this.stop = stop;

		if (encoding == true) {
			parseEncoding();
		} else {
			this.encoding = ISO;
		}
	}

	/**
	 * Set byte position
	 * 
	 * @param pos New byte position
	 */
	public void setPosition(int pos) {
		this.pos = pos;
	}

	/**
	 * @return Byte position
	 */
	public int getPosition() {
		return pos;
	}

	/**
	 * Parse encoding byte. This is automatically done in the constructor if
	 * encoding is set to true.
	 */
	public void parseEncoding() {
		encoding = in[pos];
		pos++;
	}

	/**
	 * Returns the encoding that is used for the input array.
	 * 
	 * @return the encoding
	 */
	public byte getEncoding() {
		return encoding;
	}

	/**
	 * Parse next bytes as text according to set encoding
	 * 
	 * @return Parsed text
	 * @throws ParseException if the bytes are invalid and cannot be parsed
	 */
	public String parseText() throws ParseException {
		return parseText(this.encoding);
	}

	/**
	 * Parse next bytes as text according to given encoding
	 * 
	 * @param encoding Encoding to use
	 * @return Parsed text
	 * @throws ParseException if the bytes are invalid and cannot be parsed
	 */
	public String parseText(byte encoding) throws ParseException {
		try {
			// find termination
			int term = pos;

			if (encoding == ISO) {
				while (in[term] != 0 && term < stop) {
					term++;
				}
			} else {
				while (term < stop - 1 && (in[term] != 0 || in[term + 1] != 0)) {
					term += 2;
				}
			}

			// if text is terminated by end of byte array, term must be behind
			// last index
			if ((encoding == ISO && term == stop && in[term] != 0)
					|| (encoding == UNICODE && term == stop - 1 && (in[term] != 0 || in[term + 1] != 0))) {

				term = stop + 1;
			}

			// convert
			String ret = null;
			try {
				ret = new String(in, pos, term - pos,
						(encoding == ISO ? "ISO8859_1" : "Unicode"));
			} catch (java.io.UnsupportedEncodingException e) {
				// cannot happen, but throw exception just in case
				throw new ParseException();
			}

			// advance position marker
			pos = term + (encoding == ISO ? 1 : 2);

			return ret;
		} catch (Exception e) {
			throw new ParseException();
		}
	}

	/**
	 * Read next bytes to end (no real parsing, just copying)
	 * 
	 * @return Parsed binary data
	 * @throws ParseException if the bytes are invalid and cannot be parsed
	 */
	public byte[] parseBinary() throws ParseException {
		return parseBinary(stop - pos + 1);
	}

	/**
	 * Read next <code>number</code> bytes (no real parsing, just copying)
	 * 
	 * @param number Number of bytes to read
	 * @return Parsed binary data
	 * @throws ParseException if the bytes are invalid and cannot be parsed
	 */
	public byte[] parseBinary(int number) throws ParseException {
		try {
			byte[] ret = new byte[number];
			System.arraycopy(in, pos, ret, 0, number);

			pos += number; // no more reading possible...

			return ret;
		} catch (Exception e) {
			throw new ParseException();
		}
	}

	private byte[] in;

	private int stop;

	private int pos; // Next byte to parse

	private byte encoding; // Encoding used for text

	/**
	 * Encoding: ISO-8859-1
	 */
	public final static byte ISO = 0;

	/**
	 * Encoding: Unicode
	 */
	public final static byte UNICODE = 1;

}