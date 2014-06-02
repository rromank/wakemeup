package biz.wakemeup.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import biz.wakemeup.utils.DateValidator;
import biz.wakemeup.utils.EmailValidator;
import biz.wakemeup.utils.PasswordHash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBObject;

@Entity("user")
public class User implements DBEntity {
	@Id
	private ObjectId id;
	private String name;
	@JsonIgnore
	private String password;
	private String birthday;
	private String email;
	private String sex;
	private String image;

	public String getId() {
		if (id == null) {
			return null;
		}
		return id.toString();
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty("password")
	public void setPassword(String password) {
		PasswordHash passwordHash = new PasswordHash(password);
		this.password = passwordHash.getCryptedPassword();
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@JsonIgnore
	public BasicDBObject getDBObject() {
		BasicDBObject user = new BasicDBObject();
		user.append("name", name);
		user.append("password", password);
		user.append("birthday", birthday);
		user.append("email", email);
		user.append("sex", sex);
		user.append("image", image);
		return user;
	}

	@JsonIgnore
	public boolean isCorrect() {
		EmailValidator emailValidator = new EmailValidator();
		DateValidator dateValidator = new DateValidator();

		if (name != null
				&& emailValidator.validate(email)
				&& (password.length() > 5)
				&& (password != null)
				&& (sex.equals("man") || sex.equals("woman"))
				&& dateValidator.validate(birthday,
						DateValidator.Format.BIRTHDAY)) {
			return true;
		}
		return false;
	}
}
