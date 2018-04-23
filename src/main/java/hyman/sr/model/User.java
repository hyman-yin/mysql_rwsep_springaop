package hyman.sr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_user")
public class User implements Serializable{
	private static final long serialVersionUID = 5725114254278844111L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="username")
    private String username;
	
	@Column(name="sex")
    private String sex;
	
	@Column(name="age")
    private Integer age;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}



	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", sex=" + sex + ", age=" + age + "]";
    }
}