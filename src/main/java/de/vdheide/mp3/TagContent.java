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
 * An instance of this class contains the content read from a ID3(v2) tag. This
 * class is designed to be as flexible as possible to reduce the number of cases
 * where information has to be returned as binary when it is rather more
 * structured.
 * <p>
 * It provides storage for - a type (e.g. a MIME-type or a language, Text) - a
 * subtype (text or binary) - a description (text) - the content (text or
 * binary)
 * <p>
 * Unused fields should be set to <code>null</code>.
 */
public class TagContent {
	/**
	 * Create a new instance with all fields set to null.
	 */
	public TagContent() {
	}

	/**
	 * Set type field
	 * 
	 * @param type Type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Type of tag
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set subtype field with textual data
	 * 
	 * @param subtype Subtype to set
	 */
	public void setSubtype(String subtype) {
		subtype_text = subtype;
	}

	/**
	 * Set subtype field with binary data
	 * 
	 * @param subtype Subtype to set
	 */
	public void setSubtype(byte[] subtype) {
		subtype_binary = subtype;
	}

	/**
	 * Get subtype
	 * 
	 * @return Textual subtype
	 */
	public String getTextSubtype() {
		return subtype_text;
	}

	/**
	 * Get subtype
	 * 
	 * @return Binary subtype
	 */
	public byte[] getBinarySubtype() {
		return subtype_binary;
	}

	/**
	 * Set description field
	 * 
	 * @param desc Description to set
	 */
	public void setDescription(String desc) {
		description = desc;
	}

	/**
	 * Get description
	 * 
	 * @return Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set content field with textual data
	 * 
	 * @param content Content to set
	 */
	public void setContent(String content) {
		content_text = content;
	}

	/**
	 * Set content field with binary data
	 * 
	 * @param content Content to set
	 */
	public void setContent(byte[] content) {
		content_binary = content;
	}

	/**
	 * Get content
	 * 
	 * @return Textual content
	 */
	public String getTextContent() {
		return content_text;
	}

	/**
	 * Get content
	 * 
	 * @return Binary content
	 */
	public byte[] getBinaryContent() {
		return content_binary;
	}

	/** ******** Private fields ********* */

	protected String type = null;

	protected String subtype_text = null;

	protected byte[] subtype_binary = null;

	protected String description = null;

	protected String content_text = null;

	protected byte[] content_binary = null;

}