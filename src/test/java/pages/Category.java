package pages;

/***
 * author : Subodh M
 * This is created for Everon Test
 */

public class Category {

	private Integer id;
	private String name;

	public int getId() {
		return id;
	}

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

}
