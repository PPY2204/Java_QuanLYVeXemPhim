package entity;

public class Thue {
    private String maThue;
    private String loaiThue;
    private float tyLeThue;

    public Thue(String maThue, String loaiThue, float tyLeThue) {
        this.maThue = maThue;
        this.loaiThue = loaiThue;
        this.tyLeThue = tyLeThue;
    }
    

	public Thue(String maThue) {
		super();
		this.maThue = maThue;
	}


	// Getters and Setters
    public String getMaThue() {
        return maThue;
    }

    public void setMaThue(String maThue) {
        this.maThue = maThue;
    }

    public String getLoaiThue() {
        return loaiThue;
    }

    public void setLoaiThue(String loaiThue) {
        this.loaiThue = loaiThue;
    }

    public float getTyLeThue() {
        return tyLeThue;
    }

    public void setTyLeThue(float tyLeThue) {
        this.tyLeThue = tyLeThue;
    }
}
