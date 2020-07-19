/***
 * author : Subodh M
 * This is created for Everon Test
 */

package pages;

public class Category {

	private Integer id;
	private String name;
	// private Map<String, Object> additionalProperties = new HashMap<String,
	// Object>();

	public int getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\"category\":{\"id\":" + id + ",\"name\":\"" + name + "\"}";
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//    public Map<String, Object> getAdditionalProperties() {
//        return this.additionalProperties;
//    }
//
//    public void setAdditionalProperty(String name, Object value) {
//        this.additionalProperties.put(name, value);
//    }

}
