package entities;

public class Shot {
	public String result;
	public int x;
	public int y;

	public Shot(int xCo, int yCo) {
		x = xCo;
		y = yCo;
	}
	public void setReseult(String result){
		this.result = result;
	}
	public String getReseult(){
		return result;
	}
}
