package model;

/***
 * author : Subodh M This is created for Everon Test
 */

public class Tag {

	private Integer id;
	private String name;

	public Integer getId() {
		return id;
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

	public String toString() {
		return "{\"id\":" + id + ",\"name\":" + "\"" + name + "\"}";
	}

}
