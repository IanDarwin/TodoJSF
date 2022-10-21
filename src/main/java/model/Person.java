package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.darwinsys.geo.Country;
import org.apache.deltaspike.core.util.StringUtils;

/**
 * Person represents one person who uses the site.
 */
@Entity
@Table(name = "person", schema = "public", 
	uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Named("person")
@NamedQueries({
    @NamedQuery(name="Persons.mailable",
		query="select p from Person p where p.loginAllowed = true and p.siteNotify=true")
})
@Cacheable(true)
public class Person implements Serializable {

	private static final long serialVersionUID = 29197198207933L;

	private long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	private String province;
	private String postCode;
	private Country country;
	private String email;
	private String webSite;
	private String homePhone;
	private String workPhone;
	private String mobilePhone;
	private String twitterName;
	private String facebookName;
	private String openId;
	private LocalDate joined;
	private LocalDate lastLogin;
	private boolean loginAllowed = true;
	private String loginDisallowedReason;
	private boolean acceptedLicense;
	private boolean minimumAge;
	private byte[] image;
	private String imageContentType;
	private boolean imageFromGravatar;
	private int version;
	private boolean siteNotify;

	/** Used to sort by most recent logins, for wholist */
	public static final Comparator<Person> LOGIN_DATE_COMPARATOR = (p1, p2) -> {
			return p1.getLastLogin().compareTo(p2.getLastLogin());
	};

	public Person() {
		// empty
	}

	public Person(long id) {
		this.id = id;
	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public Person(String firstName, String middle, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "username", unique = true, nullable = false, length = 12)
	@NotNull
	public String getUserName() {
		return this.username;
	}
	public void setUserName(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "firstname", nullable = false, length = 40)
	@NotNull
	public String getFirstname() {
		return this.firstName;
	}

	public void setFirstname(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "lastname", nullable = false, length = 40)
	@NotNull
	public String getLastname() {
		return this.lastName;
	}
	public void setLastname(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "twittername", nullable = true, length = 15)
	public String getTwitterName() {
		return this.twitterName;
	}
	public void setTwitterName(String twitterName) {
		this.twitterName = twitterName;
	}

	@Column(name = "email", nullable = false, length = 255)
	@NotNull
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "website", nullable = true, length = 255)
	public String getWebSite() {
		return this.webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "joined", length = 13)
	public LocalDate getJoined() {
		return this.joined;
	}

	public void setJoined(LocalDate joined) {
		this.joined = joined;
	}

	@Column(name = "acceptedLicense", nullable = false)
	@NotNull
	public boolean isAcceptedLicense() {
		return this.acceptedLicense;
	}
	public void setAcceptedLicense(boolean acceptsLicense) {
		this.acceptedLicense = acceptsLicense;
	}

	@Column(name = "minAge", nullable = false)
	@NotNull
	public boolean isMinimumAge() {
		return this.minimumAge;
	}
	public void setMinimumAge(boolean minimumAge) {
		this.minimumAge = minimumAge;
	}

	/** Allow site notification emails */
	@Column(name = "siteNotify", nullable = false)
	@NotNull
	public boolean isSiteNotify() {
		return this.siteNotify;
	}
	public void setSiteNotify(boolean siteNotify) {
		this.siteNotify = siteNotify;
	}

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastlogin", length = 13)
	public LocalDate getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(LocalDate lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Transient
	public String getName() {
		return getFirstname() + " " + getLastname();
	}

	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Transient
	public boolean isProfileComplete() {
		if (StringUtils.isEmpty(getAddress1()))
			return false;
		if (StringUtils.isEmpty(getCity()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s %s ('%s')",
				getFirstname(), getLastname(),
				getUserName());
	}
	
	/** For JPA, equals method should be based only on id */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !getClass().equals(obj.getClass())) {
			return false;
		}
		return getId() == ((Person)obj).getId();
	}

	/** hashCode method must be based on same as equals method */
	@Override
	public int hashCode() {
               return 31 * (int)getId();
	}

	@Column(name = "open_id", length = 128)
	public String getOpenId() {
		return this.openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "address1", length = 128)
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "address2", length = 128)
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(name = "city", length = 128)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "province", length=15)
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(name = "post_code", length = 15)
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "country")
	@Enumerated(EnumType.STRING)
	/* ISO 2-character country code */
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}

	@Column(name="login_allowed")
	public boolean isLoginAllowed() {
		return loginAllowed;
	}

	public void setLoginAllowed(boolean loginAllowed) {
		this.loginAllowed = loginAllowed;
	}

	@Column(name="login_disallowed_reason")
	public String getLoginDisallowedReason() {
		return loginDisallowedReason;
	}

	public void setLoginDisallowedReason(String loginDisallowedReason) {
		this.loginDisallowedReason = loginDisallowedReason;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public boolean isImageFromGravatar() {
		return imageFromGravatar;
	}

	public void setImageFromGravatar(boolean imageFromGravatar) {
		this.imageFromGravatar = imageFromGravatar;
	}

	public String getFacebookName() {
		return facebookName;
	}

	public void setFacebookName(String facebookName) {
		this.facebookName = facebookName;
	}

}
