package ie.simo.movies.domain;

public class Actor extends Person {

	private static final long serialVersionUID = -5222601603721500239L;
	private Gender gender;

	/**
	 * 
	 * @return the Gender of the Actor
	 */
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * convert String to Gender enum
	 * 
	 * @param gender
	 *            ("m" or "f")
	 */
	public void setGender(String gender) {

		if ("m".equals(gender)) {
			this.gender = Gender.M;
		} else {
			this.gender = Gender.F;
		}
	}

}

enum Gender {
	M, F;
}