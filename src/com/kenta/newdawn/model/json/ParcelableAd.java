package com.kenta.newdawn.model.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * http://stackoverflow.com/questions/7181526/example-of-implementing-parcelable
 * Good example:
 * http://prasanta-paul.blogspot.sg/2010/06/android-parcelable-example.html
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParcelableAd implements Parcelable {

	@JsonProperty	
	private String mListId;
	@JsonProperty
	private String mImage;
	@JsonProperty
	private String mSubject;
	@JsonProperty
	private String mBody;
	@JsonProperty
	private String mPrice;
	@JsonProperty
	private String mDate;
	@JsonProperty
	private String mName;
	@JsonProperty
	private String mPhone;

	
	// Dummy Constructor - We need it, otherwise, the Constructor below will mess up Jackson
	// http://stackoverflow.com/questions/7625783/jsonmappingexception-no-suitable-constructor-found-for-type-simple-type-class	// 
	public ParcelableAd() {}
	
	// Constructor
	public ParcelableAd(String _list_id, String _image, String _subject,
			String _body, String _price, String _date, String _name,
			String _phone) {
		this.mListId = _list_id;
		this.mImage = _image;
		this.mSubject = _subject;
		this.mBody = _body;
		this.mPrice = _price;
		this.mDate = _date;
		this.mName = _name;
		this.mPhone = _phone;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(mListId);
		out.writeString(mImage);
		out.writeString(mSubject);
		out.writeString(mBody);
		out.writeString(mPrice);
		out.writeString(mDate);
		out.writeString(mName);
		out.writeString(mPhone);
	}

	public static final Parcelable.Creator<ParcelableAd> CREATOR = new Parcelable.Creator<ParcelableAd>() {
		public ParcelableAd createFromParcel(Parcel in) {
			return new ParcelableAd(in);
		}

		public ParcelableAd[] newArray(int size) {
			return new ParcelableAd[size];
		}
	};

	// Parceling part
	public ParcelableAd(Parcel in) {
		mListId = in.readString();
		mImage = in.readString();
		mSubject = in.readString();
		mBody = in.readString();
		mPrice = in.readString();
		mDate = in.readString();
		mName = in.readString();
		mPhone = in.readString();
	}

	// --------------------------------------------------------------------------------------------
	// GETTER & SETTER
	// --------------------------------------------------------------------------------------------

	public String getListId() {
		return mListId;
	}

	public void setListId(String mListId) {
		this.mListId = mListId;
	}

	public String getImage() {
		return mImage;
	}

	public void setImage(String mImage) {
		this.mImage = mImage;
	}

	public String getSubject() {
		return mSubject;
	}

	public void setSubject(String mSubject) {
		this.mSubject = mSubject;
	}

	public String getBody() {
		return mBody;
	}

	public void setBody(String mBody) {
		this.mBody = mBody;
	}

	public String getPrice() {
		return mPrice;
	}

	public void setPrice(String mPrice) {
		this.mPrice = mPrice;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String mDate) {
		this.mDate = mDate;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String mPhone) {
		this.mPhone = mPhone;
	}

} // class end
