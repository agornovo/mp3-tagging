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

import java.io.IOException;
import java.io.InputStream;

/**
 * This class contains an ID3v2 extended header
 */
public class ID3v2ExtendedHeader {

	/** ******** Constructors ********* */

	/**
	 * Creates an extended header with preferences
	 */
	public ID3v2ExtendedHeader() {
		this(false, 0, 0);
	}

	/**
	 * Creates an extended header
	 * 
	 * @param use_crc Use CRC?
	 * @param crc CRC of frames (will be set to 0 if <code>use_crc</code>==
     *            false)
	 * @param padding_size Size of padding
	 */
	public ID3v2ExtendedHeader(boolean use_crc, int crc, int padding_size) {
		crc_present = use_crc;
		if (crc_present == true) {
			this.crc = crc;
		} else {
			this.crc = 0;
		}
		this.padding_size = padding_size;
	}

	/**
	 * Creates an extended header from a stream. Stream must be positioned to
	 * the first byte of the extended header.
	 * 
	 * @param in Stream to read from
	 * @exception IOException If an I/O error occurs
	 */
	public ID3v2ExtendedHeader(InputStream in) throws IOException {
		// read ext header
		byte[] head = new byte[10];
		in.read(head);

		// decode extended flags
		if (((head[4] & 0xff) & FLAG_CRC_PRESENT) > 0) {
			crc_present = true;
		}

		// decode size of padding
		padding_size = (int) (new de.vdheide.utils.Bytes(head, 6, 4).getValue());

		// read crc if present
		if (crc_present == true) {
			byte[] crc_array = new byte[4];
			in.read(crc_array);
			crc = (int) new de.vdheide.utils.Bytes(crc_array).getValue();
		}

	}

	/** ******** Public methods ********* */

	/**
	 * @return Size of extended header
	 */
	public int getSize() {
		return (crc_present == true ? 10 : 6);
	}

	/**
	 * @return Size of padding
	 */
	public int getPaddingSize() {
		return padding_size;
	}

	/**
	 * Set size of padding
	 * 
	 * @param size Size of padding
	 */
	public void setPaddingSize(int size) {
		padding_size = size;
	}

	/**
	 * @return true if CRC is used
	 */
	public boolean hasCRC() {
		return crc_present;
	}

	/**
	 * Set if CRC is used
	 * 
	 * @param crc True: CRC is used
	 */
	public void setHasCRC(boolean crc) {
		if (crc = false) {
			this.crc = 0;
		}
		crc_present = crc;
	}

	/**
	 * @return CRC stored in extended header
	 */
	public long getCRC() {
		return crc;
	}

	/**
	 * Set CRC. This automatically sets <code>hasCRC</code> to return
	 * <code>true</code>.
	 * 
	 * @param crc CRC to set
	 */
	public void setCRC(int crc) {
		this.crc = crc;
		crc_present = true;
	}

	/**
	 * Returns an array of bytes representing this extended header.
	 * <p>
	 * Note: This is not unsynchronized!
	 * 
	 * @return Extended header as bytes, ready to write
	 */
	public byte[] getBytes() {
		byte ret[];

		if (crc_present == true) {
			// extended header needs 10 + 4 bytes
			ret = new byte[14];

			// write size
			ret[0] = 0;
			ret[1] = 0;
			ret[2] = 0;
			ret[3] = 10;

			// write flags
			ret[4] = (byte) (1 << 7);
			ret[5] = 0;
		} else {
			ret = new byte[10];

			// write size
			ret[0] = 0;
			ret[1] = 0;
			ret[2] = 0;
			ret[3] = 10;

			// write flags
			ret[4] = 0;
			ret[5] = 0;
		}

		// write size of padding
		byte[] pad_byte = (new de.vdheide.utils.Bytes(padding_size, 4))
				.getBytes();
		System.arraycopy(pad_byte, 0, ret, 6, 4);

		// write crc if present
		if (crc_present == true) {
			byte[] crc_byte = (new de.vdheide.utils.Bytes(crc, 4)).getBytes();
			System.arraycopy(crc_byte, 0, ret, 10, 4);
		}

		return ret;
	}

	/** ******** Private variables ********* */

	private int padding_size = 0;

	private boolean crc_present = false;

	private int crc = 0;

	private final static byte FLAG_CRC_PRESENT = (byte) (1 << 7);

}