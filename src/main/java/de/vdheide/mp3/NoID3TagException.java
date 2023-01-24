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
 * Thrown when a file checked for id3 tag contains none.
 */
public class NoID3TagException extends ID3Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Create a new exception
     */
    public NoID3TagException() {
        super();
    }
}